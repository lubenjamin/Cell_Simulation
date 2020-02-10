package utils;

import controllerPackage.State;

import java.util.ArrayList;

public class Cell {

  private State currentState;
  private State nextState;
  private String displayColor;
  private final int x;
  private final int y;
  private ArrayList<String> myStates;

  public Cell(int x,int y){
    currentState = null;
    nextState = null;
    displayColor = "000000";
    myStates = new ArrayList<>();
    this.x = x;
    this.y = y;
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

  public String getDisplayColor() {
    return displayColor;
  }

  public State getCurrentState(){
    return currentState;
  }

  public void setCurrentState(State currentState) {
    this.currentState = currentState;
  }

  public void setNextState(State nextState) {
    this.nextState = nextState;
  }

  public void setDisplayColor(String displayColor) {
    this.displayColor = displayColor;
  }

  public void addState(String state) {
    myStates.add(state);
  }
  public void changeColor(int stateNum) {
    String state = myStates.get(stateNum);
    setDisplayColor(state);
  }
}
