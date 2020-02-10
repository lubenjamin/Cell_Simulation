package controllerPackage;

import java.util.HashMap;
import javafx.scene.Group;
import utils.Cell;
import utils.FileReader;

public class RockPaperScissors extends Controller {

  private final int numberOfElements = 3;

  public RockPaperScissors(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected HashMap<String, Object> getSimParamsForUi() {
    return null;
  }

  @Override
  protected void setSimParamsFromUI() {

  }

  @Override
  protected void setSimParams() {

  }

  @Override
  protected void initializeCellState(Cell current) {
    int type = (int) (random.nextDouble() * numberOfElements);
    for (int x = 0; x < numberOfElements; x++) {
      if (x == type) {
        current.setCurrentState(new State(x));
      }
    }
  }

  @Override
  protected void updateCell(int x, int y) {

  }
}
