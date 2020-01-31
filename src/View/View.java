package View;

import cellsociety.Cell;
import cellsociety.Model;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import java.util.ArrayList;

public class View {
  private final int SPACING = 6;
  private final int VISUAL_WIDTH = 5;
  private final int VISUAL_HEIGHT = 5;

  private Model myModel;
  private Group myViewGroup;
  private ArrayList<CellVisual> myVisuals = new ArrayList<>();

  public View(Group viewGroup, double widthCells, double heightCells, Model currentModel) {
    this.myModel = currentModel;
    this.myViewGroup = viewGroup;
    initCellView(currentModel, widthCells, heightCells);
  }
  public void updateAllCells() {
    for (CellVisual cv : myVisuals) {
      cv.setFill(displayState(myModel.getCell(cv.getXPos(), cv.getYPos())));
    }
  }
  private void initCellView(Model grid, double width, double height) {
    for (int i = 0; i < 75; i++) {
      for (int j = 0; j < 75; j++) {
        CellVisual cv = new CellVisual(VISUAL_WIDTH, VISUAL_HEIGHT, displayState(grid.getCell(i, j)), i, j);
        cv.setX(i * SPACING);
        cv.setY(j * SPACING);
        myVisuals.add(cv);
        myViewGroup.getChildren().add(cv);
      }
    }
  }
  private Paint displayState(Cell cell) {
    return cell.getDisplayColor();
  }
}
