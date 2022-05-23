import java.util.ArrayList;

/**
 * @author Harmon Transfield (1317381), Edward Wang (1144995)
 */
public class Node {
    private double x;
    private double y;
    private ArrayList<Node> hyperlanes;
    private double distanceFromEndpoint;
    private int index;

    /**
     * Constructor. Instatiates a new Node in the Graph.
     */
    public Node(double x, double y, double endX, double endY, int index){
        this.x = x;
        this.y = y;
        this.index = index;
        double horizontalDistanceFromEnd = endX - x;
        double verticalDistanceFromEnd = endY - y;
        this.distanceFromEndpoint = Math.sqrt(horizontalDistanceFromEnd * horizontalDistanceFromEnd + verticalDistanceFromEnd * verticalDistanceFromEnd);
        this.hyperlanes = new ArrayList<Node>();
    } 

    public void addHyperlane(Node hyperlane){
        this.hyperlanes.add(hyperlane);
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public int getIndex(){
        return this.index;
    }

    public ArrayList<Node> getHyperlanes(){
        return this.hyperlanes;
    }

    public double getDistance(){
        return this.distanceFromEndpoint;
    }
}