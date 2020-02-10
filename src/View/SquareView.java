package View;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import utils.Model;

import java.util.ArrayList;

public class SquareView extends View {
    public SquareView(Group viewGroup, int widthCells, int heightCells, Model currentModel, double spacing,
                      ArrayList<String> colors) {
        super(viewGroup, widthCells, heightCells, currentModel, spacing, colors);
        initCellView(currentModel, widthCells, heightCells);
    }

    private void initCellView(Model grid, int width, int height) {
        myVisualWidth = VIEW_BOUND / width /2;
        myVisualHeight = VIEW_BOUND / height /2;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                CellVisual cv = new CellVisual(myVisualWidth, myVisualHeight,
                        displayState(grid.getCell(i, j)), i, j);
                cv.setSquareShape(i * (myVisualWidth*2 + spacing/2), j * (myVisualHeight*2 + spacing/2), myVisualHeight, myVisualWidth);
                myVisuals.add(cv);
                EventHandler<MouseEvent> eventHandler = mouseEvent -> handleCellClick(
                        grid.getCell(cv.getXPos(), cv.getYPos()), cv);
                cv.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                myViewGroup.getChildren().add(cv);
            }
        }
    }
}
