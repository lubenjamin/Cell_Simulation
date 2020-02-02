package View;

import cellsociety.Cell;
import cellsociety.Model;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import java.util.ArrayList;

public class View {
  private ArrayList<CellVisual> myVisuals = new ArrayList<>();
  private final int SPACING = 6;
  private final int VISUAL_WIDTH = 5;
  private final int VISUAL_HEIGHT = 5;
  private Model myModel;
  private Group myViewGroup;

  public View(Group viewGroup, double widthCells, double heightCells, Model currentModel) {
    this.myModel = currentModel;
    this.myViewGroup = viewGroup;
    initCellView(currentModel, widthCells, heightCells);
    addAndUpdateVisual(viewGroup, currentModel);
  }
  public void updateAllCells() {
    myViewGroup.getChildren().clear();
    addAndUpdateVisual(myViewGroup, myModel);
  }
  private void initCellView(Model grid, double width, double height) {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        CellVisual cv = new CellVisual(VISUAL_WIDTH, VISUAL_HEIGHT, displayState(grid.getCell(i, j)), i, j);
        cv.setX(i * SPACING);
        cv.setY(j * SPACING);
        myVisuals.add(cv);
      }
    }
  }
  private void addAndUpdateVisual(Group viewGroup, Model grid) {
    for (CellVisual cv : myVisuals) {
      cv.setFill(displayState(grid.getCell(cv.getXPos(), cv.getYPos())));
      viewGroup.getChildren().add(cv);
    }
  }
  private Paint displayState(Cell cell) {
    return cell.getDisplayColor();
  }
}
