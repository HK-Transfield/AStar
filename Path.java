import java.util.ArrayList;

/**
 * @author Harmon Transfield (1317381), Edward Wang (1144995)
 */
public class Path {
    private ArrayList<Node> path;

    public Path(Node star){
        this.path = new ArrayList<Node>();
        this.path.add(star);
    }

    public double getEuclidean(){
        return getLastNode().getDistance();
    }

    public double getPathLength(){
        double pathLength = 0;

        if (path.size() < 2){
            return pathLength;
        }

        for (int i = 0; i < path.size() - 1; i++){
            double length = getHyperlaneDistance(path.get(i), path.get(i+1));
            pathLength += length;
        }
        return pathLength;
    }

    public double getWeight(){
        return getEuclidean() + getPathLength();
    }

    public void addNode(Node node){
        this.path.add(node);
    }

    public Node getLastNode(){
        return this.path.get(path.size()-1);
    }

    public ArrayList<Node> getPath(){
        return this.path;
    }

    public ArrayList<Integer> getPrettyPath(){
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < path.size(); i++){
            indices.add(path.get(i).getIndex());
        }
        return indices;
    }

    // ugh, code duplication
    private static double getHyperlaneDistance(Node start, Node end){
        double horizontalDistance = start.getX() - end.getX();
        double verticalDistance = start.getY() - end.getY();
        return Math.sqrt(horizontalDistance * horizontalDistance + verticalDistance * verticalDistance);
    }
}
