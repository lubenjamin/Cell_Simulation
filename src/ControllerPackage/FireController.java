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
    double stateSelect = r.nextDouble();
    if(stateSelect>initialTree){
      current.setCurrentState(new State(0));
    }
    if(stateSelect<=initialTree){
      if(r.nextDouble()<=initialBurningTree){
        current.setCurrentState(new State(2));
      }
      else{
        current.setCurrentState(new State(1));
      }
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

    ArrayList<Cell> neigh = currentModel.getSimpleNeighborhood(x, y);
    int numOnFire = 0;
    for (Cell c : neigh) {
      if (c.getCurrentState().getState()==2) numOnFire++;
    }

    if (numOnFire == 0) {
      current.setNextState(new State(1));
      return;
    }

    Random a = new Random();
    double stateSelect = a.nextDouble();
    if (stateSelect <= percentCatchFire) {
      current.setNextState(new State(2));
      return;
    }

    current.setNextState(new State(1));
  }
}
