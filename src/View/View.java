package View;

import controllerPackage.State;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import utils.Cell;
import utils.Model;
import javafx.scene.Group;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Optional;

public class View {
  private final double spacing;
  private final double VIEW_BOUND = 400;
  private double myVisualWidth;
  private double myVisualHeight;

  private final Model myModel;
  private final Group myViewGroup;
  private final ArrayList<CellVisual> myVisuals = new ArrayList<>();

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
    Group g = new Group();
    myVisualWidth = VIEW_BOUND/width;
    myVisualHeight = VIEW_BOUND/height;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        CellVisual cv = new CellVisual(myVisualWidth, myVisualHeight, displayState(grid.getCell(i, j)), i, j);
        cv.setX(i*(myVisualWidth+spacing));
        cv.setY(j*(myVisualHeight+spacing));
        myVisuals.add(cv);
        g.getChildren().add(cv);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent mouseEvent) {
//            grid.getCell(cv.getXPos(), cv.getYPos()).setNextState(Color.BLACK);
//            cv.setFill(displayState(grid.getCell(cv.getXPos(), cv.getYPos())));
              handleCellClick(grid.getCell(cv.getXPos(), cv.getYPos()));
          }
        };
        cv.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
      }
    }
    myViewGroup.getChildren().add(g);
  }
  private Paint displayState(Cell cell) {
    return Color.valueOf(cell.getDisplayColor());
  }
  private void handleCellClick(Cell cell ) {
    ChoiceDialog<Integer> cd = new ChoiceDialog<>(1, 2, 3);
    cd.setContentText("Pick a state");
    Optional<Integer> res = cd.showAndWait();
    res.ifPresent(choice -> cell.setNextState(new State(choice)));
  }
}
