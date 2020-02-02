package ControllerPackage;

import View.View;
import cellsociety.Cell;
import cellsociety.FileReader;
import cellsociety.Model;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public abstract class Controller {
  protected Model currentModel;
  protected View currentView;

  protected int WIDTH_CELLS;
  protected int HEIGHT_CELLS;

  protected Color state0Color;
  protected Color state1Color;
  protected Color state2Color;


  public Controller(Group simGroup, FileReader reader){
    setColors();
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
      current.setNextState(null);
      calcNewDisplay(current);
    }
  }

  protected void updateGrid(){
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      updateCell(x, y);
    }
  }

  protected abstract void setColors();

  protected void initializeModel(){
    Random a = new Random();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i / WIDTH_CELLS;
      int y = i % WIDTH_CELLS;
      Cell cell = currentModel.getCell(x, y);
      initializeCellState(cell, a);
      calcNewDisplay(cell);
    }
  }

  protected abstract void initializeCellState(Cell cell, Random a);

  protected abstract void updateCell(int x, int y);


  protected void calcNewDisplay(Cell cell) {

    switch (cell.getCurrentState().getState()) {
      case 0:
        cell.setDisplayColor(state0Color);
        break;
      case 1:
        cell.setDisplayColor(state1Color);
        break;
      case 2:
        cell.setDisplayColor(state2Color);
        break;

    }
  }

}
