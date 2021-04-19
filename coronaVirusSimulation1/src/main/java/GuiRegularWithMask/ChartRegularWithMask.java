package GuiRegularWithMask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author divya kulkarni
 */

public class ChartRegularWithMask extends Application {
    private static XYChart.Series seriesInfected = new XYChart.Series();
    private static XYChart.Series seriesImmune = new XYChart.Series();
    private static XYChart.Series seriesDeaths = new XYChart.Series();
    private static XYChart.Series seriesSusceptible = new XYChart.Series();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    Text text = new Text ();
    private static DoubleProperty r_factor_string = new SimpleDoubleProperty (0);

    //defining the axes
    static CategoryAxis xAxis ;
    static NumberAxis yAxis ;
    Scene scene ;
    AreaChart<String, Number> areaChart;

    public void showChartWithMask(double r_factor, boolean mask_wearing_begins, double calculated_r_factor, int no_of_infected,
                                  int no_of_immune, int no_of_susceptible,
                                  int no_of_deaths, int population) {
        Platform.runLater(() -> {
            System.out.println ("r_factor :" + r_factor + "no of infected = " + no_of_infected +
                    "no of immune = " + no_of_immune );

            r_factor_string.set (r_factor);

            Date now = new Date();

            seriesInfected.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_infected));
            seriesImmune.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_immune));
            seriesDeaths.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_deaths));
            seriesSusceptible.getData().add(new XYChart.Data(simpleDateFormat.format(now), no_of_susceptible));
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("");
        // R factor
        Label label = new Label ();
        label.setText ("R Factor: ");
        text.textProperty ().bind (r_factor_string.asString ());


        xAxis = new CategoryAxis();
        yAxis = new NumberAxis(0, 1000, 5);

        xAxis.setLabel("Time/s");
        yAxis.setLabel("Value");
        //creating the chart
        areaChart = new AreaChart<String, Number> (xAxis,yAxis);

        areaChart.setTitle("CoronaVirus Mask Usage Chart");
        //defining a series
        seriesInfected.setName("Infected");
        seriesImmune.setName("Immune");
        seriesSusceptible.setName("Susceptible");
        seriesDeaths.setName("Deaths");

        areaChart.getData().addAll(seriesImmune, seriesInfected, seriesSusceptible,seriesDeaths);


        GridPane grid = new GridPane ();
        // Setting up R factor position on the screen
        grid.setLayoutX (60);
        grid.setLayoutY (5);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets (25, 25, 25, 25));
        grid.add(label, 0, 1);
        grid.add (text, 1,1);
        Group root = new Group (areaChart,grid);

        scene = new Scene(root,800,600);


        stage.setScene(scene);

        areaChart.prefHeightProperty ().bind (scene.heightProperty ());
        areaChart.prefWidthProperty ().bind (scene.widthProperty ());

        stage.show();

    }


}
