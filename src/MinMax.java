import com.sun.jdi.IntegerType;

import javax.print.DocFlavor;
import java.util.List;

public class MinMax {

    GameBoard game;
    Tree gameTree;
    private int maximizingPlayer;
    private final int MAX_DEPTH = 5 ;

    private int[][] weights = {
            {300, -100, 100,  50,  50, 100, -100,  300},
            {-100, -200, -50, -50, -50, -50, -200, -100},
            {100,  -50, 100,   0,   0, 100,  -50,  100},
            {50,  -50,   0,   0,   0,   0,  -50,   50},
            {50,  -50,   0,   0,   0,   0,  -50,   50},
            {100,  -50, 100,   0,   0, 100,  -50,  100},
            {-100, -200, -50, -50, -50, -50, -200, -100},
            {300, -100, 100,  50,  50, 100, -100,  300}};

    public MinMax(GameBoard game, int maximizingPlayer){
        this.maximizingPlayer = maximizingPlayer;
        this.game = game;
        this.gameTree = new Tree(game, maximizingPlayer);
    }

    public int[] findNextMove(){
        int[] coord = new int[2];

        System.out.println("Calculating MinMax values");
        Node root = this.gameTree.getRoot();
        int[][] legalMoves = root.getGame().getLegalMoves(maximizingPlayer);
        populateChildrenList(legalMoves, root);

        double max = Integer.MIN_VALUE;
        Node tmp = null;


        for(int i = 0; i < root.getNumOfChildren(); i++){
            double eval = minimax(root.getChild(i), MAX_DEPTH,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, this.maximizingPlayer);
            if(max <  eval){
                max = eval;
                tmp = root.getChild(i);
            }
        System.out.println("Position Row: " + root.getChild(i).getMove()[0] + " Col: " +
                root.getChild(i).getMove()[1] + " has a minimax score of " + eval);

        }



        //losing play but have to take it
        if(tmp == null) tmp = root.getChild(0);
        return tmp.getMove();
    }

    /**
     * Control Corners
     * Discs on board
     * Mobility
     * parity
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @param maximizingPlayer
     * @return
     */
    public double minimax(Node node, int depth,
                         double alpha, double beta, int maximizingPlayer){
        if(node.getGame().gameEnd()){
            if(node.getGame().getWinner() == maximizingPlayer)
                return Integer.MAX_VALUE;
            else
                return Integer.MIN_VALUE;
        }
        else if(depth == 0)
            return evaluate(node);

        int[][] legalMoves = node.getGame().getLegalMoves(node.getPlayer());
        int legalCount = node.getGame().getLegalMovesCount();
        if(node.getNumOfChildren() == 0 && legalCount > 0){
            populateChildrenList(legalMoves, node);
        }
        if(node.getPlayer() == maximizingPlayer){
            double maxEval = Integer.MIN_VALUE;
            double eval = 0;

            for(int i = 0; i < node.getNumOfChildren(); i++){
                eval = minimax(node.getChildren().get(i), depth-1, alpha, beta, maximizingPlayer);
                if(eval > maxEval) maxEval = eval;
                if(eval > alpha) alpha = eval;
                if(beta <= alpha)
                    break;
            }
            return maxEval;
        }
        else{

            double minEval = Integer.MAX_VALUE;
            double eval = 0;
            for(int i = 0; i < node.getNumOfChildren(); i++){
                eval = minimax(node.getChildren().get(i), depth-1, alpha, beta, maximizingPlayer);
                if(eval < minEval) minEval = eval;
                if(eval < beta) beta = eval;

            }
            return minEval;
        }
    }


    private double evaluate(Node node){
        GameBoard game = node.getGame();
        if(game.gameEnd()){
            if(game.getWinner() == maximizingPlayer) return Double.MAX_VALUE;
            else return Double.MIN_VALUE;
        }
        if(game.getDiscOnBoard() <= 20){
            return 5*mobility(game, node.getPlayer())
                    +100000*corners(game)
                    +sumWeights(game, node.getPlayer());

        }
        else if(game.getDiscOnBoard() <= 58){

            return 10*discDifference(game) +
                    2*mobility(game, node.getPlayer()) +
                    100*parity(game)+
                    10000*corners(game)
                    +sumWeights(game, node.getPlayer());
        }
        else{
            return 500*discDifference(game) +
                    500*parity(game) +
                    10000*corners(game)
                    +sumWeights(game, node.getPlayer());
        }
//        return 0;
    }

    private int sumWeights(GameBoard game, int player){
        int sum = 0;
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(game.getGameBoard()[row][col] == player)
                    sum += weights[row][col];
            }
        }
        return sum;
    }

    private int mobility(GameBoard game, int player){
        game.getLegalMoves(player);
        return game.getLegalMovesCount();
    }


    private double discDifference(GameBoard game){
        double black = 0, white = 0;
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(game.getGameBoard()[row][col] == 1) black++;
                else if(game.getGameBoard()[row][col] == 2) white++;
            }
        }
        if(maximizingPlayer == 1){
            return 100*(black - white)/(black+white);
        }

        else{
            return 100*(white-black)/(black+white);
        }
    }

    private void populateChildrenList(int[][] legalMoves, Node root){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(legalMoves[row][col] == root.getPlayer()){
                    root.getChildren().add(new Node(root, root.getGame(), row, col));
                }
            }
        }
    }

    private int parity(GameBoard game){
        if((64 - game.getDiscOnBoard()) % 2==0)
            return -1;
        else return 1;
    }


    private double corners(GameBoard game){
        double black = 0, white = 0;
        if(game.getGameBoard()[0][0] == 1) black++;
        else if(game.getGameBoard()[0][0] == 2) white++;

        if(game.getGameBoard()[7][7] == 1) black++;
        else if(game.getGameBoard()[7][7] == 2) white++;

        if(game.getGameBoard()[7][0] == 1) black++;
        else if(game.getGameBoard()[7][0] == 2) white++;

        if(game.getGameBoard()[0][7] == 1) black++;
        else if(game.getGameBoard()[0][7] == 2) white++;

        if(maximizingPlayer == 1){
            return 100*(black - white)/(black+white+1);
        }

       else{
            return 100*(white-black)/(black+white+1);
        }
    }
}
