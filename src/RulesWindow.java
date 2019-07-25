import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RulesWindow {

    private JPanel rulesPanel;
    private JButton backButton;
    private JTextArea a1BlackPlayerGoesTextArea;

    private Game g;

    public RulesWindow(JFrame f, Game game) {
        this.g = game;

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setGameState(GameState.MAIN_MENU);
                clearFrame(f);
            }
        });
    }

    /**
     *
     * @param f
     */
    public void clearFrame(JFrame f){
        f.remove(rulesPanel);
        f.revalidate();
        f.repaint();
    }

    /**
     *
     * @return
     */
    public JPanel getRulesPanel(){
        return rulesPanel;
    }


}

