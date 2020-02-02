package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;



public class PercolationController extends Controller {

  private static final double PERCENT_BLOCKED = .575;

  //EMPTY = 0 : PERC = 1 : BLOCKED : 2;
  public PercolationController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }


  @Override
  protected void initializeCellState(Cell current, Random r){
    double stateSelect = r.nextDouble();
    if(stateSelect> PERCENT_BLOCKED){
      current.setCurrentState(new State(0));
    }
    if(stateSelect<= PERCENT_BLOCKED){
      current.setCurrentState(new State(2));
    }
  }
  @Override
  protected void setColors() {
    state0Color =Color.WHITE;
    state1Color = Color.LIGHTBLUE;
    state2Color = Color.SLATEGREY;
  }


  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);
    if (current.getCurrentState().getState()==2){
      current.setNextState(new State(2));
      return;
    }
    if (y == 0 && current.getCurrentState().getState()==0) {
      current.setNextState(new State(1));
      return;
    }


    for (Cell c : currentModel.getMooreNeighborhood(x, y)) {
      if (c.getCurrentState().getState()==1) {
        current.setNextState(new State(1));
        return;
      }
    }

    current.setNextState(current.getCurrentState());
  }
}
