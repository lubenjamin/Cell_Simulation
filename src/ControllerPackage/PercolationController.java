package ControllerPackage;

import cellsociety.Cell;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.Random;

public class PercolationController extends Controller{

  public PercolationController(Group simGroup) {
    super(simGroup);
  }

  @Override
  protected void initializeModel() {
    for(int i = 0; i < WIDTH_CELLS*HEIGHT_CELLS; i++) {
      int x = i / WIDTH_CELLS;
      int y = i % WIDTH_CELLS;
      Cell cell = currentModel.getCell(x,y);
      cell.setDisplayColor(Color.BLACK);
      cell.setCurrentState("BLACK");
    }
  }

  @Override
  protected void updateGrid(){
    for(int i = 0; i < WIDTH_CELLS*HEIGHT_CELLS; i++){
      int x = i % WIDTH_CELLS;
      int y = i/WIDTH_CELLS;
      updateCell(x,y);
    }
  }

  @Override
  protected void updateCell(int x, int y) {
    Random u = new Random();
    int a = u.nextInt(WIDTH_CELLS);
    int b = u.nextInt(WIDTH_CELLS);
    Cell current = currentModel.getCell(a,b);
    current.setNextState(current.getCurrentState());
    if(current.getCurrentState().equals("BLACK")){
      current.setNextState("BLUE");
    }
    else if(current.getCurrentState().equals("BLUE")){
      current.setNextState("BLACK");
    }

  }

  @Override
  protected void calcNewDisplay(Cell cell) {
    if(cell.getCurrentState().equals("BLACK")){
      cell.setDisplayColor(Color.BLUE);
    }
    else if(cell.getCurrentState().equals("BLUE")){
      cell.setDisplayColor(Color.BLACK);
    }
  }
}
