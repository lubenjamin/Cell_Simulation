package View;

import cellsociety.Cell;
import cellsociety.Model;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.ArrayList;

public class View {
  private final int SPACING = 1;
  private final int VIEW_BOUND = 400;
  private double myVisualWidth;
  private double myVisualHeight;

  private Model myModel;
  private Group myViewGroup;
  private ArrayList<CellVisual> myVisuals = new ArrayList<>();

  public View(Group viewGroup, int widthCells, int heightCells, Model currentModel) {
    this.myModel = currentModel;
    this.myViewGroup = viewGroup;
    initCellView(currentModel, widthCells, heightCells);
  }
  public void updateAllCells() {
    for (CellVisual cv : myVisuals) {
      cv.setFill(displayState(myModel.getCell(cv.getXPos(), cv.getYPos())));
    }
  }
  public void resetView() {
    for (CellVisual cv: myVisuals) {
      cv.setFill(Color.WHITE);
    }
  }
  private void initCellView(Model grid, int width, int height) {
    myVisualWidth = VIEW_BOUND/width;
    myVisualHeight = VIEW_BOUND/height;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        CellVisual cv = new CellVisual(myVisualWidth, myVisualHeight, displayState(grid.getCell(i, j)), i, j);
        cv.setX(i*(myVisualWidth+SPACING));
        cv.setY(j*(myVisualHeight+SPACING));
        myVisuals.add(cv);
        myViewGroup.getChildren().add(cv);
      }
    }
  }
  private Paint displayState(Cell cell) {
    return cell.getDisplayColor();
  }
}
