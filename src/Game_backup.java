//import java.awt.*;
//import java.awt.image.BufferStrategy;
//import java.util.logging.Handler;
//
//public class Game extends Canvas implements Runnable{
//
//
//    public final int WIDTH = 1200;
//    public final int HEIGHT = 900;
//
//    private Thread thread;
//    private boolean running = false;
//
//    private GameHandler handler;
//    private GameWindow gw;
//    private GameState gs;
//    /**
//     *
//     */
//    public synchronized void start(){
//        thread = new Thread(this);
//        thread.start();
//        running = true;
//    }
//
//    /**
//     *
//     */
//    public synchronized void stop(){
//        try{
//            thread.join();
//            running = false;
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * This function takes care of internal game clock, and will keep going
//     * until stop() function is called.
//     */
//    public void run(){
//        System.out.println("running");
//        long lastTime = System.nanoTime();
//        double amountOfTicks = 60.0;
//        double ns = 1000000000 / amountOfTicks;
//        double delta = 0;
//        long timer = System.currentTimeMillis();
//        int frames = 0;
//        while(running)
//        {
//            long now = System.nanoTime();
//            delta += (now - lastTime) / ns;
//            lastTime = now;
//            while(delta >=1)
//            {
//                tick();
//                delta--;
//            }
//            if(running)
//                render();
//            frames++;
//
//            if(System.currentTimeMillis() - timer > 1000)
//            {
//                timer += 1000;
//                System.out.println("FPS: "+ frames);
//                frames = 0;
//            }
//        }
//        stop();
//    }
//
//    /**
//     *
//     */
//    public void tick(){
//        handler.tick();
//        gw.tick();;
//    }
//
//    /**
//     *
//     */
//    public void render(){
//        BufferStrategy bs = this.getBufferStrategy();
//        if(bs == null){
//            this.createBufferStrategy(3);
//            return;
//        }
//
//        //System.out.println("Rendering");
//        Graphics g = bs.getDrawGraphics();
//        g.setColor(Color.black);
//        g.fillRect(0, 0, WIDTH, HEIGHT);
//        handler.render(g);
//        gw.render(g);
//        g.dispose();
//        bs.show();
//    }
//
//    /**
//     *
//     */
//    public Game(){
//        gs = GameState.MAIN_MENU;
//        handler = new GameHandler();
//        this.addMouseListener(new MouseInput(handler));
//        gw = new GameWindow(WIDTH, HEIGHT,  "Reversi", this);
//
//
//
//    }
//
//    /**
//     * Standard helper function
//     * @return game state
//     */
//    public GameState getGameState(){
//        return this.gs;
//    }
//
//    /**
//     * standard helper function
//     * @param state The updated gamestate
//     */
//    public void setGameState(GameState state){
//        this.gs = state;
//    }
//
//    /**
//     * This function gets player preference if they want to play first or not from the console
//     * @return 1 if player wants to play first. 2 if second.
//     */
//    public static int getPlayerPreferences(){
//
//        return 0;
//    }
//
//    /**
//     * Main function of Reversi game
//     * @param args
//     */
//    public static void main(String[] args) {
//        Tree gameTree = new Tree();
//        GameBoard newGame = new GameBoard();
//
//        //Welcome message
//        //System.out.println("Welcome to a new game of Reversi!");
//        //System.out.println("Would you like to play first or second? Black player plays first: ");
//        //int pp = getPlayerPreferences();
//        //newGame.printBoardToConsole();
//
//        new Game();
//    }
//
//}
