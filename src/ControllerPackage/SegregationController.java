package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class SegregationController extends Controller {


  private final static double percentOccupied = .945;
  private final static double percentMajority = .5;
  private final static double satisfiedLevel = .73;

  private ArrayList<Cell> emptySpots;
  private ArrayList<Cell> needMove;

  //EMPTY = 0 : MAJORITY = 1 : MINORITY : 2;
  public SegregationController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected void initializeCellState(Cell current, Random r){
    double stateSelect = r.nextDouble();
    if (stateSelect > percentOccupied) {
      current.setCurrentState(new State(0));
    }
    if (stateSelect <= percentOccupied) {
      if (r.nextDouble() < percentMajority) {
        current.setCurrentState(new State(1));
      } else {
        current.setCurrentState(new State(2));
      }
    }

  }
  @Override
  protected void setColors() {
    state0Color =Color.LIGHTGRAY;
    state1Color = Color.DARKBLUE;
    state2Color = Color.DARKGOLDENROD;
  }

  @Override
  protected void updateGrid() {
    needMove = new ArrayList<>();
    emptySpots = getEmptySpots(0);
    super.updateGrid();
    moveUnHappy();
  }



  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);
    if (current.getCurrentState().getState()==0) {
      current.setNextState(new State(0));
      return;
    }

    int currentState = current.getCurrentState().getState();

    ArrayList<Cell> neigh = currentModel.getMooreNeighborhood(x, y);
    double totalNeigh = 0;
    double similar = 0;

    for (Cell c : neigh) {
      if (c.getCurrentState().getState()==currentState) {
        similar++;
      }
      if (c.getCurrentState().getState()!=0) {
        totalNeigh++;
      }
    }

    if (totalNeigh > 0 && similar / totalNeigh < satisfiedLevel) {
      needMove.add(current);
    }

    current.setNextState(current.getCurrentState());
  }




  private ArrayList<Cell> getEmptySpots(int state) {
    ArrayList<Cell> ret = new ArrayList<>();

    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      if (currentModel.getCell(x, y).getCurrentState().getState()==state) {
        ret.add(currentModel.getCell(x, y));
      }
    }
    return ret;
  }

  /**
   * This method picks a random cell that needs moving
   * and a random empty spot. This is because at the beginning
   * there are not enough empty spots to relocate cells, so
   * it needs to be random
   */
  private void moveUnHappy() {
    Random r = new Random();
    while(emptySpots.size()!=0 && needMove.size()!=0){
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


}



