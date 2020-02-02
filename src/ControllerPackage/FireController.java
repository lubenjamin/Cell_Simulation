package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class FireController extends Controller {


  private final static double initialTree = .99;
  private final static double initialBurningTree = .001;
  private final static double percentCatchFire = .5;

  private class State{
    private String state;
    public State(String state){
      this.state = state;
    }
    public String getState(){
      return state;
    }
    @Override
    public boolean equals(Object a ){
      String s = (String) a;
      return s.equals(state);
    }
  }

  public FireController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected void initializeModel() {
    boolean burnCheck = false;
    Random a = new Random();
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i / WIDTH_CELLS;
      int y = i % WIDTH_CELLS;


      Cell cell = currentModel.getCell(x, y);

      double stateSelect = a.nextDouble();

      if(stateSelect>initialTree){
        cell.setCurrentState(new State("EMPTY"));
      }

      if(stateSelect<=initialTree){
        if(a.nextDouble()<=initialBurningTree){
          cell.setCurrentState(new State("BURNING"));
          burnCheck=true;
        }
        else{
          cell.setCurrentState(new State("TREE"));
        }
      }
      calcNewDisplay(cell);
    }

    if (!burnCheck){
      System.out.println("h2");
      int x = a.nextInt(WIDTH_CELLS);
      int y = a.nextInt(HEIGHT_CELLS);
      Cell cell = currentModel.getCell(x,y);
      cell.setCurrentState(new State("BURNING"));
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

    if (current.getCurrentState().equals("EMPTY") || current.getCurrentState().equals("BURNING")) {
      current.setNextState(new State("EMPTY"));
      return;
    }

    ArrayList<Cell> neigh = currentModel.getSimpleNeighborhood(x, y);
    int numOnFire = 0;
    for (Cell c : neigh) {
      if (c.getCurrentState().equals("BURNING")) numOnFire++;
    }

    if (numOnFire == 0) {
      current.setNextState(new State("TREE"));
      return;
    }

    Random a = new Random();
    double stateSelect = a.nextDouble();
    if (stateSelect <= percentCatchFire) {
      current.setNextState(new State("BURNING"));
      return;
    }

    current.setNextState(new State("TREE"));
  }

  @Override
  protected void calcNewDisplay(Cell cell) {
    switch (((State)cell.getCurrentState()).getState()){
      case "BURNING":
        cell.setDisplayColor(Color.RED);
        break;
      case "EMPTY":
        cell.setDisplayColor(Color.WHITE);
        break;
      case "TREE":
        cell.setDisplayColor(Color.GREEN);
        break;

    }
  }
}
