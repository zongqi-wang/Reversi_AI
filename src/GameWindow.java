import javax.swing.*;
import java.awt.*;

public class GameWindow extends Canvas{



    private JFrame frame;
    private JPanel rootPanel;

    public final int WIDTH = 1200;
    public final int HEIGHT = 825;

    private Game game;
    private GameState gs;

    private final int MAX_ROW = 8;
    private final int MAX_COL = 8;

    private CanvasPanel canvasPanel = new CanvasPanel();
    private int[][] gameBoard;
    private int[][] legalMoves;
    private int[] scores;
    private int turn;

    private int row, col;
    public final int VERTICAL_OFFSET = 30;

    public GameWindow(String title, Game game){

        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //frame.add(game);
        //game.start();

        this.game = game;

    }

    /**
     * This function takes care of switching menu screens when buttons are pressed and game state changed
     *
     */
    public void updateGameWindow(){
        this.gs = game.getGameState();
        switch(gs){
            case MAIN_MENU:
                MenuWindow menu = new MenuWindow(frame, game);
                frame.add(menu.getPanel());
                frame.setVisible(true);
                break;

            case ABOUT:
                AboutWindow about = new AboutWindow(frame, game);
                frame.add(about.getAboutPanel());
                //frame.setVisible(true);
                break;

            case RULE:
                RulesWindow rules = new RulesWindow(frame, game);
                frame.add(rules.getRulesPanel());
                //frame.setVisible(true);
                break;

            case PLAYER_1:
                PlayerOne p1w = new PlayerOne(frame, game);
                frame.add(p1w.getPlayerOnePanel());

                break;

            case PLAYER_2:
                PlayerTwo p2w = new PlayerTwo(frame, game);
                frame.add(p2w.getPlayerTwoPanel());
                break;

            case GAME:
                frame.add(canvasPanel);
                game.takeTurn();

                break;
        }
    }

    public void render(int[][] game, int[][] legal, int turn, int[] scores){
        this.gameBoard = game;
        this.legalMoves = legal;
        this.scores = scores;
        this.turn = turn;
        canvasPanel.repaint();
    }

    public void addMouse(MouseInput mouse){
        frame.addMouseListener(mouse);
    }

    public void removeMouse(MouseInput mouse){
        frame.removeMouseListener(mouse);
    }

//    public void getHumanInput(){
//
//        MouseInput mouse = new MouseInput(legalMoves);
//
//
////        frame.addMouseListener(mouse);
////        try {
////            Thread input = new Thread(new HumanInput(mouse));
////            input.start();
////            synchronized (mouse){
////                input.wait();
////            }
////            input.wait();
////        } catch (InterruptedException e) {
////            //Handle exception
////        }
////
////
////        int[] coord = mouse.getCoord();
////        frame.removeMouseListener(mouse);
////        frame.addMouseListener(new MouseAdapter() {
////            @Override
////            public void mousePressed(MouseEvent e) {
////
////                col = e.getX()/100;
////                row = (e.getY()-VERTICAL_OFFSET)/100;
////
////            }
////        });
//
//    }




    public JFrame getFrame(){
        return this.frame;
    }


    public void clearFrame(JPanel panel){
        frame.remove(panel);
        frame.revalidate();
        frame.repaint();
    }



    public class CanvasPanel extends JPanel{
        public CanvasPanel(){
            setBackground(Color.WHITE);
        }


        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(31,163,24));
            g.fillRect(0,0, 800, 800);

            int gridWidth = 100;
            //painting the grid
            g.setColor(Color.BLACK);

            for(int i = 1; i < 8; i++){
                g.drawLine(0, gridWidth*i, 800, gridWidth*i);
                g.drawLine(gridWidth*i, 0, gridWidth*i, 800);
            }

            //painting player and legal moves
            for(int row = 0; row < 8; row++){
                for(int col = 0; col < 8; col++){

                    if(gameBoard[row][col] == 1){
                        g.setColor(Color.BLACK);
                        g.fillOval(col*gridWidth+10, row*gridWidth+10, 80, 80);
                    }
                    else if(gameBoard[row][col] == 2){
                        g.setColor(Color.WHITE);
                        g.fillOval(col*gridWidth+10, row*gridWidth+10, 80, 80);
                    }

                    if(legalMoves[row][col] != 0){
                        g.setColor(Color.BLACK);
                        g.drawOval(col*gridWidth+10, row*gridWidth+10, 80, 80);
                    }
                }
            }
            g.setColor(new Color(78, 154, 242));
            g.fillRect(800,0, 400, 800);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 70));
            g.drawString("Turn", 915, 100);
            g.drawString("Score", 900, 400);

//            g.setColor(Color.BLACK);
            g.fillOval(900,450, 80, 80);
            g.drawString(Integer.toString(scores[0]), 1020, 510);
            g.drawString(Integer.toString(scores[1]), 1020, 650);
            if(turn == 1)
                g.fillOval(950, 150, 80, 80);

            g.setColor(Color.WHITE);
            g.fillOval(900,580, 80, 80);

            if(turn == 2)
                g.fillOval(940, 150, 80, 80);

        }
    }



}
