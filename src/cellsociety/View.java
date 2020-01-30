package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

public class View {
  private KeyFrame myFrame;
  private Timeline animation = new Timeline();
  private double MILLISECOND_DELAY = 1000.0;
  private double SECOND_DELAY = 100.0;
  private ArrayList<Rectangle> cellList = new ArrayList<>();
  private Scene myScene;

  public View(Group viewGroup, int widthCells, int heightCells, Model currentModel){
    this.myFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e-> this.updateAllCells(viewGroup, currentModel, SECOND_DELAY));
    this.animation.setCycleCount(Timeline.INDEFINITE);
    this.animation.getKeyFrames().add(myFrame);
    this.myScene = new Scene(viewGroup, widthCells, heightCells);

  }
  public void updateAllCells(Group viewGroup, Model grid, double time) {
    viewGroup.getChildren().clear();

  }
  private void initCells(Model grid, int size) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        Rectangle rectangle = new Rectangle(i*10, j*10, displayState(grid.getCell(i, j)));
        cellList.add(rectangle);
      }
    }
  }
  private void getCell(Model currentModel) {
  }
  private Paint displayState(Cell cell) {
    return cell.getDisplayColor();
  }

}
