import java.util.ArrayList;

/**
 * Representation of the graph the program will traverse.
 * 
 * @author Harmon Transfield (1317381), Edward Wang (1144995)
 */
public class Node {

    // declare class properties
    private double x;
    private double y;
    private ArrayList<Node> traversals;
    private double distanceFromEndpoint;
    private int index;

    /**
     * Constructor. Instatiates a new Node in the Graph.
     */
    public Node(double x, double y, double endX, double endY, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.traversals = new ArrayList<Node>();

        // calculate euclidean distance
        double horizontalDistanceFromEnd = endX - x;
        double verticalDistanceFromEnd = endY - y;
        this.distanceFromEndpoint = Math.sqrt(horizontalDistanceFromEnd * horizontalDistanceFromEnd
                + verticalDistanceFromEnd * verticalDistanceFromEnd);
    }

    public void addTraversal(Node traversal) {
        this.traversals.add(traversal);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getIndex() {
        return this.index;
    }

    public ArrayList<Node> getTraversals() {
        return this.traversals;
    }

    public double getDistance() {
        return this.distanceFromEndpoint;
    }
}