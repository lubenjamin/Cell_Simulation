/*

package ControllerPackage;


import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;



public class SegregationController extends Controller {

  public SegregationController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected void initializeModel() {
    Random a = new Random();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i / WIDTH_CELLS;
      int y = i % WIDTH_CELLS;
      Cell cell = currentModel.getCell(x, y);
      int stateSelect = a.nextInt(10);
      if(stateSelect<4){
        cell.setCurrentState("OPEN");
      }
      if(stateSelect>=4){
        cell.setCurrentState("CLOSED");
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
    Cell current = currentModel.getCell(x, y);
    if (current.getCurrentState().equals("CLOSED")){
      current.setNextState("CLOSED");
      return;
    }
    if (y == 0 && current.getCurrentState().equals("OPEN")) {
      current.setNextState("PERC");
      return;
    }


    for (Cell c : currentModel.getMooreNeighborhood(x, y)) {
      if (c.getCurrentState().equals("PERC")) {
        current.setNextState("PERC");
        return;
      }
    }

    current.setNextState(current.getCurrentState());
  }

  @Override
  protected void calcNewDisplay(Cell cell) {
    switch (cell.getCurrentState()){
      case "OPEN":
        cell.setDisplayColor(Color.WHITE);
        break;
      case "CLOSED":
        cell.setDisplayColor(Color.BLACK);
        break;
      case "PERC":
        cell.setDisplayColor(Color.LIGHTBLUE);
        break;
    }
  }
}
*/
