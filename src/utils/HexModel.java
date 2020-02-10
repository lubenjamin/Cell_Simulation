package utils;

import java.util.ArrayList;
import java.util.List;

public class HexModel extends Model{

    public HexModel(int widthCells, int heightCells, int maxState) {
        super(widthCells,heightCells,maxState);
    }




    public List<Cell> getSimpleNeighborhood(int x, int y) {
        ArrayList<Cell> ret = new ArrayList<>();
        if(y % 2 == 0) {
            addIfExists(ret, x, y - 1);
            addIfExists(ret, x + 1, y - 1);
            addIfExists(ret, x - 1, y);
            addIfExists(ret, x + 1, y);
            addIfExists(ret, x, y + 1);
            addIfExists(ret, x + 1, y + 1);
        } else {
            addIfExists(ret, x-1, y-1);
            addIfExists(ret, x, y-1);
            addIfExists(ret, x-1, y);
            addIfExists(ret, x + 1, y);
            addIfExists(ret, x - 1, y + 1);
            addIfExists(ret, x, y + 1);
        }
        return ret;
    }

    @Override
    public List<Cell> getMooreNeighborhood(int x, int y) {
        return getSimpleNeighborhood(x,y);
    }

    @Override
    public List<Cell> getTorusNeighborhood(int x, int y) {
        return null;
    }


}
