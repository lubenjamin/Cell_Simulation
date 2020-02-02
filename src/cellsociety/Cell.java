package cellsociety;

import javafx.scene.paint.Paint;




public class Cell {
  private Object currentState;
  private Object nextState;
  private Paint displayColor;
  public Cell(){
    currentState = "";
    nextState = "";

  }

  public Object getNextState() {
    return nextState;
  }

  public Paint getDisplayColor() {
    return displayColor;
  }

  public Object getCurrentState(){
    return currentState;
  }

  public void setCurrentState(Object currentState) {
    this.currentState = currentState;
  }

  public void setNextState(Object nextState) {
    this.nextState = nextState;
  }

  public void setDisplayColor(Paint displayColor) {
    this.displayColor = displayColor;
  }
}
