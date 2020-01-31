package cellsociety;

import javafx.scene.paint.Paint;


public class Cell {
  private String currentState;
  private String nextState;
  private Paint displayColor;
  public Cell(String initialState, Paint initialColor){
    currentState = initialState;
    this.displayColor = initialColor;
  }

  public String getNextState() {
    return nextState;
  }

  public Paint getDisplayColor() {
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

  public void setDisplayColor(Paint displayColor) {
    this.displayColor = displayColor;
  }
}
