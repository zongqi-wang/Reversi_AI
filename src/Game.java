import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game{

    public final int WIDTH = 1200;
    public final int HEIGHT = 900;

    private GameState gs;
    private GameWindow gw;

    public Game(){
        gw = new GameWindow(WIDTH, HEIGHT,  "Reversi", this);
        gs = GameState.MAIN_MENU;

        Tree gameTree = new Tree();
        GameBoard newGame = new GameBoard();

        gw.updateGameWindow();
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
