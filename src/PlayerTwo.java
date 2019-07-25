import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerTwo {
    private JButton humanButton;
    private JButton easyAIButton;
    private JButton hardAIButton;
    private JPanel playerTwoPanel;
    private JButton backButton;

    private Game g;


    public void clearFrame(JFrame f){
        f.remove(playerTwoPanel);
        f.revalidate();
        f.repaint();
    }

    public JPanel getPlayerTwoPanel(){
        return playerTwoPanel;
    }

    public PlayerTwo(JFrame frame, Game game){
        this.g = game;
//        frame.add(playerTwoPanel);


        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setGameState(GameState.PLAYER_1);
                clearFrame(frame);
            }
        });

        humanButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setPlayerTwo(PlayerType.HUMAN);
                g.setGameState(GameState.GAME);
                clearFrame(frame);
            }
        });
        easyAIButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setPlayerTwo(PlayerType.PURE);
                g.setGameState(GameState.GAME);
                clearFrame(frame);
            }
        });
        hardAIButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clearFrame(frame);
                g.setPlayerTwo(PlayerType.MINMAX);
                g.setGameState(GameState.GAME);

            }
        });
    }
}