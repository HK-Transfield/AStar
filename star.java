import java.util.ArrayList;

public class star {
    private double x;
    private double y;
    private ArrayList<star> hyperlanes;
    private double distanceFromEndpoint;
    private int index;

    public star(double x, double y, double endX, double endY, int index){
        this.x = x;
        this.y = y;
        this.index = index;
        double horizontalDistanceFromEnd = endX - x;
        double verticalDistanceFromEnd = endY - y;
        this.distanceFromEndpoint = Math.sqrt(horizontalDistanceFromEnd * horizontalDistanceFromEnd + verticalDistanceFromEnd * verticalDistanceFromEnd);
        this.hyperlanes = new ArrayList<star>();
    } 

    public void addHyperlane(star hyperlane){
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

    public ArrayList<star> getHyperlanes(){
        return this.hyperlanes;
    }

    public double getDistance(){
        return this.distanceFromEndpoint;
    }
}