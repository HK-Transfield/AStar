import java.util.ArrayList;

public class starPath {
    private ArrayList<star> path;

    public starPath(star star){
        this.path = new ArrayList<star>();
        this.path.add(star);
    }

    public double getEuclidean(){
        return getLastStar().getDistance();
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

    public void addStar(star star){
        this.path.add(star);
    }

    public star getLastStar(){
        return this.path.get(path.size()-1);
    }

    public ArrayList<star> getPath(){
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
    private static double getHyperlaneDistance(star start, star end){
        double horizontalDistance = start.getX() - end.getX();
        double verticalDistance = start.getY() - end.getY();
        return Math.sqrt(horizontalDistance * horizontalDistance + verticalDistance * verticalDistance);
    }
}
