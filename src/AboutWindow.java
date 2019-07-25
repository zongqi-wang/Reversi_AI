import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutWindow {
    private JTextField textField1;
    private JPanel aboutPanel;
    private JButton backButton;

    private Game g;
    public AboutWindow(JFrame f, Game game){

        this.g  = game;
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                g.setGameState(GameState.MAIN_MENU);
                clearFrame(f);
            }
        });
    }

    public JPanel getAboutPanel(){
        return aboutPanel;
    }

    public void clearFrame(JFrame f){
        f.remove(aboutPanel);
        f.revalidate();
        f.repaint();
    }


}
