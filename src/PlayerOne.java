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


    /**
     *
     * @param f
     */
    public void clearFrame(JFrame f){
        f.remove(playerOnePanel);
        f.revalidate();
        f.repaint();
    }

    /**
     *
     * @return
     */
    public JPanel getPlayerOnePanel() {
        return playerOnePanel;
    }

    /**
     *
     * @param frame
     * @param game
     */
    public PlayerOne(JFrame frame, Game game){
        this.g = game;
        //frame.add(playerOnePanel);


        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setGameState(GameState.MAIN_MENU);
                clearFrame(frame);
            }
        });
        humanButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setPlayerOne(PlayerType.HUMAN);
                g.setGameState(GameState.PLAYER_2);
                clearFrame(frame);
            }
        });
        easyAIButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setPlayerOne(PlayerType.PURE);
                g.setGameState(GameState.PLAYER_2);
                clearFrame(frame);
            }
        });
        hardAIButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setPlayerOne(PlayerType.MINMAX);
                g.setGameState(GameState.PLAYER_2);
                clearFrame(frame);
            }
        });
    }
}
