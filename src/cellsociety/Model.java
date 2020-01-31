package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Model {
    public Cell[][] myGrid = new Cell[300][300];
  public Model(double widthCells, double heightCells){
        for (int i = 0; i < widthCells-1; i ++) {
            for (int j = 0; j < heightCells-1; j++) {
                myGrid[i][j] = new Cell("Test", Color.RED);
            }
        }
  }
//
  public Cell getCell(double x, double y){
      return myGrid[(int) x][(int) y];
  }
//
//  public ArrayList<Cell> getNeighborhood(int x, int y){
//
//  }
}
