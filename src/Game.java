import javax.management.monitor.MonitorSettingException;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game{


    private GameState gs;
    private GameWindow gw;
    private GameBoard gb;
    private MonteCarloTreeSearch mcts;

    public PlayerType players[];

    public Game(){
        gw = new GameWindow("Reversi", this);
        gs = GameState.MAIN_MENU;
        players = new PlayerType[2];
        gb = new GameBoard();
        gw.updateGameWindow();
    }

    public int[] getHumanInput(int[][] legalMoves){

    }

    /**
     *
     */
    public void startGame(){
        int turn = 1;

        int[][] legalMoves = gb.getLegalMoves(turn);
            gw.render(gb.getGameBoard(), legalMoves, turn, gb.getScore());

            //first player's turn
            if(turn == 1){
                if(players[0] == PlayerType.HUMAN){
                    getHumanInput(legalMoves);
                }
                else if(players[0] == PlayerType.PURE){
                    mcts = new MonteCarloTreeSearch(gb, PlayerType.PURE, turn);

                }
                else if(players[0] == PlayerType.MINMAX){
                    mcts = new MonteCarloTreeSearch(gb, PlayerType.MINMAX, turn);

                }
                //Neural Network
                else{
                    mcts = new MonteCarloTreeSearch(gb, PlayerType.NN, turn);
                    //TODO: IMPLEMENT NEURAL NETWORK XFUCKINGD
                }

                turn = 2;

            }
            if(turn == 1){
                if(players[1] == PlayerType.HUMAN){

                }
                else if(players[1] == PlayerType.PURE){
                    mcts = new MonteCarloTreeSearch(gb, PlayerType.PURE, turn);

                }
                else if(players[1] == PlayerType.MINMAX){
                    mcts = new MonteCarloTreeSearch(gb, PlayerType.MINMAX, turn);

                }
                //Neural Network
                else{
                    mcts = new MonteCarloTreeSearch(gb, PlayerType.NN, turn);
                    //TODO: IMPLEMENT NEURAL NETWORK XFUCKINGD
                }

                turn = 1;
            }


    }


    /**
     *
     * @return
     */
    public GameBoard getGameBoard(){
        return this.gb;
    }


    /**
     * Standard helper function
     * @param p1 player one type
     */
    public void setPlayerOne(PlayerType p1){
        this.players[0] = p1;
    }

    /**
     * standard helper function
     * @param p2 player two type
     */
    public void setPlayerTwo(PlayerType p2){
        this.players[1] = p2;
    }



    /**
     * Main function of Reversi game
     * @param args
     */
    public static void main(String[] args) {

        //Welcome message
        //System.out.println("Welcome to a new game of Reversi!");
        //System.out.println("Would you like to play first or second? Black player plays first: ");
        //int pp = getPlayerPreferences();
        //newGame.printBoardToConsole();

        new Game();
    }

        /**
     * Standard helper function
     * @return game state
     */
    public GameState getGameState(){
        return this.gs;
    }

    /**
     * standard helper function
     * @param state The updated gamestate
     */
    public void setGameState(GameState state){
        this.gs = state;
        gw.updateGameWindow();
    }

}
