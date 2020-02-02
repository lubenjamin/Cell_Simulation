package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class GameOfLifeController extends Controller {

  private final static double initialLive = .3;

  //DEAD = 0 : ALIVE = 1

  public GameOfLifeController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected void initializeCellState(Cell current, Random r){
    double stateSelect = r.nextDouble();
    if(stateSelect<initialLive){
      current.setCurrentState(new State(1));
    }
    if(stateSelect>=initialLive){
      current.setCurrentState(new State(0));
    }

  }


  @Override
  protected void setColors() {
    state0Color =Color.WHITE;
    state1Color = Color.BLACK;

    //not
    state2Color = Color.WHITE;
  }



  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x,y);
    ArrayList<Cell> neigh = currentModel.getMooreNeighborhood(x,y);

    int numAlive =0;

    for (Cell c : neigh){
      if (c.getCurrentState().getState()==1){
        numAlive++;
      }
    }

    if(current.getCurrentState().getState()==1){
      if (numAlive==2 || numAlive ==3){
        current.setNextState(new State(1));
      }
      else{
        current.setNextState(new State(0));
      }
    }
    else{
      if(numAlive == 3){
        current.setNextState(new State(1));
      }
      else{
        current.setNextState(new State(0));
      }
    }
  }
}
