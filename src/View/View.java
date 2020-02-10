package View;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import utils.Cell;
import utils.Model;

public abstract class View {

  protected final double spacing;
  protected final double VIEW_BOUND = 400;
  protected final Model myModel;
  protected final Group myViewGroup;
  protected final ArrayList<CellVisual> myVisuals = new ArrayList<>();
  protected ArrayList<String> colors;
  protected double myVisualWidth;
  protected double myVisualHeight;
  protected int currState = 0;

  public View(Group viewGroup, int widthCells, int heightCells, Model currentModel, double spacing,
      ArrayList<String> colors) {
    this.colors = colors;
    this.spacing = spacing;
    this.myModel = currentModel;
    this.myViewGroup = viewGroup;
    //initCellView(currentModel, widthCells, heightCells);
  }

  public void updateAllCells() {
    for (CellVisual cv : myVisuals) {
      cv.setFill(displayState(myModel.getCell(cv.getXPos(), cv.getYPos())));
    }
  }

  private void initCellView(Model grid, int width, int height) {
    myVisualWidth = VIEW_BOUND / width;
    myVisualHeight = VIEW_BOUND / height;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        CellVisual cv = new CellVisual(myVisualWidth, myVisualHeight,
            displayState(grid.getCell(i, j)), i, j);
        cv.setSquareShape(i * (myVisualWidth + spacing), j * (myVisualHeight + spacing));
//        cv.setX(i * (myVisualWidth + spacing));
//        cv.setY(j * (myVisualHeight + spacing));
        myVisuals.add(cv);
        EventHandler<MouseEvent> eventHandler = mouseEvent -> handleCellClick(
            grid.getCell(cv.getXPos(), cv.getYPos()), cv);
        cv.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        myViewGroup.getChildren().add(cv);
      }
    }
  }

  protected Paint displayState(Cell cell) {
    return Color.valueOf(colors.get(cell.getCurrentState().getState()));
  }

  protected void handleCellClick(Cell cell, CellVisual cv) {
    myModel.manualEntry = true;
    cell.incrementState();
    cv.setFill(Color.valueOf(colors.get(cell.getNewStateFromClick())));
  }

  public void replaceColors(ArrayList<String> colors) {
    this.colors = colors;
  }
}
