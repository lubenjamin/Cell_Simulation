package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class SegregationController extends Controller {


  private final static double percentOccupied = .7;
  private final static double percentMajority = .75;
  private final static double satisfiedLevel = .9;

  private ArrayList<Cell> emptySpots;
  private ArrayList<Cell> needMove;


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
        if (a.nextDouble() < percentMajority) {
          cell.setCurrentState(new State("MAJORITY"));
        } else {
          cell.setCurrentState(new State("MINORITY"));
        }
      }
      calcNewDisplay(cell);
    }


  }

  @Override
  protected void updateGrid() {
    needMove = new ArrayList<>();
    emptySpots = getEmptySpots("EMPTY");
    ArrayList<Cell> trial = new ArrayList<>(emptySpots);
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      updateCell(x, y);
    }
    moveUnHappy();

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

    if (totalNeigh > 0 && similar / totalNeigh < satisfiedLevel) {
      needMove.add(current);
    }

    current.setNextState(current.getCurrentState());
  }

  @Override
  protected void calcNewDisplay(Cell cell) {

    switch (((State) cell.getCurrentState()).getState()) {
      case "EMPTY":
        cell.setDisplayColor(Color.LIGHTGRAY);
        break;
      case "MAJORITY":
        cell.setDisplayColor(Color.DARKBLUE);
        break;
      case "MINORITY":
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

  /**
   * This method picks a random cell that needs moving
   * and a random empty spot. This is because at the beginning
   * there are not enough empty spots to relocate cells, so
   * it needs to be random
   */
  private void moveUnHappy() {
    Random r = new Random();
    while(emptySpots.size()!=0 && needMove.size()!=0){
      int indexTo = r.nextInt(emptySpots.size());
      int indexFrom = r.nextInt(needMove.size());
      Cell cellReplace = emptySpots.get(indexTo);
      Cell current = needMove.get(indexFrom);
      cellReplace.setNextState(new State(((State)current.getCurrentState()).getState()));
      current.setNextState(new State("EMPTY"));
      emptySpots.remove(cellReplace);
      needMove.remove(current);
    }

  }


}



