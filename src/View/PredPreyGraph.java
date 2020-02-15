package View;

import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class PredPreyGraph extends Group {

  private static final int GRAPH_WIDTH = 300;
  private static final int GRAPH_HEIGHT = 300;
  private final LineChart<Number, Number> myLineChart;
  private int time = 0;
  private XYChart.Series<Number, Number> series = new XYChart.Series<>();
  private XYChart.Series<Number, Number> series2 = new XYChart.Series<>();

  /**
   * create a linechart object that records population states
   */
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

  /**
   * reinitialize the graph back to original state with new data series
   */
  public void reinit() {
    clear();
    series = new XYChart.Series<>();
    series2 = new XYChart.Series<>();
    myLineChart.getData().add(series);
    myLineChart.getData().add(series2);
    time = 0;
  }

  /**
   * update the graph with population totals of sharks and fish retrieved from sim controller
   * and increment timer
   * @param predNum
   * @param preyNum
   */
  public void update(int predNum, int preyNum) {
    enterData("SHARK", predNum, series);
    enterData("FISH", preyNum, series2);
    time++;
  }

  /**
   * clear the current graph of all data
   */
  public void clear() {
    myLineChart.getData().removeAll(myLineChart.getData());
  }

  private XYChart.Series<Number, Number> enterData(String title, Number numSpecies,
      XYChart.Series series) {
    if (title.equals("SHARK")) {
      series.setName(title);
      series.getData().add(new XYChart.Data<>(time, numSpecies));
      return series;
    } else if (title.equals("FISH")) {
      series2.setName(title);
      series2.getData().add(new XYChart.Data<>(time, numSpecies));
      return series2;
    }
    return series;
  }
}
