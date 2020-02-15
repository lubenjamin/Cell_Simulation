package View;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class CellVisual extends Rectangle {

  final int myXPos;
  final int myYPos;
  final Paint myColor;

  /** create a visualization of a cell object held in model that corresponds to cell state
   *
   * @param width
   * @param height
   * @param color
   * @param x - i index of model
   * @param y - j index of model
   */
  public CellVisual(double width, double height, Paint color, int x, int y) {
    this.myXPos = x;
    this.myYPos = y;
    this.myColor = color;
    this.setWidth(width);
    this.setHeight(height);
    this.setFill(color);
  }

  /**
   * @return i index of this visual corresponding to model
   */
  public int getXPos() {
    return myXPos;
  }

  /**
   * @return j index of this visual corresponding to model
   */
  public int getYPos() {
    return myYPos;
  }

}
