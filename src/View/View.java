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

  public View(Group viewGroup, double widthCells, double heightCells, Model currentModel) {
    initCellView(currentModel, widthCells);
    addAndUpdateVisual(viewGroup, currentModel);
  }
  public void updateAllCells(Group viewGroup, Model grid, double time) {
    viewGroup.getChildren().clear();
    addAndUpdateVisual(viewGroup, grid);
  }
  private void initCellView(Model grid, double size) {
    for (int i = 0; i < 75; i++) {
      for (int j = 0; j < 75; j++) {
        CellVisual cv = new CellVisual(VISUAL_WIDTH, VISUAL_HEIGHT, displayState(grid.getCell(i, j)), i, j);
        cv.setX(i * SPACING);
        cv.setY(j * SPACING);
        myVisuals.add(cv);
      }
    }
  }
  private void addAndUpdateVisual(Group viewGroup, Model grid) {
    for (CellVisual cv : myVisuals) {
      cv.setFill(displayState(grid.getCell((int) cv.getXPos(), (int) cv.getYPos())));
      viewGroup.getChildren().add(cv);
    }
  }
  private Paint displayState(Cell cell) {
    return cell.getDisplayColor();
  }
}
