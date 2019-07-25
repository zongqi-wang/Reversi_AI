import java.util.*;


public class Node {

    Node parent;
    List<Node> children;
    public int depth, w, n, t;


    public Node(){
        this.depth = 0;
        this.w = 0;
        this.n = 0;
        this.t = 0;
        this.parent = null;
        this.children =  new ArrayList<Node>();
    }

    public Node(Node parent){
        this.parent = parent;
        this.depth = parent.depth+1;
        this.t = parent.n;
        this.n = 0;
        this.w = 0;
        this.children = new ArrayList<Node>();
    }
}
