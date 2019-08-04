import java.util.*;



public class Node {

    private Node parent;
    private List<Node> children;
    private int depth;
    public double w, n, t;
    public boolean simulated = false;
    private GameBoard game;
    private int player = 0;
    private int[] move;
    public boolean hasMove;


    public Node(GameBoard game, int currentPlayer){
        this.depth = 0;
        this.w = 0;
        this.n = 0;
        this.t = 0;
        this.simulated = false;
        this.parent = null;
        this.simulated = false;
        this.children =  new ArrayList<Node>();
        this.player = currentPlayer;
        this.game = new GameBoard(game);
        this.move = new int[2];
        hasMove = false;
    }

    public Node(Node parent, GameBoard game, int row, int col){
        this.parent = parent;
        this.depth = parent.depth+1;
        this.t = parent.n;
        this.n = 0;
        this.w = 0;
        this.simulated = false;
        this.children = new ArrayList<Node>();
        if(parent.getPlayer() == 1) this.player = 2;
        else this.player = 1;

        this.game = new GameBoard(game);
        this.game.updatePosition(row, col, parent.getPlayer());

        this.move = new int[2];
        this.move[0] = row;
        this.move[1] = col;
        hasMove = true;
    }

    public GameBoard getGame() {
        return game;
    }

    public double getUCTscore(){
        if(this.n == 0){
            return 10000000;
        }
        else{
            return ((w/n)+ (Math.sqrt(2)*Math.sqrt(Math.log(parent.n)/n)));
        }
    }

    public Node highestUTCChild(){
        double max = -10000000;
        Node tmp = null;
//        System.out.println("highest UTC children size:" + this.children.size());
        for(int i = 0; i < this.children.size(); i++){
            double childScore = children.get(i).getUCTscore();
            if(childScore > max){
                max = childScore;
                tmp = children.get(i);

            }
        }
        return tmp;
    }

    public int getPlayer(){
        return this.player;
    }

    public int[] getMove(){
        return this.move;
    }

    public Node getChildWithHighestWR(){
        double max = -100000;
        Node tmp = null;
        for(int i = 0; i < children.size(); i++){
            double wr = children.get(i).w/children.get(i).n;
            if(wr > max){
                max = wr;
                tmp = children.get(i);
            }
        }
        return tmp;
    }


    public void removeParents(){
        this.parent = null;
    }

    public List<Node> getChildren(){
        return this.children;
    }

    public int getNumOfChildren(){
        return this.children.size();
    }

    public void addChild(Node child){
        this.children.add(child);
    }

    public void addParent(Node parent){
        this.parent = parent;
    }

    public Node getParent(){return this.parent;}
    /**
     *
     * @param row
     * @param col
     */
    public void setMove(int row, int col){
        this.move[0] = row;
        this.move[1] = col;
        this.hasMove = true;
    }

    public Node getChild(int index){
        return children.get(index);
    }

}
