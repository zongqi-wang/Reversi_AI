import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private final int VERTICAL_OFFSET = 30;
    private int[][] legalMoves;
    public boolean inputReceived = false;
    public int row, col;

    public MouseInput(int[][] legalMoves){

        this.legalMoves = legalMoves;
    }

    public int[] getCoord(){
        int[] coord = {row, col};
        return coord;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        col = e.getX()/100;
        row = (e.getY()-VERTICAL_OFFSET)/100;

        if(legalMoves[row][col] != 1){
            inputReceived = true;
        }
    }
}
