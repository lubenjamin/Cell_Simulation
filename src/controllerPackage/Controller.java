package controllerPackage;



import View.View;
import View.HexView;
import View.SquareView;
import View.SimSpecificUI;
import View.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import utils.Cell;
import utils.FileReader;
import utils.HexModel;
import utils.Model;
import utils.SquareModel;


public abstract class Controller {

  protected static ArrayList<String> colors;
  protected static int maxState;
  protected final View currentView;
  protected final Random random;
  protected final FileReader reader;
  protected final int WIDTH_CELLS;
  protected final int HEIGHT_CELLS;
  protected Model currentModel;
  protected double spacing;
  protected SimSpecificUI simUI;
  protected String modelType;


  public Controller(Group simGroup, FileReader reader, Group simUiGroup) {
    modelType = "";
    colors = new ArrayList<>();
    this.reader = reader;
    random = new Random();
    WIDTH_CELLS = reader.getColumns();
    HEIGHT_CELLS = reader.getRows();

    setSimParams();
    setSimColor();

    createModel();


    currentView = new SquareView(simGroup, WIDTH_CELLS, HEIGHT_CELLS, currentModel, spacing, colors);


    initializeModel();
    currentView.updateAllCells();
    simUI = new SimSpecificUI(simUiGroup, getSimParamsForUi());
  }

  protected void createModel() {
    if (modelType.equals("Hex")) {
      currentModel = new HexModel(WIDTH_CELLS, HEIGHT_CELLS, maxState);
    } else {
      currentModel = new SquareModel(WIDTH_CELLS, HEIGHT_CELLS, maxState);
    }

  }

  public void updateSim() {
    updateGrid();
    switchGridStates();
    currentView.updateAllCells();
  }

  public void resetSim() {
    setSimParamsFromUI();
    initializeModel();
    setSimColor();
    currentView.replaceColors(colors);
    currentView.updateAllCells();
  }

  private void switchGridStates() {
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {

      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x, y);

      current.setCurrentState(current.getNextState());
      current.setNextState(null);
    }
  }

  protected void updateGrid() {
    checkManualEntry();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      updateCell(x, y);
    }
  }

  protected void checkManualEntry() {
    if (!currentModel.manualEntry) {
      return;
    }
    currentModel.manualEntry = false;
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x, y);
      if (current.isClickedCell()) {
        setState(current, current.getNewStateFromClick());
      }
    }
  }

  protected void initializeModel() {
    Random a = new Random();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell cell = currentModel.getCell(x, y);
      initializeCellState(cell);
    }
  }

  protected String randomColor() {
    return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)).toString();
  }

  protected void setSimColor() {
    colors.clear();
    for (int x = 0; x <= maxState; x++) {
      if (!reader.checkExists("state" + x + "Color")) {
        System.out.println("missing state color: adding random color");
        colors.add(randomColor());
      } else {
        colors.add(reader.getString("state" + x + "Color"));
      }
    }
  }

  protected boolean probabilityChecker(double compareTo) {
    double stateSelect = random.nextDouble();
    return stateSelect < compareTo;
  }

  protected void setState(Cell current, int newStateFromClick) {
    current.setCurrentState(new State(newStateFromClick));
  }


  protected abstract void setSimParams();

  protected abstract void initializeCellState(Cell cell);

  protected abstract void updateCell(int x, int y);

  protected abstract HashMap<String, Object> getSimParamsForUi();

  protected abstract void setSimParamsFromUI();

}
