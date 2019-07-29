public class Game implements Runnable{


    private Thread thread;
    private boolean running = false;

    private GameState gs;
    private GameWindow gw;
    private GameBoard gb;
    private MonteCarloTreeSearch mcts;
    private MouseInput mouse;

    private boolean humanTurn = false;
    private int currentPlayer;
    public PlayerType players[];

    public Game(){
        gw = new GameWindow("Reversi", this);
        gs = GameState.MAIN_MENU;
        players = new PlayerType[2];
        currentPlayer = 1;
        gb = new GameBoard();
        gw.updateGameWindow();
        this.start();
    }


    /**
     *
     */
    public void takeTurn(){
        int[][] legalMoves = gb.getLegalMoves(currentPlayer);
        gw.render(gb.getGameBoard(), legalMoves, currentPlayer, gb.getScore());

        //first player's turn
        if(currentPlayer == 1){
            if(players[0] == PlayerType.HUMAN){
                humanTurn(legalMoves);
            }
            else if(players[0] == PlayerType.PURE){
                mcts = new MonteCarloTreeSearch(gb, PlayerType.PURE, currentPlayer);

            }
            else if(players[0] == PlayerType.MINMAX){
                mcts = new MonteCarloTreeSearch(gb, PlayerType.MINMAX, currentPlayer);

            }
            //Neural Network
            else{
                mcts = new MonteCarloTreeSearch(gb, PlayerType.NN, currentPlayer);

            }

            System.out.println("current player is " + currentPlayer);
            currentPlayer = 2;

        }
        //second player's turn
        else if(currentPlayer == 2){
            if(players[1] == PlayerType.HUMAN){
                humanTurn(legalMoves);
            }
            else if(players[1] == PlayerType.PURE){
                mcts = new MonteCarloTreeSearch(gb, PlayerType.PURE, currentPlayer);

            }
            else if(players[1] == PlayerType.MINMAX){
                mcts = new MonteCarloTreeSearch(gb, PlayerType.MINMAX, currentPlayer);

            }
            //Neural Network
            else{
                mcts = new MonteCarloTreeSearch(gb, PlayerType.NN, currentPlayer);

            }
            System.out.println("current player is " + currentPlayer);
            currentPlayer =1;

        }



    }


    /**
     * This function gets human input on the board.
     * @param legalMoves The grid of legal moves possible for player
     */

    private void humanTurn(int[][] legalMoves){
        mouse = new MouseInput(legalMoves);
        gw.addMouse(mouse);
        humanTurn = true;
    }

    private void passTurn(int[] coord){

        humanTurn = false;
        gw.removeMouse(mouse);
        System.out.println(coord[0] + " Y: "+  coord[1]);
        gb.updatePosition(coord[1], coord[0], this.currentPlayer);
//        if(this.currentPlayer == 1) this.currentPlayer = 2;
//        else if(this.currentPlayer == 2) this.currentPlayer = 1;
        takeTurn();
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

    /**
     * This function takes care of internal game clock, and will keep going
     * until stop() function is called.
     */
    public void run(){
        System.out.println("running");
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
//                render();
                frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
//                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    /**
     *
     */
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    /**
     *
     */
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void tick(){
        if(mouse != null){
            if(humanTurn && mouse.received()){
                passTurn(mouse.getCoord());
            }
        }


    }

}
