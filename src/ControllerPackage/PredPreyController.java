
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
  protected void initializeCellState(Cell current, Random r){
    double stateSelect = r.nextDouble();

    if (stateSelect > percentOccupied) {
      current.setCurrentState(new PPState(0));
    }
    if (stateSelect <= percentOccupied) {
      if (r.nextDouble() < percentFish) {
        current.setCurrentState(new PPState(1));
        ((PPState) current.getCurrentState()).setBreed(r.nextInt(fishBreed+1));
      } else {
        current.setCurrentState(new PPState(2));
        ((PPState) current.getCurrentState()).setBreed(r.nextInt(sharkBreed+1));
      }
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
      return;
    }

    int currentStateName = current.getCurrentState().getState();

    if (currentStateName == 2) {
      sharkNeedMove.add(current);
    }
    if(currentStateName == 1) {
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
        if (c.getNextState()!=null && c.getNextState().getState()==0) {
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
        if (c.getCurrentState().getState()==1 && c.getNextState()==null) {
          fishLoc.add(c);
        }
        if (c.getCurrentState().getState()==0 && (c.getNextState()==null || c.getNextState().getState()!=2)) {
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
    move.setNextState(new PPState((PPState) current.getCurrentState()));
    if (move.getCurrentState().getState()==1) {
      ((PPState) move.getNextState()).eat();
    }

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
      if (prevState.getState()==2) {
        life = prevState.getLife();
        breed = prevState.getBreed();
      }
      if (prevState.getState()==1) {
        breed = prevState.getBreed();
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

    @Override
    public boolean equals(Object a) {
      String s = a.toString();
      return s.equals(state);
    }

    public int getState() {
      return state;
    }

    public void dec() {
      if (state==2) {
        breed--;
        life--;
      }
      if (state == 1) {
        breed--;
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


