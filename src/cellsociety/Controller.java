package cellsociety;

import View.View;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class Controller {
  private Model currentModel;
  private View currentView;

  private final static int WIDTH_CELLS = 100;
  private final static int HEIGHT_CELLS = 100;

  public Controller (Group simGroup){
    currentModel = new Model(WIDTH_CELLS,HEIGHT_CELLS);
    currentView = new View(simGroup, WIDTH_CELLS, HEIGHT_CELLS, currentModel);
  }

  public void updateSim(){
    updateGrid();
    switchGridStates();
    updateView();
  }
  private void updateGrid(){
    for(int i = 0; i < WIDTH_CELLS*HEIGHT_CELLS; i++){
      int x = i % WIDTH_CELLS;
      int y = i/WIDTH_CELLS;
      updateCell(x,y);
    }
  }

  private void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x,y);

    current.setNextState(current.getCurrentState());
    current.setDisplayColor(Color.rgb(255,0,0));


  }

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
