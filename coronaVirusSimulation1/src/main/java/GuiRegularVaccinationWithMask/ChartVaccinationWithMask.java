package GuiRegularVaccinationWithMask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartVaccinationWithMask extends Application {
    public static int timeCounter = 0;
    private boolean maskIndicator = false;
    final int WINDOW_SIZE = 10;

    private static XYChart.Series seriesInfected = new XYChart.Series();
    private static XYChart.Series seriesImmune = new XYChart.Series();
    private static XYChart.Series seriesDeaths = new XYChart.Series();
    private static XYChart.Series seriesSusceptible = new XYChart.Series();
    private static XYChart.Series seriesVaccinated = new XYChart.Series();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    //defining the axes
    static CategoryAxis xAxis ;
    static NumberAxis yAxis ;
    private Line valueMarker = new Line();
    private double yShift;

    static Group root = new Group ();

    Scene scene ;
    AreaChart<String, Number> areaChart;

    public void showChartVaccinationWithMask(boolean mask_wearing_begins, double calculated_r_factor,
        int no_of_infected, int no_of_immune, int no_of_susceptible, int no_of_vaccinated, int no_of_deaths,int length){
        Platform.runLater(() -> {
            System.out.println ("no of infected = " + no_of_infected +
                    "no of immune = " + no_of_immune );

            Date now = new Date();
//            if(mask_wearing_begins && !maskIndicator) {
//
//                double displayPosition = yAxis.getDisplayPosition(no_of_infected);
//                // update marker
//                valueMarker.setStartX(yShift + displayPosition);
//                valueMarker.setEndX(yShift + displayPosition);
//                System.out.println("Setting up LIne#################");
//                maskIndicator = true;
//            }


            seriesInfected.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_infected));
            seriesImmune.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_immune));
            seriesDeaths.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_deaths));
            seriesSusceptible.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_susceptible));
            seriesVaccinated.getData().add(new XYChart.Data(simpleDateFormat.format(now),no_of_vaccinated));
            //timeCounter ++;
        });

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("");
         xAxis = new CategoryAxis();
         yAxis = new NumberAxis(0, 1000, 5);

        xAxis.setLabel("Time/s");
        yAxis.setLabel("Value");
        //creating the chart
        areaChart = new AreaChart<String, Number> (xAxis,yAxis);

        areaChart.setTitle("Infection Control Mask Usage");
        //defining a series
        seriesInfected.setName("Infected");
        seriesImmune.setName("Immune");
        seriesSusceptible.setName("Susceptible");
        seriesVaccinated.setName("Vaccinated");
        seriesDeaths.setName("Deaths");

        //Creating a Group object
        //root = new Group (areaChart);
        root.getChildren ().add (areaChart);

        scene = new Scene(root,800,600);
        //scene.getStylesheets().add("GuiRegularWithMask/MaskChart.css");
        areaChart.getData().addAll(seriesImmune, seriesInfected, seriesSusceptible,seriesVaccinated,seriesDeaths);
        stage.setScene(scene);
        areaChart.prefHeightProperty ().bind (scene.heightProperty ());
        areaChart.prefWidthProperty ().bind (scene.widthProperty ());

        stage.show();

        Node chartArea = areaChart.lookup(".chart-plot-background");
        Bounds chartAreaBounds = chartArea.localToScene(chartArea.getBoundsInLocal());
        // remember scene position of chart area
        yShift = chartAreaBounds.getMinY();
        valueMarker.setStartX(chartAreaBounds.getMinX());
        valueMarker.setEndX(chartAreaBounds.getMaxX());


    }

}
