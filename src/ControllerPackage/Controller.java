package ControllerPackage;

import View.View;
import cellsociety.Cell;
import cellsociety.Model;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public abstract class Controller {
  protected Model currentModel;
  protected View currentView;

  protected final static int WIDTH_CELLS = 100;
  protected final static int HEIGHT_CELLS = 100;

  public Controller (Group simGroup){
    currentModel = new Model(WIDTH_CELLS,HEIGHT_CELLS);
    currentView = new View(simGroup, WIDTH_CELLS, HEIGHT_CELLS, currentModel);
  }

  public void updateSim(){
    updateGrid();
    switchGridStates();
    updateView();
  }


  protected abstract void updateGrid();

  protected abstract void updateCell(int x, int y);

  private void switchGridStates(){
    for(int i = 0; i < WIDTH_CELLS*HEIGHT_CELLS; i++){
      int x = i % WIDTH_CELLS;
      int y = i/WIDTH_CELLS;
      Cell current = currentModel.getCell(x,y);
      current.setCurrentState(current.getNextState());
    }

  }

  private void updateView(){
    currentView.updateAllCells();
  }


}
