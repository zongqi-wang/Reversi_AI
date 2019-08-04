import java.util.List;
import java.util.Random;

public class MonteCarloTreeSearch{

    //setting the max time a tree can search
    public final int MAX_TIMER = 1500;
    public final int MAX_DEPTH = 15;

    private int currentPlayer;
    private int opponent;
    PlayerType ai;
    Tree gameTree;
//    Node root;

    GameBoard gameBoard;
    public MonteCarloTreeSearch(GameBoard game){
        this.gameTree = new Tree(game, 1);
//        this.root = gameTree.getRoot();
        this.gameBoard = game;
    }

    public MonteCarloTreeSearch(GameBoard game, PlayerType ai, int currentPlayer){

        this.currentPlayer = currentPlayer;
        this.ai = ai;
        if(currentPlayer == 1) this.opponent = 2;
        else if(currentPlayer == 2) this.opponent = 1;
        this.gameTree = new Tree(game, currentPlayer);
//        this.root = gameTree.getRoot();
    }

    /**
     * This function returns the next move for the AI
     * @return
     */
    public int[] getNextMove(){

        long start = System.currentTimeMillis();
        int[] coord = new int[2];
        int numOfSimulations = 0;
        if(this.ai == PlayerType.PURE){
            //set timer on this
            while(System.currentTimeMillis()-start < MAX_TIMER){
                Node promisingNode = findPromisingNode(gameTree.getRoot());
//                if(promisingNode == null) return null;
                GameBoard game = promisingNode.getGame();
                int[][] legalMoves = game.getLegalMoves(promisingNode.getPlayer());
                if(game.getLegalMovesCount(legalMoves) > 0){
                    simulate(promisingNode);
                    numOfSimulations++;

                }

            }
            int numOfChildren = this.gameTree.getRoot().getNumOfChildren();
            if(numOfChildren == 0)
                return null;
            //printing Statistics
            for(int i = 0; i < numOfChildren; i++){
                Node child = this.gameTree.getRoot().getChildren().get(i);
                System.out.println("Move "+ i +  " At Row: " + child.getMove()[0] +
                        " and Col: " + child.getMove()[1] + " Has "+child.n + " Simulations and "+
                        child.w + " Wins with a UTC score of " + child.getUCTscore());
            }
            System.out.println(numOfSimulations + " Simulations found!");
            Node nextMove = gameTree.getRoot().getChildWithHighestWR();
            gameTree.setRoot(nextMove);
            return nextMove.getMove();


        }
        else if(this.ai == PlayerType.MINMAX){

        }
        else if(this.ai == PlayerType.NN){

        }

        return coord;

    }

    /**
     *
     * @param root
     * @return
     */
    private Node findPromisingNode(Node root){

        if(root == null){
            System.out.println("root is null????");
        }
        if(root.getPlayer() == 1 && root.getGame().isPlayerOneNoMove()){
            return root;
        }
        else if(root.getPlayer() == 2 && root.getGame().isPlayerTwoNoMove())
            return root;
        if(root.getGame().gameEnd()){
            return root;
        }

//        root.getGame().printBoardToConsole();
        List<Node> children = root.getChildren();
//        System.out.println("number of children: " + root.getNumOfChildren());
        //node has been untouched
        if(!root.simulated || root.getNumOfChildren() == 0){
            root.simulated = true;
            //Populate its children on all available legal moves
            int[][] legalMoves = root.getGame().getLegalMoves(root.getPlayer());

            populateChildrenList(legalMoves, root, children);

            return root;
        }


        Node hiestUTC = root.highestUTCChild();
        if(hiestUTC == null){
//            root.getGame().printBoardToConsole();
            System.out.println(root.getNumOfChildren());
        }
        return findPromisingNode(root.highestUTCChild());

    }

    /**
     * Play out the rest of the game based purely on random moves
     * @param node
     */
    public void simulate(Node node){
        //game has ended
        if(node.getGame().gameEnd()){
            gameTree.backPropagation(node);
        }
        else{

            int player = node.getPlayer();
            int[][] legalMoves = node.getGame().getLegalMoves(player);
            int legalMovesCount = node.getGame().getLegalMovesCount();

            if(node.getPlayer() == 1 && legalMovesCount == 0){
//                System.out.println("Player 2 has no move!");
                legalMoves = node.getGame().getLegalMoves(2);
                if(node.getGame().gameEnd())
                    gameTree.backPropagation(node);
                else{
                    Node sameBoard = new Node(node.getGame(), 2);
                    sameBoard.addParent(node);
                    populateChildrenList(legalMoves, sameBoard, sameBoard.getChildren());
                    node.addChild(sameBoard);
                    simulate(sameBoard);
                }


            }
            else if(node.getPlayer() == 2 && legalMovesCount == 0){
//                System.out.println("Player 2 has no move!");
                legalMoves = node.getGame().getLegalMoves(1);
//                System.out.println("Player 1's ");
                if(node.getGame().gameEnd())
                    gameTree.backPropagation(node);
                else{
                    Node sameBoard = new Node(node.getGame(), 1);
                    populateChildrenList(legalMoves, sameBoard, sameBoard.getChildren());
                    sameBoard.addParent(node);
                    node.addChild(sameBoard);
                    simulate(sameBoard);
                }

            }
            else{

                Random r = new Random();
//            System.out.println("AI's player " + player);
//            System.out.println("AI's legal moves = " + legalMovesCount);
//            System.out.println("AI's childrean size = " + node.getNumOfChildren());

                int n = r.nextInt(legalMovesCount);
//            System.out.println("n = "+n);
                if(node.getNumOfChildren() == 0 && legalMovesCount != 0){
                    populateChildrenList(legalMoves, node, node.getChildren());
//                    System.out.println("current player :" + player + " legal moves: " + legalMovesCount);
//                    node.getGame().printBoardToConsole();
                }
                Node nextMove = node.getChildren().get(n);

                int[][] nextLegalMoves = nextMove.getGame().getLegalMoves(nextMove.getPlayer());
                populateChildrenList(nextLegalMoves, nextMove, nextMove.getChildren());
//                System.out.println("root childrean size = "+ node.getNumOfChildren());
//                System.out.println("Next move children size = " + nextMove.getNumOfChildren());
                simulate(nextMove);
            }

        }
    }

    private void populateChildrenList(int[][] legalMoves, Node root, List<Node> children){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(legalMoves[row][col] == root.getPlayer()){
                    children.add(new Node(root, root.getGame(), row, col));
                }
            }
        }
    }


    /**
     *
     * @param currentPlayer
     */
    public void setCurrentPlayer(int currentPlayer){
        this.currentPlayer = currentPlayer;
        if(currentPlayer == 1) this.opponent = 2;
        else if(currentPlayer == 2) this.opponent = 1;

    }

    /**
     *
     * @param ai
     */
    public void setAI(PlayerType ai){
        this.ai = ai;
    }


    /**
     *
     * @param game
     */
    public void setGameBoard(GameBoard game){
        this.gameBoard = game;
    }

    /**
     *
     */
    public void advanceGameTree(){

        //if nothing to be changed
        if(this.gameBoard.equals(gameTree.getRoot().getGame())) return;


        if(gameTree.getRoot().getNumOfChildren()==0){
//            System.out.println("cannot find opponent's move");
            //first player
//            System.out.println("Opponents is: " + opponent);
            this.gameTree = new Tree(this.gameBoard, currentPlayer);
//            this.root = gameTree.getRoot();
//            this.root.getGame().printBoardToConsole();
//            gameTree.getRoot().getGame().printBoardToConsole();
        }
        else{
            Node tmp = null;
            List<Node> children = gameTree.getRoot().getChildren();
            for(int i = 0; i < children.size(); i++){
                tmp = children.get(i);
//                children.get(i).getGame().printBoardToConsole();
                if(tmp.getGame().equals(this.gameBoard))
                    break;
            }
//            tmp.getGame().printBoardToConsole();
            if(tmp == null){
                this.gameTree = new Tree(this.gameBoard, currentPlayer);
            }
            else
                this.gameTree.setRoot(tmp);
        }


    }

}
