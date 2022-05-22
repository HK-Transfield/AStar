import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class a {
    // just research jump drives bro, the unbidden are gonna spawn anyway

    public static void main(String[] args) throws FileNotFoundException{

        // defining vars
        String galaxyFile = args[0];
        int startIndex = Integer.parseInt(args[1]);
        int endIndex = Integer.parseInt(args[2]);
        double maxHyperlaneDistance = Double.parseDouble(args[3]);

        // process the galaxy map
        ArrayList<star> galaxyMap = processGalaxyMap(galaxyFile, maxHyperlaneDistance, endIndex);
        // verify hyperlane generation
        // for (int i = 0; i< 10; i++){
        //     ArrayList<star> hyperlanes = galaxyMap.get(i).getHyperlanes();
        //     System.out.println(hyperlanes.size());
        // }

        // run the A* search algorithm
        starPath path = generatePath(startIndex, galaxyMap);
        ArrayList<star> stars = path.getPath();

        // Spit out the path
        System.out.println("PATH FOUND: ");
        for (int i = 0; i < stars.size(); i++){
            int starIndex = stars.get(i).getIndex();
            System.out.println(starIndex);
        }

    }

    // Converts the CSV file with all stars into an array of stars, and generates hyperlanes between all stars
    private static ArrayList<star> processGalaxyMap(String galaxyFile, double maxHyperlaneDistance, int endIndex) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(galaxyFile));
        ArrayList<String> rawGalaxy = new ArrayList<String>();
        while (scan.hasNextLine()) {
            String newstar = scan.nextLine();
            rawGalaxy.add(newstar);
        }
        scan.close();
        ArrayList<star> galaxyMap = new ArrayList<star>();
        generateInitialStars(endIndex, galaxyMap, rawGalaxy);
        generateHyperlanes(maxHyperlaneDistance, galaxyMap);
        return galaxyMap;
    }


    // generates all stars with coords and euclidean weights
    private static void generateInitialStars(int endIndex, ArrayList<star> galaxyMap, ArrayList<String> rawGalaxy){
        String rawEndStar = rawGalaxy.get(endIndex);
        double endX = Double.parseDouble(rawEndStar.split(",")[0]);
        double endY = Double.parseDouble(rawEndStar.split(",")[1]);
        for (int i = 0; i < rawGalaxy.size(); i++){
            String rawStar = rawGalaxy.get(i);
            double x = Double.parseDouble(rawStar.split(",")[0]);
            double y = Double.parseDouble(rawStar.split(",")[1]);
            star NewStar = new star(x, y, endX, endY, i);
            galaxyMap.add(NewStar);
        }
    }

    // adds hyperlanes between each valid pair of stars
    private static void generateHyperlanes(double maxHyperlaneDistance, ArrayList<star> galaxyMap){
        for (int i = 0; i < galaxyMap.size(); i++){
            star start = galaxyMap.get(i);
            for (int j = 0; j < galaxyMap.size(); j++){
                if (i != j){
                    star end = galaxyMap.get(j);
                    double dist = getHyperlaneDistance(start, end);
                    if (dist <= maxHyperlaneDistance){
                        start.addHyperlane(end);
                    }
                }
            }
        }
    }

    // get the distance of the hyperlane between two stars
    private static double getHyperlaneDistance(star start, star end){
        double horizontalDistance = start.getX() - end.getX();
        double verticalDistance = start.getY() - end.getY();
        return Math.sqrt(horizontalDistance * horizontalDistance + verticalDistance * verticalDistance);
    }
    
    // this is the A* algorithm
    private static starPath generatePath(int start, ArrayList<star> galaxyMap){
        System.out.println("Starting search");
        star startStar = galaxyMap.get(start);

        // initializes stack by generating a starpath with one star - the start star
        starPath first = new starPath(startStar);
        ArrayList<starPath> stack = new ArrayList<starPath>();
        stack.add(first);  

        int nextIter = 0;
        // disgusting boolean equality comparisons
        double currentDistance = stack.get(nextIter).getLastStar().getDistance();
        while (currentDistance > 0.0001){
            // System.out.print("current path: ");
            // System.out.println(stack.get(nextIter).getPrettyPath().toString());
            // System.out.println("distance from end: " + currentDistance);
            expandPath(nextIter, stack);
            nextIter = getBestPathIndex(stack);
            currentDistance = stack.get(nextIter).getLastStar().getDistance();
        }

        return stack.get(nextIter);
    }


    // gets the index of the path in the stack with the lowest weight
    private static int getBestPathIndex(ArrayList<starPath> stack){
        starPath nextPath = stack.get(0);
        int nextPathIndex = -1;
        for (int i = 0; i < stack.size(); i++){
            // System.out.println("Best weight: " + nextPath.getPrettyPath().toString() + " " + nextPath.getWeight());
            
            // System.out.println("Test weight: " + stack.get(i).getPrettyPath().toString() + " " + stack.get(i).getWeight());
            if (stack.get(i).getWeight() <= nextPath.getWeight()){
                nextPath = stack.get(i);
                nextPathIndex = i;
            }
        }
        // System.out.println("index of next path: " + nextPathIndex);
        return nextPathIndex;
    }

    // gets a path at an index, expands that path into child paths, removes the original path
    private static void expandPath(int expansionIndex, ArrayList<starPath> stack){
        // System.out.println("Expanding path at index " + expansionIndex);
        starPath workingPath = stack.get(expansionIndex);
        ArrayList<star> nextStars = workingPath.getLastStar().getHyperlanes();

        for (int i = 0; i < nextStars.size(); i++){
            // create a copy of the current star path
            starPath newPath = new starPath(workingPath.getPath().get(0));
            for (int j = 1; j < workingPath.getPath().size(); j++){
                newPath.addStar(workingPath.getPath().get(j));
            }
            // tack on our new star if it doesnt already exist, so as to avoid circles
            if (!newPath.getPrettyPath().contains(nextStars.get(i).getIndex())){
                newPath.addStar(nextStars.get(i));
                // System.out.println("Adding new path " + newPath.getPrettyPath().toString());
                // add our new path to the stack
                stack.add(newPath);
                
            }
        }
        // finally, remove the original path from the stack
        stack.remove(expansionIndex);
        // premature optimization - i assume that we want to look at the ones we just added first
        Collections.reverse(stack);
        // System.out.println("current stack size: " + stack.size());

    }

}
