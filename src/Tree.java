public class Tree {

    private Node root;
    private int depth;


    /**
     * Constructor
     */
    public Tree(GameBoard game, int currentPlayer){
        this.root = new Node(game, currentPlayer);
        this.depth = 0;
    }

    public Tree(Node root){
        this.root = root;
        this.depth = 0;
    }

    public Node dfs(){
        return null;
    }

    public void backPropagation(Node leaf){

        int winner = leaf.getGame().getWinner();
//        System.out.println("winner is: " + (winner));
        Node ptr = leaf;


        while(ptr != this.root){
            ptr.simulated = true;
            ptr.n+=1;
            //if draw
            if(winner == 0) ptr.w += 0.5;
            if(ptr.getPlayer() != winner) ptr.w+=1;
            ptr = ptr.getParent();
        }
        ptr.n+=1;
    }

    public Node getRoot(){
        return this.root;
    }

    public void setRoot(Node root){
        if(root == null) System.out.println("null root!");
        this.root = root;
        this.root.removeParents();
    }


}
