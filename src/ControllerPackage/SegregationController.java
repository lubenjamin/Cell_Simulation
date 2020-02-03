package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class SegregationController extends Controller {


  private final static double percentOccupied = .945;
  private final static double percentMajority = .7;
  private final static double satisfiedLevel = .73;

  private ArrayList<Cell> emptySpots;
  private ArrayList<Cell> needMove;

  //EMPTY = 0 : MAJORITY = 1 : MINORITY : 2;
  public SegregationController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected void initializeCellState(Cell current) {
    if (probabilityChecker(percentOccupied)) {
      if (probabilityChecker(percentMajority)) {
        current.setCurrentState(new State(1));
      } else {
        current.setCurrentState(new State(2));
      }
    } else {
      current.setCurrentState(new State(0));
    }
  }

  @Override
  protected void setColors() {
    state0Color = Color.valueOf(reader.getString("state0Color"));
    state1Color = Color.valueOf(reader.getString("state1Color"));
    state2Color = Color.valueOf(reader.getString("state2Color"));
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);

    if (current.getCurrentState().getState() == 0) {
      current.setNextState(new State(0));
      return;
    }
    if (getSatisfy(current) < satisfiedLevel) {
      needMove.add(current);
    }
    current.setNextState(current.getCurrentState());
  }

  @Override
  protected void updateGrid() {
    needMove = new ArrayList<>();
    emptySpots = getEmptySpots(0);
    super.updateGrid();
    moveUnHappy();
  }

  /**
   * This method picks a random cell that needs moving and a random empty spot. This is because at
   * the beginning there are not enough empty spots to relocate cells, so it needs to be random
   */
  private void moveUnHappy() {
    Random r = new Random();
    while (emptySpots.size() != 0 && needMove.size() != 0) {
      int indexTo = r.nextInt(emptySpots.size());
      int indexFrom = r.nextInt(needMove.size());
      Cell cellReplace = emptySpots.get(indexTo);
      Cell current = needMove.get(indexFrom);
      cellReplace.setNextState(new State(current.getCurrentState().getState()));
      current.setNextState(new State(0));
      emptySpots.remove(cellReplace);
      needMove.remove(current);
    }
  }

  private double getSatisfy(Cell current) {
    ArrayList<Cell> neigh = currentModel.getMooreNeighborhood(current.getX(), current.getY());
    double totalNeigh = 0;
    double similar = 0;
    for (Cell c : neigh) {
      if (c.getCurrentState().getState() == current.getCurrentState().getState()) {
        similar++;
      }
      if (c.getCurrentState().getState() != 0) {
        totalNeigh++;
      }
    }
    if (totalNeigh == 0) {
      return 1;
    }
    return similar / totalNeigh;
  }

  private ArrayList<Cell> getEmptySpots(int state) {
    ArrayList<Cell> ret = new ArrayList<>();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      if (currentModel.getCell(x, y).getCurrentState().getState() == state) {
        ret.add(currentModel.getCell(x, y));
      }
    }
    return ret;
  }


}



