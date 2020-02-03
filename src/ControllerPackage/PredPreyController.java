
package ControllerPackage;

import View.PredPreyGraph;
import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class PredPreyController extends Controller {


  private final static int sharkBreed = 20;
  private final static int fishBreed = 6;
  private final static int fishEnergy = 3;
  private final static int sharkStarve = 5;

  private final static double percentOccupied = .6;
  private final static double percentFish = .95;

  private ArrayList<Cell> sharkNeedMove;
  private ArrayList<Cell> fishNeedMove;

  private PredPreyGraph myGraph;


  public PredPreyController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
    myGraph = new PredPreyGraph();
    simGroup.getChildren().add(myGraph);
  }

  @Override
  protected void initializeModel() {
    Random a = new Random(201);
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i / WIDTH_CELLS;
      int y = i % WIDTH_CELLS;

      Cell cell = currentModel.getCell(x, y);

      double stateSelect = a.nextDouble();

      if (stateSelect > percentOccupied) {
        cell.setCurrentState(new PPState("EMPTY"));
      }
      if (stateSelect <= percentOccupied) {
        if (a.nextDouble() < percentFish) {
          cell.setCurrentState(new PPState("FISH"));
        } else {
          cell.setCurrentState(new PPState("SHARK"));
        }
      }
      calcNewDisplay(cell);
    }

  }

  @Override
  protected void updateGrid() {
    sharkNeedMove = new ArrayList<>();
    fishNeedMove = new ArrayList<>();
    super.updateGrid();
    moveSharks();
    moveFish();
    myGraph.update(countShark(),countFish());
  }
  public int countFish(){
    int count =0;
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x,y);
      if(current.getCurrentState().getState().equals("FISH")){
        count ++;
      }
    }
    System.out.println(count);
    return count;
  }
  public int countShark(){
    int count =0;
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x,y);
      if(current.getCurrentState().getState().equals("SHARK")){
        count ++;
      }
    }
    System.out.println(count);
    return count;
  }
  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);
    ((PPState)current.getCurrentState()).dec();

    if (current.getCurrentState().equals("EMPTY") || ((PPState)current.getCurrentState()).checkLife()) {
      current.setNextState(new PPState("EMPTY"));
      return;
    }

    String currentStateName = current.getCurrentState().getState();

    if (currentStateName.equals("SHARK")) {
      sharkNeedMove.add(current);
    }
    if(currentStateName.equals("FISH")) {
      fishNeedMove.add(current);
    }


  }


  private void moveFish() {
    Random r = new Random();
    while (fishNeedMove.size() > 0) {
      int index = r.nextInt(fishNeedMove.size());
      Cell current = fishNeedMove.get(index);
      fishNeedMove.remove(current);
      Cell move;

      if (current.getNextState()!=null) {
        continue;
      }

      ArrayList<Cell> neigh = currentModel.getTorusNeighborhood(current.getX(), current.getY());
      ArrayList<Cell> empty = new ArrayList<>();
      for (Cell c : neigh) {
        if (c.getNextState()!=null && c.getNextState().equals("EMPTY")) {
          empty.add(c);
        }
      }

      if(empty.size()==0){
        current.setNextState(new PPState((PPState) current.getCurrentState()));
        continue;
      }

      int moveIndex = r.nextInt(empty.size());
      move = empty.get(moveIndex);
      checkBreedAndRepo(current);
      move.setNextState(new PPState((PPState) current.getCurrentState()));

    }

  }

  private void moveSharks() {
    Random r = new Random();
    while (sharkNeedMove.size() > 0) {

      int index = r.nextInt(sharkNeedMove.size());
      Cell current = sharkNeedMove.get(index);
      sharkNeedMove.remove(current);
      Cell move;


      ArrayList<Cell> neigh = currentModel.getTorusNeighborhood(current.getX(), current.getY());
      ArrayList<Cell> fishLoc = new ArrayList<>();
      ArrayList<Cell> empty = new ArrayList<>();
      for (Cell c : neigh) {
        if (c.getCurrentState().equals("FISH") && c.getNextState()==null) {
          fishLoc.add(c);
        }
        if (c.getCurrentState().equals("EMPTY") && (c.getNextState()==null || !c.getNextState().equals("SHARK"))) {
          empty.add(c);
        }
      }

      if (fishLoc.size() > 0) {
        int place = r.nextInt(fishLoc.size());
        move = fishLoc.get(place);
      } else if (empty.size() > 0) {
        int place = r.nextInt(empty.size());
        move = empty.get(place);
      } else {
        current.setNextState(new PPState((PPState) current.getCurrentState()));
        continue;
      }
      moveSharkToSpot(move, current);
    }
  }

  private void moveSharkToSpot(Cell move, Cell current) {
    checkBreedAndRepo(current);
    move.setNextState(new PPState((PPState)current.getCurrentState()));
    if (move.getCurrentState().equals("FISH")) {
      ((PPState) move.getNextState()).eat();
    }

  }


  private void checkBreedAndRepo(Cell current) {

    if (((PPState) current.getCurrentState()).checkBreed()) {
      current.setNextState(new PPState(current.getCurrentState().getState()));
      ((PPState) current.getCurrentState()).resetBreed();
    } else {
      current.setNextState(new PPState("EMPTY"));
    }
  }


  @Override
  protected void calcNewDisplay(Cell cell) {

    switch (cell.getCurrentState().getState()) {
      case "EMPTY":
        cell.setDisplayColor(Color.LIGHTGRAY);
        break;
      case "SHARK":
        cell.setDisplayColor(Color.DARKGOLDENROD);
        break;
      case "FISH":
        cell.setDisplayColor(Color.GREEN);
        break;

    }
  }

  protected class PPState extends State {

    private int life;
    private int breed;

    public PPState(String state) {
      super(state);
      if (state.equals("SHARK")) {
        life = sharkStarve;
        breed = sharkBreed;
      }
      if (state.equals("FISH")) {
        breed = fishBreed;
      }
    }


    public PPState(PPState prevState) {
      super(prevState.getState());
      if (prevState.getState().equals("SHARK")) {
        life = prevState.getLife();
        breed = prevState.getBreed();
      }
      if (prevState.getState().equals("FISH")) {
        breed = prevState.getBreed();
      }
    }

    private int getBreed() {
      return breed;
    }

    private int getLife() {
      return life;
    }

    @Override
    public boolean equals(Object a) {
      String s = a.toString();
      return s.equals(state);
    }

    public String getState() {
      return state;
    }

    public void dec() {
      if (state.equals("SHARK")) {
        breed--;
        life--;
      }
      if (state.equals("FISH")) {
        breed--;
      }
    }

    public boolean checkBreed() {
      return breed <= 0;
    }

    public void resetBreed() {
      if (state.equals("SHARK")) {
        breed = sharkBreed;
      }
      if (state.equals("FISH")) {
        breed = fishBreed;
      }
    }

    public boolean checkLife() {
      if(state.equals("SHARK"))return life <= 0;
      return false;

    }

    public void eat() {
      life = Math.min(sharkStarve, life + fishEnergy);
    }
  }


}


