package cellsociety;

import java.awt.Color;

public class Cell {
  private String currentState;
  private String nextState;
  private Color displayColor;
  public Cell(String initialState, Color initialColor){
    currentState = initialState;
    displayColor = initialColor;
  }

  public String getNextState() {
    return nextState;
  }

  public Color getDisplayColor() {
    return displayColor;
  }

  public String getCurrentState(){
    return currentState;
  }

  public void setCurrentState(String currentState) {
    this.currentState = currentState;
  }

  public void setNextState(String nextState) {
    this.nextState = nextState;
  }

  public void setDisplayColor(Color displayColor) {
    this.displayColor = displayColor;
  }
}
