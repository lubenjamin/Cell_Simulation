package ControllerPackage;

import cellsociety.Cell;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class PercolationController extends Controller{

  public PercolationController(Group simGroup) {
    super(simGroup);
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
      current.setDisplayColor(Color.BLUE);
      current.setNextState("BLUE");
    }
    else if(current.getCurrentState().equals("BLUE")){
      current.setDisplayColor(Color.RED);
      current.setNextState("RED");
    }

  }
}
