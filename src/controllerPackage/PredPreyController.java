package controllerPackage;

import View.PredPreyGraph;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Group;
import utils.Cell;
import utils.FileReader;

/**
 * This class creates the Pred Prey simulation.
 *
 * It uses 3 states, Kelp, Fish, and Shark.
 *
 * The specific params are time for shark breed, fish breed,
 * amount of energy gained from fish, and time for sharks to starve.
 *
 * Additionally, the percent occupies and percent fish determine starting ratios.
 */
public class PredPreyController extends Controller {


  private int sharkBreed;
  private int fishBreed;
  private int fishEnergy;
  private int sharkStarve;

  private double percentOccupied;
  private double percentFish;

  private ArrayList<Cell> sharkNeedMove;
  private ArrayList<Cell> fishNeedMove;


  private PredPreyGraph myGraph;

  private int numFish;
  private int numShark;


  public PredPreyController(Group simGroup, FileReader reader, Group simUIGroup,
      PredPreyGraph graph) {
    super(simGroup, reader, simUIGroup);
    this.myGraph = graph;
  }

  @Override
  protected HashMap<String, Object> getSimParamsForUi() {
    HashMap<String, Object> ret = new HashMap<>();
    ret.put("sharkBreed", sharkBreed);
    ret.put("fishBreed", fishBreed);
    ret.put("fishEnergy", fishEnergy);
    ret.put("percentOccupied", percentOccupied);
    ret.put("percentFish", percentFish);
    ret.put("sharkStarve", sharkStarve);
    return ret;
  }

  @Override
  protected void setSimParamsFromUI() {
    HashMap<String, Object> values = (HashMap<String, Object>) simUI.getValues();
    sharkBreed = (int) values.get("sharkBreed");
    fishBreed = (int) values.get("fishBreed");
    fishEnergy = (int) values.get("fishEnergy");
    percentOccupied = (double) values.get("percentOccupied");
    percentFish = (double) values.get("percentFish");
    sharkStarve = (int) values.get("sharkStarve");
  }

  @Override
  protected void setCurrentState(Cell current, int newStateFromClick) {
    current.setCurrentState(new PPState(newStateFromClick));
  }


  @Override
  protected void initializeCellState(Cell current) {
    if (probabilityChecker(percentOccupied)) {
      if (probabilityChecker(percentFish)) {
        current.setCurrentState(new PPState(1));
        ((PPState) current.getCurrentState()).setBreed(random.nextInt(fishBreed + 1));
      } else {
        current.setCurrentState(new PPState(2));
        ((PPState) current.getCurrentState()).setBreed(random.nextInt(sharkBreed + 1));
      }
    } else {
      current.setCurrentState(new PPState(0));
    }

  }

  @Override
  protected void setSimParams() {
    sharkBreed = reader.getIntValue("sharkBreed");
    fishBreed = reader.getIntValue("fishBreed");
    fishEnergy = reader.getIntValue("fishEnergy");
    sharkStarve = reader.getIntValue("sharkStarve");
    percentOccupied = reader.getDoubleValue("percentOccupied");
    percentFish = reader.getDoubleValue("percentFish");
    spacing = reader.getDoubleValue("spacing");
    maxState = 2;
  }

  @Override
  protected void updateGrid() {
    sharkNeedMove = new ArrayList<>();
    fishNeedMove = new ArrayList<>();
    super.updateGrid();
    moveSharks();
    moveFish();

    countSpecies();
    myGraph.update(numShark, numFish);
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);
    if (current.getCurrentState().getState() == 0 || ((PPState) current.getCurrentState())
        .checkLife()) {
      current.setNextState(new PPState(0));
    } else if (current.getCurrentState().getState() == 2) {
      sharkNeedMove.add(current);
    } else {
      fishNeedMove.add(current);
    }
    ((PPState) current.getCurrentState()).dec();
  }

  private void moveFish() {
    while (!fishNeedMove.isEmpty()) {
      int index = random.nextInt(fishNeedMove.size());
      Cell current = fishNeedMove.remove(index);
      if (current.getNextState() != null) {
        continue;
      }
      ArrayList<Cell> emptyNeighbors = getEmptyNextState(current);
      if (emptyNeighbors.isEmpty()) {
        current.setNextState(new PPState((PPState) current.getCurrentState()));
      } else {
        int moveIndex = random.nextInt(emptyNeighbors.size());
        moveFishToSpot(emptyNeighbors.get(moveIndex), current);
      }
    }
  }

  private void moveSharks() {
    while (!sharkNeedMove.isEmpty()) {
      int index = random.nextInt(sharkNeedMove.size());
      Cell current = sharkNeedMove.remove(index);
      ArrayList<Cell> fishLoc = getFishNeighbors(current);
      ArrayList<Cell> empty = getEmptyNextState(current);
      if (!fishLoc.isEmpty()) {
        int place = random.nextInt(fishLoc.size());
        moveSharkToSpot(fishLoc.get(place), current);
      } else if (!empty.isEmpty()) {
        int place = random.nextInt(empty.size());
        moveSharkToSpot(empty.get(place), current);
      } else {
        current.setNextState(new PPState((PPState) current.getCurrentState()));
      }
    }
  }

  private void moveSharkToSpot(Cell too, Cell from) {
    checkBreedAndRepo(from);
    too.setNextState(new PPState((PPState) from.getCurrentState()));
    if (too.getCurrentState().getState() == 1) {
      ((PPState) too.getNextState()).eat();
    }
  }

  private void moveFishToSpot(Cell too, Cell from) {
    checkBreedAndRepo(from);
    too.setNextState(new PPState((PPState) from.getCurrentState()));
  }

  private ArrayList<Cell> getEmptyNextState(Cell current) {
    ArrayList<Cell> neigh = (ArrayList<Cell>) currentModel
        .getTorusNeighborhood(current.getX(), current.getY());
    ArrayList<Cell> empty = new ArrayList<>();
    for (Cell c : neigh) {
      if (c.getCurrentState().getState() == 0 && (c.getNextState() == null
          || c.getNextState().getState() == 0)) {
        empty.add(c);
      }
    }
    return empty;
  }

  private ArrayList<Cell> getFishNeighbors(Cell current) {
    ArrayList<Cell> neigh = (ArrayList<Cell>) currentModel
        .getTorusNeighborhood(current.getX(), current.getY());
    ArrayList<Cell> fish = new ArrayList<>();
    for (Cell c : neigh) {
      if (c.getCurrentState().getState() == 1) {
        fish.add(c);
      }
    }
    return fish;
  }

  private void checkBreedAndRepo(Cell current) {
    if (((PPState) current.getCurrentState()).checkBreed()) {
      current.setNextState(new PPState(current.getCurrentState().getState()));
      ((PPState) current.getCurrentState()).resetBreed();
    } else {
      current.setNextState(new PPState(0));
    }
  }

  public void countSpecies() {
    numFish = 0;
    numShark = 0;
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x, y);
      if (current.getCurrentState().getState() == 1) {
        numFish++;
      }
      if (current.getCurrentState().getState() == 2) {
        numShark++;
      }
    }
  }

  protected class PPState extends State {

    private int life;
    private int breed;

    public PPState(int state) {
      super(state);
      if (state == 2) {
        life = sharkStarve;
        breed = sharkBreed;
      }
      if (state == 1) {
        breed = fishBreed;
      }
    }

    public PPState(PPState prevState) {
      super(prevState.getState());
      breed = prevState.getBreed();
      if (prevState.getState() == 2) {
        life = prevState.getLife();
      }

    }

    private int getBreed() {
      return breed;
    }

    private void setBreed(int newBreed) {
      breed = newBreed;
    }

    private int getLife() {
      return life;
    }

    public int getState() {
      return state;
    }

    public void dec() {
      breed--;
      if (state == 2) {
        life--;
      }
    }

    public boolean checkBreed() {
      return breed <= 0;
    }

    public void resetBreed() {
      if (state == 2) {
        breed = sharkBreed;
      }
      if (state == 1) {
        breed = fishBreed;
      }
    }

    public boolean checkLife() {
      if (state == 2) {
        return life <= 0;
      }
      return false;
    }

    public void eat() {
      //life = life + fishEnergy;
      life = Math.min(sharkStarve, life + fishEnergy);
    }
  }
}


