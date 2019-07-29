import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;

public class MouseInput extends MouseAdapter {

    private final int VERTICAL_OFFSET = 30;
    private int[][] legalMoves;
    private boolean inputReveiced = false;
    private int[] coord;

    public MouseInput(int[][] legalMoves){

        this.legalMoves = legalMoves;
        coord = new int[2];
    }

    public int[] getCoord(){
        return coord;
    }

    public boolean received(){
        return inputReveiced;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int col = e.getX()/100;
        int row = (e.getY()-VERTICAL_OFFSET)/100;


        if(legalMoves[row][col] != 0){
            coord[0] = col;
            coord[1] = row;
            inputReveiced = true;
        }
    }
}
