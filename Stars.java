import javafx.application.Application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

/**
 * Opens a window to display a plot showing all the stars and will
 * also show the shortest path found.
 * 
 * @author Harmon Transfield (1317381), Edward Wang (1144995)
 */
public class Stars extends Application {

    /**
     * These store x and y coordinates from the CSV.
     */
    private static ArrayList<Double> _xCoords = new ArrayList<Double>();
    private static ArrayList<Double> _yCoords = new ArrayList<Double>();
    private static ArrayList<Node> _optimalRoute;

    /**
     * Main entry point for the program.
     * 
     * @param args
     */
    public static void main(String args[]) {
        System.out.println("\nRunning: A* Algorithm");
        System.out.println("javafx.runtime.version: " + System.getProperty("javafx.runtime.version") + "\n");

        if (args.length != 4) { // validate there are cli arguments
            System.out.println("Usage: ./run_star.sh [galaxy_csv_filename] [start_index] [end_index] [D]\n");
            System.exit(1);
        }

        // declare variables
        String line = "";
        String split = ",";

        // assign cli args
        String filename = args[0];
        int startIndex = Integer.parseInt(args[1]);
        int endIndex = Integer.parseInt(args[2]);
        double distance = Double.parseDouble(args[3]);

        try {
            File f = new File(filename);

            if (!f.exists()) { // ensure there is a valid csv file
                System.out.println("Warning: Please enter a valid CSV file\n");
                System.exit(0);
            }

            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<String>();

            line = br.readLine();
            lines.add(line);

            while (line != null) { // store all XY coordinates
                String[] coords = line.split(split);

                _xCoords.add(Double.valueOf(coords[0]));
                _yCoords.add(Double.valueOf(coords[1]));

                line = br.readLine();
                if (line != null)
                    lines.add(line);
            }
            br.close();

            if (endIndex == lines.size() || startIndex == lines.size()) { // check for valid end index
                System.out.println(
                        "Warning: Out of bounds index value. Choose a number between 0-"
                                + (lines.size() - 1) + "\n");
                System.exit(0);
            }

            // begin the A* algorithm
            _optimalRoute = Search.startSearch(lines, startIndex, endIndex, distance);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        launch(args); // start JavaFX application
    }

    /**
     * Main entry point for JavaFX applications.
     * The plot for the A* star algorithm is drawn
     * using data retrieved from any CSV file.
     */
    @Override
    public void start(Stage stage) {
        // Define axes
        NumberAxis xAxis = new NumberAxis(0, 100, 20);
        NumberAxis yAxis = new NumberAxis(0, 100, 20);

        /*---------------------------------------------------------------------------*/

        // Creating the line chart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setLegendVisible(false);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.getXAxis().setVisible(false);
        lineChart.getYAxis().setVisible(false);

        // plot all points from the csv file
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        for (int i = 0; i < _xCoords.size(); i++) {
            series.getData().add(new XYChart.Data<>(_xCoords.get(i), _yCoords.get(i)));
        }

        // plot the route searched by the A* algorithm
        XYChart.Series<Number, Number> traversedSeries = new XYChart.Series<Number, Number>();
        for (int i = 0; i < _optimalRoute.size(); i++) {
            traversedSeries.getData().add(new XYChart.Data<>(_optimalRoute.get(i).getX(), _optimalRoute.get(i).getY()));
        }

        // add data to the line chart
        lineChart.getData().addAll(series, traversedSeries);

        /*---------------------------------------------------------------------------*/

        Pane root = new Pane();
        root.getChildren().add(lineChart);

        // Creating a scene object
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().addAll(getClass().getResource("styles/stylesheet.css").toExternalForm());

        // Setting Stage title
        stage.setTitle("A* Search Algorithm");

        // Adding Stage scene
        stage.setScene(scene);

        // Displaying stage contents
        stage.show();
    }
}