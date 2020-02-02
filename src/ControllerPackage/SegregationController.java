package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class SegregationController extends Controller {


  private final static double percentOccupied = .9;
  private final static double ratio = .7;
  private final static double satisfiedLevel = .3;

  private ArrayList<Cell> emptySpots;
  private ArrayList<Cell> nextEmpty;


  private class State {

    private String state;

    public State(String state) {
      this.state = state;
    }

    public String getState() {
      return state;
    }

    @Override
    public boolean equals(Object a) {
      String s = a.toString();
      return s.equals(state);
    }
  }

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

      double stateSelect = a.nextDouble();

      if (stateSelect > percentOccupied) {
        cell.setCurrentState(new State("EMPTY"));
      }
      if (stateSelect <= percentOccupied) {
        if (a.nextDouble() < ratio) {
          cell.setCurrentState(new State("AGENT0"));
        } else {
          cell.setCurrentState(new State("AGENT1"));
        }
      }
      calcNewDisplay(cell);
    }
    emptySpots = getEmptySpots("EMPTY");
    nextEmpty = new ArrayList<>();

  }

  @Override
  protected void updateGrid() {
    ArrayList<Cell> trial = new ArrayList<>(emptySpots);
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      updateCell(x, y);
    }
    emptySpots.addAll(nextEmpty);
    nextEmpty.clear();
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);
    if (current.getNextState() != null) {
      return;
    }
    if (current.getCurrentState().equals("EMPTY")) {
      current.setNextState(new State("EMPTY"));
      return;
    }

    String currentState = ((State) current.getCurrentState()).getState();

    ArrayList<Cell> neigh = currentModel.getMooreNeighborhood(x, y);
    double totalNeigh = 0;
    double similar = 0;

    for (Cell c : neigh) {
      if (c.getCurrentState().equals(currentState)) {
        similar++;
      }
      if (!c.getCurrentState().equals("EMPTY")) {
        totalNeigh++;
      }
    }

    if (totalNeigh > 0 && similar / totalNeigh < satisfiedLevel && emptySpots.size() > 0) {

      Random r = new Random();
      int index = r.nextInt(emptySpots.size());
      Cell cellReplace = emptySpots.get(index);

      cellReplace.setNextState(new State(currentState));

      current.setNextState(new State("EMPTY"));

      emptySpots.remove(cellReplace);

      nextEmpty.add(current);
      return;
    }

    current.setNextState(current.getCurrentState());
  }

  @Override
  protected void calcNewDisplay(Cell cell) {

    switch (((State) cell.getCurrentState()).getState()) {
      case "EMPTY":
        cell.setDisplayColor(Color.LIGHTGRAY);
        break;
      case "AGENT0":
        cell.setDisplayColor(Color.DARKBLUE);
        break;
      case "AGENT1":
        cell.setDisplayColor(Color.DARKGOLDENROD);
        break;

    }
  }

  private ArrayList<Cell> getEmptySpots(String state) {
    ArrayList<Cell> ret = new ArrayList<>();

    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      if (currentModel.getCell(x, y).getCurrentState().equals(state)) {
        ret.add(currentModel.getCell(x, y));
      }
    }
    return ret;
  }
}
