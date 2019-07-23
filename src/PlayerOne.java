import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerOne {
    private JButton humanButton;
    private JButton easyAIButton;
    private JButton hardAIButton;
    private JPanel playerOnePanel;
    private JButton backButton;

    private Game g;


    public void clearFrame(JFrame f){
        f.remove(playerOnePanel);
        f.revalidate();
        f.repaint();
    }
    public PlayerOne(JFrame frame, Game game){
        this.g = game;
        frame.add(playerOnePanel);


        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setGameState(GameState.MAIN_MENU);
                clearFrame(frame);
            }
        });
    }
}
