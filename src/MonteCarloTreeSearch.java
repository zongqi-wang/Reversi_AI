

public class MonteCarloTreeSearch {

    //setting the max time a tree can search
    public final int MAX_TIMER = 3;

    GameBoard gameBoard;
    private int turn;
    PlayerType ai;

    public MonteCarloTreeSearch(GameBoard game, PlayerType ai, int turn){
        Tree gameTree = new Tree();
        this.gameBoard = game;
        this.turn = turn;
        this.ai = ai;
    }

    /**
     * This function returns the next move for the AI
     * @return
     */
//    public int[] getNextMove(){
//
//    }
//
//    private int[] selection(){
//
//    }
//
//    private int[] findPromisingNode(){
//
//    }
//
//    private void expansion(){}
//
//
//    private void backpropagation(){}
//

}
