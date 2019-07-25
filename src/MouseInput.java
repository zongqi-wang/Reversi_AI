import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    public MouseInput(){

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX() + " . " + e.getY());
    }
}
