package cellsociety;

import java.util.ArrayList;

public class Model {

  private Cell[][] grid;
  private ArrayList<Cell> neighborhood = new ArrayList<Cell>();

  public Model(int widthCells, int heightCells){
    grid = new Cell[heightCells][widthCells];
  }

  public Cell getCell(int x, int y){
    return grid[x][y];
  }

  public ArrayList<Cell> getNeighborhood(int x, int y){
    neighborhood.add(grid[x][y + 1]);
    neighborhood.add(grid[x][y - 1]);
    neighborhood.add(grid[x + 1][y]);
    neighborhood.add(grid[x - 1][y]);
    return neighborhood;
  }
}
