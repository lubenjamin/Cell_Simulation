package View;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class CellVisual extends Rectangle {

  final int myXPos;
  final int myYPos;
  final Paint myColor;

  public CellVisual(double width, double height, Paint color, int x, int y) {
    this.myXPos = x;
    this.myYPos = y;
    this.myColor = color;
    this.setWidth(width);
    this.setHeight(height);
    this.setFill(color);
  }

  public int getXPos() {
    return myXPos;
  }

  public int getYPos() {
    return myYPos;
  }
}
