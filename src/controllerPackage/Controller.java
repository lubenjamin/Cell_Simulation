package controllerPackage;

import View.View;
import utils.Cell;
import utils.FileReader;
import utils.Model;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public abstract class Controller {

  protected final Model currentModel;
  protected final View currentView;
  protected final Random random;
  protected final FileReader reader;

  protected final int WIDTH_CELLS;
  protected final int HEIGHT_CELLS;
  protected double spacing;

  protected String state0Color;
  protected String state1Color;
  protected String state2Color;



  public Controller(Group simGroup, FileReader reader) {
    this.reader = reader;
    random = new Random();
    setSimParams();
    setSimColor();

    WIDTH_CELLS = reader.getColumns();
    HEIGHT_CELLS = reader.getRows();
    currentModel = new Model(WIDTH_CELLS, HEIGHT_CELLS);
    currentView = new View(simGroup, WIDTH_CELLS, HEIGHT_CELLS, currentModel, spacing);
    initializeModel();
    currentView.updateAllCells();
  }

  public void updateSim() {
    updateGrid();
    switchGridStates();
    currentView.updateAllCells();
  }

  public void resetSim(){
    initializeModel();
    currentView.updateAllCells();

  }
  public void clear() {
    currentView.clear();
  }
  private void switchGridStates(){
    for(int i = 0; i < WIDTH_CELLS*HEIGHT_CELLS; i++){

      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x, y);

      current.setCurrentState(current.getNextState());
      current.setNextState(null);
      calcNewDisplay(current);
    }
  }

  protected void updateGrid() {
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      updateCell(x, y);
    }
  }

  protected void initializeModel() {
    Random a = new Random();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell cell = currentModel.getCell(x, y);
      initializeCellState(cell);
      calcNewDisplay(cell);
    }
  }

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

  protected void setSimColor(){
    state0Color = reader.getString("state0Color");
    state1Color = reader.getString("state1Color");
    state2Color = reader.getString("state2Color");
  }

  protected boolean probabilityChecker(double compareTo) {
    double stateSelect = random.nextDouble();
    return stateSelect < compareTo;

  }


  protected abstract void setSimParams();

  protected abstract void initializeCellState(Cell cell);

  protected abstract void updateCell(int x, int y);


}
