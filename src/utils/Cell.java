package utils;

import controllerPackage.State;
import javafx.scene.paint.Paint;

public class Cell {

  private State currentState;
  private State nextState;
  private Paint displayColor;
  private final int x;
  private final int y;

  public Cell(int x,int y){
    currentState = null;
    nextState = null;
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

  public Paint getDisplayColor() {
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

  public void setDisplayColor(Paint displayColor) {
    this.displayColor = displayColor;
  }
}
