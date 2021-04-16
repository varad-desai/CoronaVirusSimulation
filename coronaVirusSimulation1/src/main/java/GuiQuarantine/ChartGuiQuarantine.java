package GuiQuarantine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartGuiQuarantine extends Application {
    public static int timeCounter = 0;
    private boolean maskIndicator = false;
    private final int WINDOW_SIZE = 10;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    private static Series seriesInfected = new Series();
    private static Series seriesImmune = new Series();
    private static Series seriesDeaths = new Series();
    private static Series seriesSusceptible = new Series();
    private static Series seriesQuarantine = new Series();

    private Scene scene ;
    private AreaChart<String, Number> areaChart;

    public void showChartWithQuarantine( double r_factor, int no_of_infected, int no_of_immune,
        int no_of_susceptible,int no_of_quarantine, int no_of_deaths, int length){
        Platform.runLater(() -> {
            System.out.println ("no of infected = " + no_of_infected +
                                " no of immune = " + no_of_immune );

            Date now = new Date();
            seriesInfected.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_infected));
            seriesImmune.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_immune));
            seriesDeaths.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_deaths));
            seriesSusceptible.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_susceptible));
            seriesQuarantine.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_quarantine));
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
        areaChart = new AreaChart<String, Number> (xAxis,yAxis);

        areaChart.setTitle("CoronaVirus LineChart");
        //defining a series
        seriesInfected.setName("Infected");
        seriesImmune.setName("Immune");
        seriesDeaths.setName("Deaths");
        seriesSusceptible.setName("Susceptible");
        seriesQuarantine.setName("Qurantine");
        areaChart.getData().addAll(seriesInfected,seriesImmune,seriesDeaths,seriesSusceptible,seriesQuarantine);
        //areaChart.setPrefSize(280,180);



//Creating a Group object
        //root = new Group (areaChart);
        //root.getChildren ().addAll (areaChart,barChart);
        //SplitPane pane = new SplitPane(areaChart,barChart);
        SplitPane pane = new SplitPane(areaChart);

        scene = new Scene(pane,600,800);

        stage.setScene(scene);
        areaChart.prefHeightProperty ().bind (scene.heightProperty ());
        areaChart.prefWidthProperty ().bind (scene.widthProperty ());
        stage.show();

    }
}
