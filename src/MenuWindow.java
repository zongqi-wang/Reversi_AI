import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuWindow extends JFrame {

    private JButton newGameButton;
    private JButton rulesButton;
    private JButton aboutButton;
    private JButton exitGameButton;
    private JPanel menuPanel;

    private Game g;

    public void clearFrame(JFrame f){
        f.remove(menuPanel);
        f.revalidate();
        f.repaint();
    }

    public MenuWindow(JFrame rootFrame, Game game){
        this.g = game;
        rootFrame.add(menuPanel);
        newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setGameState(GameState.PLAYER_1);
                clearFrame(rootFrame);

            }
        });
        exitGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rootFrame.dispose();
                System.exit(0);
            }
        });
    }

    public JPanel getPanel(){
        return menuPanel;
    }

}
