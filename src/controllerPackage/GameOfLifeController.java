package controllerPackage;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Group;
import utils.Cell;
import utils.FileReader;


public class GameOfLifeController extends Controller {

  private double initialLive;

  //DEAD = 0 : ALIVE = 1

  public GameOfLifeController(Group simGroup, FileReader reader, Group simUIGroup) {
    super(simGroup, reader, simUIGroup);
  }

  @Override
  protected HashMap<String, Object> getSimParamsForUi() {
    HashMap<String, Object> ret = new HashMap<>();
    ret.put("initialLive", initialLive);
    return ret;
  }

  @Override
  protected void setSimParamsFromUI() {
    HashMap<String, Object> values = (HashMap<String, Object>) simUI.getValues();
    initialLive = (double) values.get("initialLive");
  }

  @Override
  protected void initializeCellState(Cell current) {
    if (probabilityChecker(initialLive)) {
      current.setCurrentState(new State(1));
    } else {
      current.setCurrentState(new State(0));
    }
  }

  @Override
  protected void setSimParams() {
    initialLive = reader.getDoubleValue("initialLive");
    spacing = reader.getDoubleValue("spacing");
    maxState = 1;
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);

    int numAlive = getNumAlive(current);
    if ((current.getCurrentState().getState() == 1 && numAlive == 2) || numAlive == 3) {
      current.setNextState(new State(1));
    } else {
      current.setNextState(new State(0));
    }
  }


  private int getNumAlive(Cell current) {
    int numAlive = 0;
    ArrayList<Cell> neigh = (ArrayList<Cell>) currentModel
        .getMooreNeighborhood(current.getX(), current.getY());
    for (Cell c : neigh) {
      if (c.getCurrentState().getState() == 1) {
        numAlive++;
      }
    }
    return numAlive;
  }
}
