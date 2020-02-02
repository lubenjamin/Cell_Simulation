package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class GameOfLifeController extends Controller {

  private final static double initialLive = .3;

  public GameOfLifeController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected void initializeModel() {
    Random a = new Random();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i / WIDTH_CELLS;
      int y = i % WIDTH_CELLS;


      Cell cell = currentModel.getCell(x, y);
      double stateSelect = a.nextDouble();
      if(stateSelect<initialLive){
        cell.setCurrentState(new State("ALIVE"));
      }
      if(stateSelect>=initialLive){
        cell.setCurrentState(new State("DEAD"));
      }
      calcNewDisplay(cell);
    }
  }



  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x,y);
    ArrayList<Cell> neigh = currentModel.getMooreNeighborhood(x,y);

    int numAlive =0;

    for (Cell c : neigh){
      if (c.getCurrentState().equals("ALIVE")){
        numAlive++;
      }
    }

    if(current.getCurrentState().equals("ALIVE")){
      if (numAlive==2 || numAlive ==3){
        current.setNextState(new State("ALIVE"));
      }
      else{
        current.setNextState(new State("DEAD"));
      }
    }
    else{
      if(numAlive == 3){
        current.setNextState(new State("ALIVE"));
      }
      else{
        current.setNextState(new State("DEAD"));
      }
    }



  }

  @Override
  protected void calcNewDisplay(Cell cell) {
    switch ( cell.getCurrentState().getState()){
      case "DEAD":
        cell.setDisplayColor(Color.WHITE);
        break;
      case "ALIVE":
        cell.setDisplayColor(Color.BLACK);
        break;

    }
  }
}
