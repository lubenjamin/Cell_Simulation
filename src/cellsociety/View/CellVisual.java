package cellsociety.View;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class CellVisual extends Rectangle {
    double myXPos;
    double myYPos;
    Paint myColor;

    public CellVisual(double width, double height, Paint color, double x, double y) {
        this.myXPos = x;
        this.myYPos = y;
        this.myColor = color;
        this.setWidth(width);
        this.setHeight(height);
        this.setFill(color);
    }
    public double getXPos() {
        return myXPos;
    }
    public double getYPos() {
        return myYPos;
    }
}
