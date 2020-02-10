package View;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import utils.Model;

import java.util.ArrayList;

public class HexView extends View {

    public HexView(Group viewGroup, int widthCells, int heightCells, Model currentModel, double spacing,
                   ArrayList<String> colors) {
        super(viewGroup, widthCells, heightCells, currentModel, spacing, colors);
        initCellView(currentModel, widthCells, heightCells);
    }

    public void initCellView(Model grid, int width, int height) {
        //myVisualWidth = VIEW_BOUND / width;
        //myVisualHeight = VIEW_BOUND / height;
        double r = 4; // the inner radius from hexagon center to outer corner
        double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
        myVisualWidth = 2*n;
        myVisualHeight = 2*r;
        int xStartOffset = 20;
        int yStartOffset = 20;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double xCoord = i * myVisualWidth + (j % 2) * n + xStartOffset;
                double yCoord = j * myVisualHeight *.75 + yStartOffset;
                CellVisual cv = new CellVisual(myVisualWidth, myVisualHeight,
                        super.displayState(grid.getCell(i, j)),i, j);
                cv.setHexShape(xCoord, yCoord);
                myVisuals.add(cv);
                EventHandler<MouseEvent> eventHandler = mouseEvent -> handleCellClick(
                        grid.getCell(cv.getXPos(), cv.getYPos()), cv);
                cv.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                myViewGroup.getChildren().add(cv);
            }
        }

    }
}
