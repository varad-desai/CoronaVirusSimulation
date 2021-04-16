package GuiRegular;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartGuiRegular extends Application {
    public static int timeCounter = 0;
    private boolean maskIndicator = false;
    private final int WINDOW_SIZE = 10;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    private static Series seriesInfected = new Series();
    private static Series seriesImmune = new Series();
    private static Series seriesDeaths = new Series();
    private static Series seriesSusceptible = new Series();

//    private static XYChart.Series barChartSeries = new XYChart.Series();
//    private static CategoryAxis xAxisBarChart= new CategoryAxis();
//    private static NumberAxis yAxisBarChart = new NumberAxis(0, 1100, 5);
//    private static Group root = new Group ();

    private Scene scene ;
    private AreaChart<String, Number> areaChart;
//    private static BarChart<String, Number> barChart = new BarChart<String, Number>(xAxisBarChart, yAxisBarChart);

    public void showChartRegular( double r_factor, int no_of_infected, int no_of_immune,
        int no_of_susceptible, int no_of_deaths, int length){
        Platform.runLater(() -> {
            System.out.println ("no of infected = " + no_of_infected +
                                " no of immune = " + no_of_immune );

//            if(mask_wearing_begins && !maskIndicator) {
//                // add a line indicating usage of mask started
//                System.out.println ("Drawing circle...");
//                Circle circle = new Circle(timeCounter, no_of_infected, 5.0f);
//                circle.setAccessibleText ("Mask Usage Started");
//                root.getChildren ().add (circle);
//                maskIndicator = true;
//            }

            Date now = new Date();
            seriesInfected.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_infected));
            seriesImmune.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_immune));
            seriesDeaths.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_deaths));
            seriesSusceptible.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_susceptible));


//            // BAR Chart Series
//            XYChart.Series barChartSeries = new XYChart.Series();
//            barChartSeries.getData().add(new XYChart.Data("Infected", no_of_infected));
//            barChartSeries.getData().add(new XYChart.Data("Susceptible", no_of_susceptible));
//            barChart.getData().add(barChartSeries);
//            //timeCounter ++;
                });

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("");
// AreaChart Data
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, 1100, 5);

        xAxis.setLabel("Time/s");
        yAxis.setLabel("Value");
        //creating the chart
        areaChart = new AreaChart<String, Number>(xAxis,yAxis);

        areaChart.setTitle("CoronaVirus AreaChart");
        //defining a series
        seriesInfected.setName("Infected");
        seriesImmune.setName("Immune");
        seriesDeaths.setName("Deaths");
        seriesSusceptible.setName("Susceptible");
        areaChart.getData().addAll(seriesImmune, seriesInfected, seriesDeaths, seriesSusceptible);
        //areaChart.setPrefSize(280,180);

//// BAR chart data
//
//        xAxisBarChart.setLabel("Category");
//
//
//        yAxisBarChart.setLabel("Value");
//        barChart = new BarChart(xAxisBarChart,yAxisBarChart);
//        barChart.setTitle("CoronaVirus BarChart");
//        barChartSeries.setName("CoronaInfectionsVsImmune");
//
//        barChart.getData().addAll(barChartSeries);
//        barChart.setPrefSize(280,180);


//Creating a Group object
        //root = new Group (areaChart);
        //root.getChildren ().addAll (areaChart,barChart);
        //SplitPane pane = new SplitPane(areaChart,barChart);
        SplitPane pane = new SplitPane(areaChart);

        scene = new Scene(pane,595,350);

        stage.setScene(scene);
        areaChart.prefHeightProperty ().bind (scene.heightProperty ());
        areaChart.prefWidthProperty ().bind (scene.widthProperty ());
        //barChart.prefHeightProperty().bind(scene.heightProperty());
        //barChart.prefWidthProperty().bind(scene.widthProperty());

        stage.show();

    }

}
