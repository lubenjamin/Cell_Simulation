package ControllerPackage;

import cellsociety.Cell;
import javafx.scene.Group;
import javafx.scene.paint.Color;

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
      cell.setDisplayColor(Color.RED);
      cell.setCurrentState("RED");
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
    Cell current = currentModel.getCell(x,y);


    current.setNextState(current.getCurrentState());
    if(current.getCurrentState().equals("RED")){
      current.setNextState("BLUE");
    }
    else if(current.getCurrentState().equals("BLUE")){
      current.setNextState("RED");
    }

  }

  @Override
  protected void calcNewDisplay(Cell cell) {
    if(cell.getCurrentState().equals("RED")){
      cell.setDisplayColor(Color.BLUE);
    }
    else if(cell.getCurrentState().equals("BLUE")){
      cell.setDisplayColor(Color.RED);
    }
  }
}
