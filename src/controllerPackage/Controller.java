package controllerPackage;


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


/**
 * @author connorhazen
 * The controller Class is used to create each simulation.
 * It handles both the view and model while providing sim logic and specific UI elements.
 *
 * The Class is dependent upon the view having update methods and initialize methods.
 * The main dependeices exist with the Model and Cell classes. The controller uses model to
 * receive neighborhoods and specific cells.
 *
 * The Cell class is used to set current and next state for each cell.
 *
 * Making a specific controller object:
 * Controller sim = new FireController(simulationJavaFxGroup, fileReader, simulationUIJavaFxGroup);
 * sim.updateSim();
 * sim.reset();
 *
 */

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


  /**
   * This constructor creates the required view and model. Then it creates initial states.
   * @param simGroup JavaFx group to ba passed to view for cell display
   * @param reader File Reader object use to parse xml file values for initializing simulation.
   * @param simUiGroup avaFx group to ba passed to simSpecificUi for ui elements.
   */
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

    currentView = new View(simGroup, WIDTH_CELLS, HEIGHT_CELLS, currentModel, spacing, colors);

    initializeModel();
    currentView.updateAllCells();
    simUI = new SimSpecificUI(simUiGroup, getSimParamsForUi());
  }

  /**
   * This method steps the simulation forward one generation. This would be called
   * whenever the animation wants to see the next step.
   */
  public void updateSim() {
    updateGrid();
    switchGridStates();
    currentView.updateAllCells();
  }

  /**
   * This is used to reset the simulation back to an initial state.
   * Specifically, this method would be tied to the UI reset button event handler.
   */
  public void resetSim() {
    setSimParamsFromUI();
    initializeModel();
    setSimColor();
    currentView.replaceColors(colors);
    currentView.updateAllCells();
  }

  /**
   * This method is used to read simulation parameters from the xml file.m
   */
  protected abstract void setSimParams();

  /**
   * This method determines the initial state of the grid.
   * Most implemented subclasses use xml params and a random number
   * generator to effectively randomize starting state.
   *
   * The method must set the current state of the passed in Cell object.
   *
   * @param cell cell to be initialized.
   */
  protected abstract void initializeCellState(Cell cell);

  /**
   * This method takes a given cell and creates its next state based
   * off current state and neighborhood. This is where simulation logic would
   * be placed.
   *
   * Perhaps the most important method to implement.
   *
   * @param x x coordinate of cell
   * @param y y coordinate of cell
   */
  protected abstract void updateCell(int x, int y);

  /**
   * This method creates a map of simulation parameters and initial value.
   * Each value must be a double or int.
   *
   * The map is passed to the simSpecificUI class which creates the UI elements.
   * @return Map of <varName, initialVal>
   */
  protected abstract HashMap<String, Object> getSimParamsForUi();

  /**
   * This method works in conjunction to the method above.
   * It updates the initial params when the resetSim method is called.
   * The values are returned in a parallel map to the set method.
   *
   * Each value can be accessed by calling
   * <varName> = simUI.getValues().get(<varName>);
   */
  protected abstract void setSimParamsFromUI();



  /**
   * This method switches current state with next state for each cell.
   */
  private void switchGridStates() {
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {

      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x, y);

      current.setCurrentState(current.getNextState());
      current.setNextState(null);
    }
  }

  private void createModel() {
    if (modelType.equals("Hex")) {
      currentModel = new HexModel(WIDTH_CELLS, HEIGHT_CELLS, maxState);
    } else {
      currentModel = new SquareModel(WIDTH_CELLS, HEIGHT_CELLS, maxState);
    }

  }

  private void checkManualEntry() {
    if (!currentModel.manualEntry) {
      return;
    }
    currentModel.manualEntry = false;
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x, y);
      if (current.isClickedCell()) {
        setCurrentState(current, current.getNewStateFromClick());
      }
    }
  }

  private String randomColor() {
    return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)).toString();
  }

  /**
   * Reads Colors from xml file if present for each state.
   *
   * Otherwise creates random color.
   */
  private void setSimColor() {
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

  protected void updateGrid() {
    checkManualEntry();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      updateCell(x, y);
    }
  }

  /**
   * This method can be overwritten if a simulation uses a subclass of the State class.
   *
   * For example, Predator Prey uses a PPState object due to more complex state attributes.
   * It overwrites this method to create seamless integration.
   * @param current
   * @param newStateFromClick
   */
  protected void setCurrentState(Cell current, int newStateFromClick) {
    current.setCurrentState(new State(newStateFromClick));
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

  protected boolean probabilityChecker(double compareTo) {
    double stateSelect = random.nextDouble();
    return stateSelect < compareTo;
  }

}
