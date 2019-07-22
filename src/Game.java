import java.awt.*;

public class Game extends Canvas implements Runnable{

    public final int HEIGHT = 600;
    public final int WIDTH = 800;
    public synchronized void start(){

    }

    public void run(){

    }

    public Game(){
        GameWindow g = new GameWindow(WIDTH, HEIGHT,  "Reversi", this);

        
    }


    public static int getPlayerPreferences(){

        return 0;
    }

    public static void main(String[] args) {
        Tree gameTree = new Tree();
        GameBoard newGame = new GameBoard();

        //Welcome message
        System.out.println("Welcome to a new game of Reversi!");

        System.out.println("Would you like to play first or second? Black player plays first: ");
        int pp = getPlayerPreferences();

        newGame.printBoardToConsole();

        new Game();
    }

}
