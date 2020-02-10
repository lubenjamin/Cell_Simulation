package controllerPackage;

import utils.Cell;
import utils.FileReader;
import javafx.scene.Group;


public class PercolationController extends Controller {

  private double percentBlocked;


  //EMPTY = 0 : PERC = 1 : BLOCKED : 2;
  public PercolationController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }


  @Override
  protected void initializeCellState(Cell current) {
    if (probabilityChecker(percentBlocked)) {
      current.setCurrentState(new State(2));
    } else {
      current.setCurrentState(new State(0));
    }

  }

  @Override
  protected void setSimParams() {
    percentBlocked = reader.getDoubleValue("percentBlocked");
    spacing = reader.getDoubleValue("spacing");
    maxState = 2;
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);
    if (current.getCurrentState().getState() == 2 || current.getCurrentState().getState() == 1) {
      current.setNextState(new State(current.getCurrentState().getState()));
    } else if (checkWater(current)) {
      current.setNextState(new State(1));
    } else {
      current.setNextState(new State(0));
    }
  }

  private boolean checkWater(Cell current) {
    if (current.getY() == 0) {
      return true;
    }
    for (Cell c : currentModel.getMooreNeighborhood(current.getX(), current.getY())) {
      if (c.getCurrentState().getState() == 1) {
        return true;
      }
    }
    return false;
  }
}
