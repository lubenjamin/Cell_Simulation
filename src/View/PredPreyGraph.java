package View;

import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class PredPreyGraph extends Group {
    private final LineChart<Number, Number> myLineChart;
    private static final int GRAPH_WIDTH=250;
    private static final int GRAPH_HEIGHT=150;
    private int time = 0;
    private XYChart.Series<Number,Number> series = new XYChart.Series<>();
    private XYChart.Series<Number,Number> series2 = new XYChart.Series<>();

    public PredPreyGraph() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        myLineChart = new LineChart<>(xAxis, yAxis);
        myLineChart.setTitle("PredatorPrey Relationship");
        yAxis.setLabel("Population");
        xAxis.setLabel("Generation");
        myLineChart.setPrefSize(GRAPH_WIDTH, GRAPH_HEIGHT);
        myLineChart.setAnimated(false);
        getChildren().add(myLineChart);
        myLineChart.setCreateSymbols(false);
        myLineChart.getData().add(series);
        myLineChart.getData().add(series2);
    }
    public void update(int predNum, int preyNum) {
        enterData("SHARK", predNum, series);
        enterData("FISH", preyNum, series2);
        time++;
    }
    public void clear() {
        myLineChart.getData().removeAll(myLineChart.getData());
    }
    private XYChart.Series<Number,Number> enterData(String title, Number numSpecies, XYChart.Series series) {
        if (title.equals("SHARK")) {
            series.setName(title);
            series.getData().add(new XYChart.Data<>(time, numSpecies));
            return series;
        }
        else if (title.equals("FISH")) {
            series2.setName(title);
            series2.getData().add(new XYChart.Data<>(time, numSpecies));
            return series2;
        }
        return series;
    }
}
