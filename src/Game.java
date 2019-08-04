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

    private boolean playerOneNoMove = false;
    private boolean playerTwoNoMove = false;

    public Game(){

        gw = new GameWindow("Reversi", this);
        gs = GameState.MAIN_MENU;
        players = new PlayerType[2];
        currentPlayer = 1;
        gb = new GameBoard();
        gw.updateGameWindow();
        mcts = new MonteCarloTreeSearch(this.gb);
        start();
    }


    /**
     *
     */
    public void takeTurn(){
        int[][] legalMoves = gb.getLegalMoves(currentPlayer);
        //checking if game ended
        if(gb.gameEnd()||(playerOneNoMove && playerTwoNoMove)){
            System.out.println("Game Ended!");
            System.out.println("Player "+gb.getWinner()+" Won!");
            int scores[] = gb.getScore();
            System.out.println("Player 1 score: " + scores[0] + " Player 2 score: " + scores[1]);
            this.gw.getFrame().dispose();
            running = false;
            System.exit(0);

        }
        if(gb.getLegalMovesCount() == 0){
            if(currentPlayer == 1) playerOneNoMove = true;
            else playerTwoNoMove = true;
            currentPlayer = 3-currentPlayer;
            takeTurn();
        }
        gw.render(gb.getGameBoard(), legalMoves, currentPlayer, gb.getScore());

        if(players[0] != PlayerType.HUMAN && players[1] != PlayerType.HUMAN)
            gb.printBoardToConsole();
        //first player's turn
        if(currentPlayer == 1){
            if(gb.isPlayerOneNoMove()){
                currentPlayer = 2;
                takeTurn();
            }
            if(players[0] == PlayerType.HUMAN){
                humanTurn(legalMoves);
            }
            else{
                AITurn(players[0]);
            }
        }
        //second player's turn
        else if(currentPlayer == 2){
            //if no move is available, skip turn
            if(gb.isPlayerTwoNoMove()){
                currentPlayer = 1;
                takeTurn();
            }
            if(players[1] == PlayerType.HUMAN){
                humanTurn(legalMoves);
            }
            else{
                AITurn(players[1]);
            }

        }

    }


    private void AITurn(PlayerType type){
        if(type == PlayerType.PURE){
            mcts.setCurrentPlayer(this.currentPlayer);
            mcts.setGameBoard(this.gb);
            mcts.setAI(type);
            mcts.advanceGameTree();
            int[] coord = mcts.getNextMove();
            if(coord != null)
                gb.updatePosition(coord[0], coord[1], currentPlayer);

        }
        else if(type == PlayerType.MINMAX){
            MinMax mm = new MinMax(this.gb, currentPlayer);
            int[] coord = mm.findNextMove();
            if(coord != null)
                gb.updatePosition(coord[0], coord[1], currentPlayer);
            mm = null;
        }

        if(this.currentPlayer == 1) this.currentPlayer = 2;
        else if(this.currentPlayer == 2) this.currentPlayer = 1;
        takeTurn();

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
        gb.updatePosition(coord[1], coord[0], this.currentPlayer);
        if(this.currentPlayer == 1) this.currentPlayer = 2;
        else if(this.currentPlayer == 2) this.currentPlayer = 1;
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
            System.out.println("Stopping");
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
