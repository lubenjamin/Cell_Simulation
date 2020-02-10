package controllerPackage;

import utils.Cell;
import utils.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;
import utils.XMLException;
import utils.parameterException;


public class FireController extends Controller {


  private double initialTree;
  private double initialBurningTree;
  private double percentCatchFire;


  //EMPTY = 0 : TREE = 1 : BURNING : 2;

  public FireController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected int getMaxStates() {
    return 2;
  }

  @Override
  protected void initializeCellState(Cell current) {
    if (probabilityChecker(initialTree)) {
      if (probabilityChecker(initialBurningTree)) {
        current.setCurrentState(new State(2));
      } else {
        current.setCurrentState(new State(1));
      }
    } else {
      current.setCurrentState(new State(0));
    }

  }

  @Override
  protected void setSimParams() {
      initialTree = reader.getDoubleValue("initialTree");
      initialBurningTree = reader.getDoubleValue("initialBurningTree");
      percentCatchFire = reader.getDoubleValue("percentCatchFire");
      spacing = reader.getDoubleValue("spacing");
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);

    if (current.getCurrentState().getState() == 0 || current.getCurrentState().getState() == 2) {
      current.setNextState(new State(0));
      return;
    }

    int numFire = getNumFire(current);
    if (numFire > 0 && probabilityChecker(percentCatchFire)) {
      current.setNextState(new State(2));
    } else {
      current.setNextState(new State(1));
    }
  }

  private int getNumFire(Cell current) {
    ArrayList<Cell> neigh = (ArrayList<Cell>) currentModel.getSimpleNeighborhood(current.getX(), current.getY());
    int numOnFire = 0;
    for (Cell c : neigh) {
      if (c.getCurrentState().getState() == 2) {
        numOnFire++;
      }
    }
    return numOnFire;
  }
}
