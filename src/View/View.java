package View;

import utils.Cell;
import utils.Model;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import java.util.ArrayList;

public class View {
  private double spacing;
  private final int VIEW_BOUND = 400;
  private double myVisualWidth;
  private double myVisualHeight;

  private Model myModel;
  private Group myViewGroup;
  private ArrayList<CellVisual> myVisuals = new ArrayList<>();

  public View(Group viewGroup, int widthCells, int heightCells, Model currentModel, double spacing) {

    this.spacing = spacing;
    this.myModel = currentModel;
    this.myViewGroup = viewGroup;
    initCellView(currentModel, widthCells, heightCells);
  }
  public void updateAllCells() {
    for (CellVisual cv : myVisuals) {
      cv.setFill(displayState(myModel.getCell(cv.getXPos(), cv.getYPos())));
    }
  }
  public void clear() {
    myViewGroup.getChildren().clear();
  }
  private void initCellView(Model grid, int width, int height) {
    myVisualWidth = VIEW_BOUND/width;
    myVisualHeight = VIEW_BOUND/height;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        CellVisual cv = new CellVisual(myVisualWidth, myVisualHeight, displayState(grid.getCell(i, j)), i, j);
        cv.setX(i*(myVisualWidth+spacing));
        cv.setY(j*(myVisualHeight+spacing));
        myVisuals.add(cv);
        myViewGroup.getChildren().add(cv);
      }
    }
  }
  private Paint displayState(Cell cell) {
    return cell.getDisplayColor();
  }
}
