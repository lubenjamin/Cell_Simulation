package utils;

import controllerPackage.State;

import java.util.ArrayList;

public class Cell {
  private int newStateFromClick;
  private int maxState;
  private boolean clickedCell;
  private State currentState;
  private State nextState;
  private final int x;
  private final int y;

  public Cell(int x,int y, int maxState){
    clickedCell = false;
    currentState = new State(0);
    nextState = null;
    this.x = x;
    this.y = y;
    this.maxState = maxState;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public State getNextState() {
    return nextState;
  }

  public State getCurrentState(){
    return currentState;
  }

  public void setCurrentState(State currentState) {
    this.currentState = currentState;
    newStateFromClick = currentState.getState();
  }

  public void setNextState(State nextState) {
    this.nextState = nextState;
  }

  public void incrementState(){
    clickedCell = true;
    newStateFromClick = (newStateFromClick + 1) % maxState;
  }
  public boolean isClickedCell(){
    if(clickedCell){
      clickedCell = false;
      return true;
    }
    return false;
  }
  public int getNewStateFromClick(){
    return newStateFromClick;
  }
}
