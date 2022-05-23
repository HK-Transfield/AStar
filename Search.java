import java.util.ArrayList;
import java.util.Collections;

/**
 * Performs a A* Search algorithm.
 * 
 * @author Harmon Transfield (1317381), Edward Wang (1144995)
 */
public class Search {

    // declare class member variables
    private ArrayList<String> _lines;
    private int _startIndex;
    private int _endIndex;
    private double _maxHyperlaneDistance;

    private ArrayList<Node> galaxyMap = new ArrayList<Node>();

    /**
     * Constructor. Instatiates a new Search
     */
    public Search(ArrayList<String> lines, int startIndex, int endIndex, double maxHyperlaneDistance) {
        _lines = lines;
        _startIndex = startIndex - 1;
        _endIndex = endIndex - 1;
        _maxHyperlaneDistance = maxHyperlaneDistance;
    }

    /**
     * Start point for A* algorithm.
     * 
     * @return An ArrayList of all traversed paths.
     */
    public ArrayList<Node> optimalRoute() {

        // prepare ArrayList for algorithm
        generateInitialNodes(_endIndex);
        generateHyperlanes(_maxHyperlaneDistance);

        // run the A* search algorithm
        Path path = generatePath(_startIndex);
        ArrayList<Node> stars = path.getPath();

        // Spit out the path
        System.out.println("PATH FOUND: ");
        for (int i = 0; i < stars.size(); i++){
            int starIndex = stars.get(i).getIndex() + 1;
            System.out.print(starIndex + " -> ");
        }
        System.out.print("FINISHED\n");
        return stars;
    }

    /**
     * Generates all Nodes with coordinates and euclidean weights. 
     */
    private void generateInitialNodes(int endIndex){
        
        // identify the end node
        String rawEndStar = _lines.get(endIndex);
        double endX = Double.parseDouble(rawEndStar.split(",")[0]);
        double endY = Double.parseDouble(rawEndStar.split(",")[1]);
        
        for (int i = 0; i < _lines.size(); i++){
            String rawStar = _lines.get(i);
            double x = Double.parseDouble(rawStar.split(",")[0]);
            double y = Double.parseDouble(rawStar.split(",")[1]);

            Node NewStar = new Node(x, y, endX, endY, i);
            galaxyMap.add(NewStar);
        }
    }

    /**
     * Adds hyperlane between each valid pair of Nodes.
     */
    private void generateHyperlanes(double maxHyperlaneDistance){
        for (int i = 0; i < galaxyMap.size(); i++){
            Node start = galaxyMap.get(i);

            for (int j = 0; j < galaxyMap.size(); j++){

                if (i != j){
                    Node end = galaxyMap.get(j);
                    double dist = getHyperlaneDistance(start, end);

                    if (dist <= maxHyperlaneDistance){
                        start.addHyperlane(end);
                    }
                }
            }
        }
    }

    /**
     * Get the distance of the hyperlane between two stars.
     */
    private double getHyperlaneDistance(Node start, Node end){
        double horizontalDistance = start.getX() - end.getX();
        double verticalDistance = start.getY() - end.getY();
        return Math.sqrt(horizontalDistance * horizontalDistance + verticalDistance * verticalDistance);
    }
    
    /**
     * Performs the A* algorithmn.
     */
    private Path generatePath(int start){
        System.out.println("Starting search...");
        Node startStar = galaxyMap.get(start);

        // initializes stack by generating a starpath with one star - the start star
        Path first = new Path(startStar);
        ArrayList<Path> stack = new ArrayList<Path>();
        stack.add(first);  

        int nextIter = 0;
        // disgusting boolean equality comparisons
        double currentDistance = stack.get(nextIter).getLastNode().getDistance();
        while (currentDistance > 0.0001){
           
            expandPath(nextIter, stack);
            nextIter = getBestPathIndex(stack);
            currentDistance = stack.get(nextIter).getLastNode().getDistance();
        }

        return stack.get(nextIter);
    }
    
    /**
     * Gets the index of the path in the stack with the lowest weight.
     *  
     * @param stack
     * @return
     */
    private int getBestPathIndex(ArrayList<Path> stack){
        Path nextPath = stack.get(0);
        int nextPathIndex = -1;
        for (int i = 0; i < stack.size(); i++){
            if (stack.get(i).getWeight() <= nextPath.getWeight()){
                nextPath = stack.get(i);
                nextPathIndex = i;
            }
        }
        return nextPathIndex;
    }

    
    /**
     * Gets a path at an index, expands that path into child paths, removes the original path.

     */
    private void expandPath(int expansionIndex, ArrayList<Path> stack){

        Path workingPath = stack.get(expansionIndex);
        ArrayList<Node> nextStars = workingPath.getLastNode().getHyperlanes();

        for (int i = 0; i < nextStars.size(); i++){
            // create a copy of the current star path
            Path newPath = new Path(workingPath.getPath().get(0));

            for (int j = 1; j < workingPath.getPath().size(); j++){
                newPath.addNode(workingPath.getPath().get(j));
            }

            // tack on our new star if it doesnt already exist, so as to avoid circles
            if (!newPath.getPrettyPath().contains(nextStars.get(i).getIndex())){
                newPath.addNode(nextStars.get(i));
                // System.out.println("Adding new path " + newPath.getPrettyPath().toString());
                // add our new path to the stack
                stack.add(newPath);
                
            }
        }
        // finally, remove the original path from the stack
        stack.remove(expansionIndex);

        // premature optimization: look at ones added first
        Collections.reverse(stack);
    }
}
