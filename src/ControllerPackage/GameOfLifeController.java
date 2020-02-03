package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class GameOfLifeController extends Controller {

  private double initialLive;

  //DEAD = 0 : ALIVE = 1

  public GameOfLifeController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
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
    state0Color = Color.valueOf(reader.getString("state0Color"));
    state1Color = Color.valueOf(reader.getString("state1Color"));
    state2Color = Color.valueOf(reader.getString("state2Color"));

    initialLive = reader.getDoubleValue("initialLive");
    spacing = reader.getDoubleValue("spacing");
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
    ArrayList<Cell> neigh = currentModel.getMooreNeighborhood(current.getX(), current.getY());
    for (Cell c : neigh) {
      if (c.getCurrentState().getState() == 1) {
        numAlive++;
      }
    }
    return numAlive;
  }
}
