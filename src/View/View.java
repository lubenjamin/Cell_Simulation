package View;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import utils.Cell;
import utils.Model;

public class View {

  private final double spacing;
  private final double VIEW_BOUND = 400;
  private final Model myModel;
  private final Group myViewGroup;
  private final ArrayList<CellVisual> myVisuals = new ArrayList<>();
  private List<String> colors;
  private double myVisualWidth;
  private double myVisualHeight;
  private int currState = 0;

  /**
   * create object responsible for visualizing cells
   *
   * @param viewGroup
   * @param widthCells
   * @param heightCells
   * @param currentModel
   * @param spacing
   * @param colors
   */
  public View(Group viewGroup, int widthCells, int heightCells, Model currentModel, double spacing,
              List<String> colors) {

    this.colors = colors;
    this.spacing = spacing;
    this.myModel = currentModel;
    this.myViewGroup = viewGroup;

    initCellView(currentModel, widthCells, heightCells);
  }

  /**
   * for each cell visual object retreived from model, update based on new state calculated from game logic
   */
  public void updateAllCells() {
    for (CellVisual cv : myVisuals) {
      cv.setFill(displayState(myModel.getCell(cv.getXPos(), cv.getYPos())));
    }
  }

  /**
   * reset visuals to default colors
   * @param colors
   */
  public void replaceColors(List<String> colors) {
    this.colors = colors;
  }

  private void initCellView(Model grid, int width, int height) {
    myVisualWidth = VIEW_BOUND / width;
    myVisualHeight = VIEW_BOUND / height;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        CellVisual cv = new CellVisual(myVisualWidth, myVisualHeight,
                displayState(grid.getCell(i, j)), i, j);

        cv.setX(i * (myVisualWidth + spacing));
        cv.setY(j * (myVisualHeight + spacing));
        myVisuals.add(cv);

        EventHandler<MouseEvent> eventHandler = mouseEvent -> handleCellClick(
                grid.getCell(cv.getXPos(), cv.getYPos()), cv);
        cv.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        myViewGroup.getChildren().add(cv);
      }
    }
  }


  private void handleCellClick(Cell cell, CellVisual cv) {
    myModel.manualEntry = true;
    currState++;
    if (currState > 2) {
      currState = 0;
    }
    cell.incrementState();
    cv.setFill(Color.valueOf(colors.get(cell.getNewStateFromClick())));
  }

  private Paint displayState(Cell cell) {
    return Color.valueOf(colors.get(cell.getCurrentState().getState()));
  }
}

