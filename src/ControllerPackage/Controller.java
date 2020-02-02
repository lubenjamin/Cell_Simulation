package ControllerPackage;

import View.View;
import cellsociety.Cell;
import cellsociety.FileReader;
import cellsociety.Model;
import javafx.scene.Group;



public abstract class Controller {
  protected Model currentModel;
  protected View currentView;

  protected int WIDTH_CELLS;
  protected int HEIGHT_CELLS;


  public Controller(Group simGroup, FileReader reader){
    WIDTH_CELLS = reader.getColumns();
    HEIGHT_CELLS = reader.getRows();
    currentModel = new Model(WIDTH_CELLS,HEIGHT_CELLS);
    currentView = new View(simGroup, WIDTH_CELLS, HEIGHT_CELLS, currentModel);
    initializeModel();
  }

  public void updateSim(){
    updateGrid();
    switchGridStates();
    currentView.updateAllCells();
  }

  public void resetSim(){

    initializeModel();
    currentView.updateAllCells();

  }

  private void switchGridStates(){
    for(int i = 0; i < WIDTH_CELLS*HEIGHT_CELLS; i++){
      int x = i % WIDTH_CELLS;
      int y = i/WIDTH_CELLS;
      Cell current = currentModel.getCell(x,y);
      current.setCurrentState(current.getNextState());
      calcNewDisplay(current);
    }
  }

  protected abstract void initializeModel();

  protected abstract void updateGrid();

  protected abstract void updateCell(int x, int y);

  protected abstract void calcNewDisplay(Cell cell);

}
