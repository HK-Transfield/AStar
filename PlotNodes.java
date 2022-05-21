import javafx.application.Application;
import static javafx.application.Application.launch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Opens a window to display a plot showing all the stars and will
 * also show the shortest path found.
 */
public class PlotNodes extends Application {

    /**
     * These store x and y coordinates from the CSV.
     * * Since it is exepcted that the a* will return a set of
     * * nodes it has visited, it should also be stored in
     * * ArrayLists as well, which will be used to
     */
    static ArrayList<Double> xCoords = new ArrayList<Double>();
    static ArrayList<Double> yCoords = new ArrayList<Double>();

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

        // Creating the Scatter chart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setLegendVisible(false);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.getXAxis().setVisible(false);
        lineChart.getYAxis().setVisible(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        for (int i = 0; i < xCoords.size(); i++) {
            series.getData().add(new XYChart.Data<>(xCoords.get(i), yCoords.get(i)));
        }

        // example data to test that it draws correctly
        XYChart.Series<Number, Number> traversedSeries = new XYChart.Series<Number, Number>();
        traversedSeries.getData().add(new XYChart.Data<>(34.90, 13.05));
        traversedSeries.getData().add(new XYChart.Data<>(92.02, 22.29));
        traversedSeries.getData().add(new XYChart.Data<>(33.82, 10.20));
        traversedSeries.getData().add(new XYChart.Data<>(92.66, 27.09));
        traversedSeries.getData().add(new XYChart.Data<>(29.39, 46.94));
        traversedSeries.getData().add(new XYChart.Data<>(99.73, 33.70));
        lineChart.getData().addAll(series, traversedSeries);

        /*---------------------------------------------------------------------------*/

        Pane root = new Pane();
        root.getChildren().add(lineChart);

        // Creating a scene object
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().addAll(getClass().getResource("stylesheet.css").toExternalForm());

        // Setting title to the Stage
        stage.setTitle("A* Search Algorithm");

        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();
    }

    public static void main(String args[]) {
        String line = "";
        String split = ",";
        System.out.println("javafx.runtime.version: " + System.getProperty("javafx.runtime.version"));

        try {
            File f = new File("spiral_v2.csv");
            BufferedReader br = new BufferedReader(new FileReader(f));

            line = br.readLine();
            while (line != null) {
                String[] coords = line.split(split);

                xCoords.add(Double.valueOf(coords[0]));
                yCoords.add(Double.valueOf(coords[1]));

                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        launch(args); // start JavaFX application
    }
}