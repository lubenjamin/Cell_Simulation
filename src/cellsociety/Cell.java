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

}
