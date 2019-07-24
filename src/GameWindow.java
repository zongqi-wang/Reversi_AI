import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameWindow extends Canvas {

    private JFrame frame;

    private JPanel rootPanel;

    private Game game;
    private GameState gs;

    public GameWindow(int width, int height, String title, Game game){

        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //frame.add(game);
        //game.start();

        this.game = game;

    }

    public void updateGameWindow(){
        gs = game.getGameState();
        switch(gs){
            case MAIN_MENU:
                MenuWindow menu = new MenuWindow(frame, game);
                frame.add(menu.getPanel());
                frame.setVisible(true);
                break;
            case ABOUT:
                break;
            case RULE:
                break;
            case PLAYER_1:
                new PlayerOne(frame, game);
            case PLAYER_2:
                break;
            case GAME:
                break;
        }
    }

    public void clearFrame(JPanel panel){
        frame.remove(panel);
        frame.revalidate();
        frame.repaint();
    }

//    public void renderPanel(){
//        this.gs = ggame.getGameState();    }

//    public void tick(){
////        switch(gs){
////            case MAIN_MENU:
////                MenuWindow menu = new MenuWindow(frame, game);
////                break;
////            case ABOUT:
////                break;
////            case RULE:
////                break;
////            case PLAYER_1:
////                System.out.println("case switched");
////                PlayerOne p1 = new PlayerOne(frame, game);
////            case PLAYER_2:
////                break;
////            case GAME:
////                break;
////        }
//
//    }
//
//    public void render(Graphics g){
//        System.out.println("called");
//        this.gs = game.getGameState();
//        switch(this.gs){
//            case MAIN_MENU:
//                System.out.println("rendering");
//                g.setColor(Color.white);
//                g.fillRect(0, 0, WIDTH, HEIGHT);
//                g.drawRect(200, 100, 100, 64);
//                break;
//            case ABOUT:
//                break;
//            case RULE:
//                break;
//            case PLAYER_1:
//                System.out.println("case switched");
//                PlayerOne p1 = new PlayerOne(frame, game);
//            case PLAYER_2:
//                break;
//            case GAME:
//                break;
//        }
//
//    }
}
