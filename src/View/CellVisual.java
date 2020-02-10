package View;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class CellVisual extends Polygon {

  final int myXPos;
  final int myYPos;
  final Paint myColor;
  private final static double r = 4; // the inner radius from hexagon center to outer corner
  private final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
  private  double width;
  private double height;

  public CellVisual(double width, double height, Paint color, int x, int y) {
    this.myXPos = x;
    this.myYPos = y;
    this.myColor = color;
    this.setFill(color);
    this.width = 2*n;
    this.height = 2*r;
  }

  public int getXPos() {
    return myXPos;
  }

  public int getYPos() {
    return myYPos;
  }

  public void setHexShape(double x, double y) {
      getPoints().addAll(
              x, y,
              x, y + r,
              x + n, y + r * 1.5,
              x + width, y + r,
              x + width, y,
              x + n, y - r * 0.5
      );
  }
  public void setSquareShape(double x, double y) {
    getPoints().addAll(x, y);
  }
}
