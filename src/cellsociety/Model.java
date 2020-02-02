package cellsociety;


import java.util.ArrayList;


public class Model {

  private Cell[][] grid;

  public Model(int widthCells, int heightCells){
    grid = new Cell[heightCells][widthCells];
    initializeGrid();
  }

  private void initializeGrid() {
    for(int x = 0; x < grid.length; x++){
      for(int y = 0; y<grid[0].length; y++){
        grid[x][y] = new Cell();
      }
    }
  }

  public Cell getCell(int x, int y){
    return grid[y][x];
  }

  public ArrayList<Cell> getSimpleNeighborhood(int x, int y){
    ArrayList<Cell> ret = new ArrayList<>();


    addIfExists(ret, x+0, y+1);


    addIfExists(ret, x-1, y+0);
    addIfExists(ret, x+1, y+0);


    addIfExists(ret, x+0, y-1);


    return ret;
  }


  public ArrayList<Cell> getMooreNeighborhood(int x, int y){
    ArrayList<Cell> ret = new ArrayList<>();

    addIfExists(ret, x-1, y+1);
    addIfExists(ret, x+0, y+1);
    addIfExists(ret, x+1, y+1);

    addIfExists(ret, x-1, y+0);
    addIfExists(ret, x+1, y+0);

    addIfExists(ret, x-1, y-1);
    addIfExists(ret, x+0, y-1);
    addIfExists(ret, x+1, y-1);

    return ret;
  }

  private void addIfExists(ArrayList<Cell> ret, int x, int y){
    if(x<grid[0].length && x>=0 && y>=0 && y<grid.length){
      ret.add(grid[y][x]);
    }
  }
}
