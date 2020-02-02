package cellsociety;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Model {

  private Cell[][] grid;
  private ArrayList<Cell> neighborhood = new ArrayList<Cell>();

  public Model(int widthCells, int heightCells){
    grid = new Cell[heightCells][widthCells];
    initializeGrid();
  }

  private void initializeGrid() {
    for(int x = 0; x < grid.length; x++){
      for(int y = 0; y<grid[0].length; y++){
        grid [x][y] = new Cell();
      }
    }
  }

  public Cell getCell(int x, int y){
    return grid[x][y];
  }

  public ArrayList<Cell> getNeighborhood(int x, int y) {
    if (x >= 1 && y >= 1 && x <= 73 && y <= 73) {
      neighborhood.add(grid[x][y + 1]);
      neighborhood.add(grid[x][y - 1]);
      neighborhood.add(grid[x + 1][y]);
      neighborhood.add(grid[x - 1][y]);
    }
      return neighborhood;
  }
}
