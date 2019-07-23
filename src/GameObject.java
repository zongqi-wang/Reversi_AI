import java.awt.*;

/**
 *
 */
public abstract class GameObject {


    protected int player;
    protected int row;
    protected int col;

    public GameObject(int row, int col, int player){
        this.col = col;
        this.row = row;
        this.player = player;
    }

    public abstract void tick();
    public abstract void render();

    public abstract void render(Graphics g);
}
