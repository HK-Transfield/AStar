import java.util.ArrayList;
import java.util.Collections;

/**
 * Performs a A* Search algorithm.
 * The actual algorithm occurs in the
 * method findOptimalPath()
 * 
 * @author Harmon Transfield (1317381), Edward Wang (1144995)
 */
public class Search {

    // declare class properties
    private ArrayList<String> _lines;
    private int _startIndex;
    private int _endIndex;
    private double _maxTraversalDistance;

    // declare class scopr variables
    private ArrayList<Node> map = new ArrayList<Node>();

    /**
     * Constructor. Instantiates a new Search.
     */
    public Search(ArrayList<String> lines, int startIndex, int endIndex, double maxTraversalDistance) {
        _lines = lines;
        _startIndex = startIndex - 1;
        _endIndex = endIndex - 1;
        _maxTraversalDistance = maxTraversalDistance;
    }

    /// A* Algorithm Code
    // -----------------------------------------------------------------

    /**
     * Performs the A* algorithmn.
     */
    private Path findOptimalPath(int start) {
        System.out.println("Starting search...");
        Node startNode = map.get(start);

        // initializes stack by generating a path with one node - the start node
        Path startPath = new Path(startNode);
        ArrayList<Path> stack = new ArrayList<Path>();
        stack.add(startPath);

        int nextIter = 0;

        // boolean equality comparisons
        double currentDistance = stack.get(nextIter).getLastNode().getDistance();

        while (currentDistance > 0.0001) {

            expandPath(nextIter, stack);
            nextIter = getBestPathIndex(stack);
            currentDistance = stack.get(nextIter).getLastNode().getDistance();
        }

        return stack.get(nextIter);
    }

    /**
     * Gets the index of the path in the stack with the lowest weight.
     */
    private int getBestPathIndex(ArrayList<Path> stack) {

        if (stack.isEmpty()) { // no paths can be found
            System.out.println("No suitable path is found");
            System.exit(0);
        }

        Path nextPath = stack.get(0);
        int nextPathIndex = -1;

        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getWeight() <= nextPath.getWeight()) {
                nextPath = stack.get(i);
                nextPathIndex = i;
            }
        }
        return nextPathIndex;
    }

    /**
     * Gets a path at an index, expands that path into child paths,
     * removes the original path.
     * 
     */
    private void expandPath(int expansionIndex, ArrayList<Path> stack) {

        Path workingPath = stack.get(expansionIndex);
        ArrayList<Node> nextStars = workingPath.getLastNode().getHyperlanes();

        for (int i = 0; i < nextStars.size(); i++) {
            // create a copy of the current star path
            Path newPath = new Path(workingPath.getPath().get(0));

            for (int j = 1; j < workingPath.getPath().size(); j++) {
                newPath.addNode(workingPath.getPath().get(j));
            }

            // tack on our new star if it doesnt already exist, so as to avoid circles
            if (!newPath.getPrettyPath().contains(nextStars.get(i).getIndex())) {
                newPath.addNode(nextStars.get(i));

                // add our new path to the stack
                stack.add(newPath);

            }
        }
        // finally, remove the original path from the stack
        stack.remove(expansionIndex);

        // premature optimization: look at ones added first
        Collections.reverse(stack);
    }
    // -----------------------------------------------------------------

    /**
     * Start point for A* algorithm.
     * 
     * @return An ArrayList of all traversed paths.
     */
    public ArrayList<Node> startSearch() {

        // prepare ArrayList for algorithm
        setInitialNodes(_endIndex);
        setTraversal(_maxTraversalDistance);

        // run the A* search algorithm
        Path path = findOptimalPath(_startIndex);

        // retrieve final list of all visited nodes
        ArrayList<Node> visited = path.getPath();

        // Spit out the path
        System.out.print("PATH FOUND: ");
        for (int i = 0; i < visited.size(); i++) {
            int visitedIndex = visited.get(i).getIndex() + 1;
            System.out.print(visitedIndex + " -> ");
        }
        System.out.print("FINISHED\n");
        return visited;
    }

    /**
     * Generates all Nodes with coordinates and euclidean weights.
     */
    private void setInitialNodes(int endIndex) {

        // identify the end node
        String endLine = _lines.get(endIndex);
        double endX = Double.parseDouble(endLine.split(",")[0]);
        double endY = Double.parseDouble(endLine.split(",")[1]);

        for (int i = 0; i < _lines.size(); i++) { // create new nodes
            String line = _lines.get(i);
            double x = Double.parseDouble(line.split(",")[0]);
            double y = Double.parseDouble(line.split(",")[1]);

            Node newNode = new Node(x, y, endX, endY, i);
            map.add(newNode);
        }
    }

    /**
     * Adds hyperlane between each valid pair of Nodes.
     */
    private void setTraversal(double maxTraversalDistance) {
        for (int i = 0; i < map.size(); i++) {
            Node start = map.get(i);

            for (int j = 0; j < map.size(); j++) {

                if (i != j) {
                    Node end = map.get(j);
                    double dist = getTraversalDistance(start, end);

                    if (dist <= maxTraversalDistance) {
                        start.addTraversal(end);
                    }
                }
            }
        }
    }

    /**
     * Get the distance of the hyperlane between two stars.
     */
    private double getTraversalDistance(Node start, Node end) {
        double horizontalDistance = start.getX() - end.getX();
        double verticalDistance = start.getY() - end.getY();
        return Math.sqrt(horizontalDistance * horizontalDistance + verticalDistance * verticalDistance);
    }

}
