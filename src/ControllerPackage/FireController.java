package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class FireController extends Controller {


  private final static double initialTree = .9;
  private final static double initialBurningTree = .001;
  private final static double percentCatchFire = .6;

  //EMPTY = 0 : TREE = 1 : BURNING : 2;

  public FireController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected void initializeCellState(Cell current, Random r){
    if(probabilityChecker(initialTree)){
      if(probabilityChecker(initialBurningTree)){
        current.setCurrentState(new State(2));
      }
      else{
        current.setCurrentState(new State(1));
      }
    }
    else{
      current.setCurrentState(new State(0));
    }
  }

  @Override
  protected void setColors() {
    state0Color =Color.WHITE;
    state1Color = Color.GREEN;
    state2Color = Color.RED;
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);

    if (current.getCurrentState().getState()==0 || current.getCurrentState().getState()==2) {
      current.setNextState(new State(0));
      return;
    }

    int numFire = getNumFire(current);
    if (numFire >0 && probabilityChecker(percentCatchFire)) {
      current.setNextState(new State(2));
    }
    else{
      current.setNextState(new State(1));
    }



  }

  private int getNumFire(Cell current) {
    ArrayList<Cell> neigh = currentModel.getSimpleNeighborhood(current.getX(), current.getY());
    int numOnFire = 0;
    for (Cell c : neigh) {
      if (c.getCurrentState().getState()==2) numOnFire++;
    }
    return numOnFire;
  }
}
