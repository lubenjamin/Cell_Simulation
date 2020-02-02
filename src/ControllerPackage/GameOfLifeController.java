package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class GameOfLifeController extends Controller {

  private final static double initialLive = .5;

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
      if(stateSelect>initialLive){
        cell.setCurrentState("ALIVE");
      }
      if(stateSelect<=initialLive){
        cell.setCurrentState("DEAD");
      }
      calcNewDisplay(cell);
    }
  }

  @Override
  protected void updateGrid() {
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      updateCell(x, y);
    }
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x,y);
    ArrayList<Cell> neigh = currentModel.getNeighborhood(x,y);

    int numAlive =0;

    for (Cell c : neigh){
      if (c.getCurrentState().equals("ALIVE")){
        numAlive++;
      }
    }

    if(current.getCurrentState().equals("ALIVE")){
      if (numAlive==2 || numAlive ==3){
        current.setNextState("ALIVE");
      }
      else{
        current.setNextState("DEAD");
      }
    }
    else{
      if(numAlive == 3){
        current.setNextState("ALIVE");
      }
      else{
        current.setNextState("DEAD");
      }
    }



  }

  @Override
  protected void calcNewDisplay(Cell cell) {
    switch (cell.getCurrentState()){
      case "DEAD":
        cell.setDisplayColor(Color.WHITE);
        break;
      case "ALIVE":
        cell.setDisplayColor(Color.BLACK);
        break;

    }
  }
}
