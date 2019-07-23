import java.awt.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.logging.Handler;

public class GameHandler {

    LinkedList<GameObject> objList = new LinkedList<GameObject>();

    public void tick(){
        for(int i = 0; i < objList.size(); i++){
            GameObject tempObject = objList.get(i);
            tempObject.tick();

        }


    }

    public void render(Graphics g){
        for(int i = 0; i < objList.size(); i++){
            GameObject tempObject = objList.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object){
        this.objList.add(object);
    }

    public void removeObject(GameObject object){
        this.objList.remove(object);
    }

}

