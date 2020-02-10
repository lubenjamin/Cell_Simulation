package controllerPackage;

import java.util.ArrayList;
import javafx.scene.Group;
import utils.Cell;
import utils.FileReader;

public class RockPaperScissors extends Controller{

  private final int numberOfElements = 3;

  public RockPaperScissors(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

t

  @Override
  protected void setSimParams() {


  }

  @Override
  protected void initializeCellState(Cell current) {
    int type = (int) (random.nextDouble()*numberOfElements);
    for(int x = 0; x<numberOfElements; x++){
      if (x == type){
        current.setCurrentState(new State(x));
      }
    }
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x,y);
    ArrayList<Cell> neigh = (ArrayList<Cell>) currentModel.getMooreNeighborhood(x,y);
    int state = current.getCurrentState().getState();

    //array list of what beats



  }
}
