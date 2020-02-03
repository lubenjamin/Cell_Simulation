
package ControllerPackage;

import cellsociety.Cell;
import cellsociety.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class PredPreyController extends Controller {


  private final static int sharkBreed = 25;
  private final static int fishBreed = 10;
  private final static int fishEnergy = 1;
  private final static int sharkStarve = 20;

  private final static double percentOccupied = .6;
  private final static double percentFish = .95;

  private ArrayList<Cell> sharkNeedMove;
  private ArrayList<Cell> fishNeedMove;

  //EMPTY = 0 : FISH = 1 : SHARK : 2;
  public PredPreyController(Group simGroup, FileReader reader) {
    super(simGroup, reader);
  }

  @Override
  protected void initializeCellState(Cell current){
    if (probabilityChecker(percentOccupied)){
      if (probabilityChecker(percentFish)) {
        current.setCurrentState(new PPState(1));
        ((PPState) current.getCurrentState()).setBreed(random.nextInt(fishBreed+1));
      } else {
        current.setCurrentState(new PPState(2));
        ((PPState) current.getCurrentState()).setBreed(random.nextInt(sharkBreed+1));
      }
    }
    else{
      current.setCurrentState(new PPState(0));
    }
  }

  @Override
  protected void setColors() {
    state0Color =Color.LIGHTGRAY;
    state1Color = Color.GREEN;
    state2Color = Color.DARKGOLDENROD;
  }

  @Override
  protected void updateGrid() {
    sharkNeedMove = new ArrayList<>();
    fishNeedMove = new ArrayList<>();
    super.updateGrid();
    moveSharks();
    moveFish();
  }

  public void countFish(){
    int count =0;
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell current = currentModel.getCell(x,y);
      if(current.getCurrentState().getState()==1){
        count ++;
      }
    }
    System.out.println(count);
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell current = currentModel.getCell(x, y);
    ((PPState)current.getCurrentState()).dec();
    if (current.getCurrentState().getState()==0 || ((PPState)current.getCurrentState()).checkLife()) {
      current.setNextState(new PPState(0));
    }
    else if (current.getCurrentState().getState() == 2) {
      sharkNeedMove.add(current);
    }
    else{
      fishNeedMove.add(current);
    }
  }


  private void moveFish() {
    while (fishNeedMove.size() > 0) {
      int index = random.nextInt(fishNeedMove.size());
      Cell current = fishNeedMove.get(index);
      fishNeedMove.remove(current);
      if (current.getNextState()!=null) {
        continue;
      }
      ArrayList<Cell> emptyNeighbors = getEmptyNextState(current);
      if(emptyNeighbors.size()==0){
        current.setNextState(new PPState((PPState) current.getCurrentState()));
      }
      else{
        int moveIndex = random.nextInt(emptyNeighbors.size());
        moveFishToSpot(emptyNeighbors.get(moveIndex), current);
      }
    }
  }

  private void moveSharks() {
    while (sharkNeedMove.size() > 0) {
      int index = random.nextInt(sharkNeedMove.size());
      Cell current = sharkNeedMove.get(index);
      sharkNeedMove.remove(current);

      ArrayList<Cell> fishLoc = getFishNeighbors(current);
      ArrayList<Cell> empty = getEmptyNextState(current);

      if (fishLoc.size() > 0) {
        int place = random.nextInt(fishLoc.size());
        moveSharkToSpot(fishLoc.get(place), current);
      } else if (empty.size() > 0) {
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
    if (too.getCurrentState().getState()==1) {
      ((PPState) too.getNextState()).eat();
    }
  }
  private void moveFishToSpot(Cell too, Cell from) {
    checkBreedAndRepo(from);
    too.setNextState(new PPState((PPState) from.getCurrentState()));
  }
  private ArrayList<Cell> getEmptyNextState(Cell current) {
    ArrayList<Cell> neigh = currentModel.getTorusNeighborhood(current.getX(), current.getY());
    ArrayList<Cell> empty = new ArrayList<>();
    for (Cell c : neigh) {
      if (c.getCurrentState().getState()==0 && (c.getNextState()==null || c.getNextState().getState()==0)) {
        empty.add(c);
      }
    }
    return empty;
  }
  private ArrayList<Cell> getFishNeighbors(Cell current) {
    ArrayList<Cell> neigh = currentModel.getTorusNeighborhood(current.getX(), current.getY());
    ArrayList<Cell> fish = new ArrayList<>();
    for (Cell c : neigh) {
      if (c.getCurrentState().getState()==1) {
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




  protected static class PPState extends State {

    private int life;
    private int breed;

    public PPState(int state) {
      super(state);
      if (state==2) {
        life = sharkStarve;
        breed = sharkBreed;
      }
      if (state==1) {
        breed = fishBreed;
      }
    }

    public PPState(PPState prevState) {
      super(prevState.getState());
      breed = prevState.getBreed();
      if (prevState.getState()==2) {
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
      if (state==2) {
        life--;
      }
    }

    public boolean checkBreed() {
      return breed <= 0;
    }

    public void resetBreed() {
      if (state==2) {
        breed = sharkBreed;
      }
      if (state==1) {
        breed = fishBreed;
      }
    }

    public boolean checkLife() {
      if(state==2)return life <= 0;
      return false;
    }
    
    public void eat() {
      life = Math.min(sharkStarve, life + fishEnergy);
    }
  }


}


