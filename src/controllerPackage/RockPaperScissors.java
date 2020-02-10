package controllerPackage;

import controllerPackage.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Group;
import utils.Cell;
import utils.FileReader;

public class RockPaperScissors extends Controller {

  private int numberOfTypes;
  private int threshold;
  private HashMap<Integer, ArrayList<Integer>> loseTo;

  public RockPaperScissors(Group simGroup, FileReader reader, Group simUIGroup) {
    super(simGroup, reader, simUIGroup);
    loseTo = new HashMap<>();
    createLoseToMap();
  }

  private void checkCount() {
    int x0 = 0;
    int x1 = 0;
    int x2 = 0;
    for (int i = 0; i < WIDTH_CELLS * HEIGHT_CELLS; i++) {
      int x = i % WIDTH_CELLS;
      int y = i / WIDTH_CELLS;
      Cell cell = currentModel.getCell(x,y);
      if(cell.getCurrentState().getState()==0){
        x0 ++;
      }
      if(cell.getCurrentState().getState()==1){
        x1 ++;
      }
      if(cell.getCurrentState().getState()==2){
        x2 ++;
      }
    }
    System.out.println(x0+" "+x1+" " + x2);
  }


  @Override
  protected HashMap<String, Object> getSimParamsForUi() {

    HashMap<String, Object> ret = new HashMap<>();
    ret.put("numberOfTypes", numberOfTypes);
    ret.put("threshold", threshold);
    return ret;
  }

  @Override
  protected void setSimParamsFromUI() {
    HashMap<String, Object> values = (HashMap<String, Object>) simUI.getValues();
    numberOfTypes = (int) values.get("numberOfTypes");
    threshold = (int) values.get("threshold");
    maxState = numberOfTypes-1;
    createLoseToMap();
  }


  @Override
  protected void setSimParams() {
    numberOfTypes = reader.getIntValue("numberOfTypes");
    threshold = reader.getIntValue("threshold");
    spacing = reader.getDoubleValue("spacing");
    maxState = numberOfTypes-1;
  }

  @Override
  protected void initializeCellState(Cell cell) {
    int type = (int) (random.nextDouble()*numberOfTypes);
    cell.setCurrentState(new State(type));
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell cell = currentModel.getCell(x,y);
    int nextType = getNextType(cell);
    cell.setNextState(new State(nextType));
  }

  private int getNextType(Cell cell) {
    ArrayList<Cell> neigh = (ArrayList<Cell>) currentModel.getMooreNeighborhood(cell.getX(), cell.getY());
    ArrayList<Integer> losing = loseTo.get(cell.getCurrentState().getState());
    int[] maxLoseTo = new int[loseTo.size()];
    int maxIndex = 0;
    int maxVal = 0;
    for(Cell x : neigh){
      int index = losing.indexOf(x.getCurrentState().getState());
      if(index>=0){
        maxLoseTo[index]  = maxLoseTo[index] + 1;
        if(maxLoseTo[index]>maxVal){
          maxVal = maxLoseTo[index];
          maxIndex = index;
        }
      }
    }
    if(maxVal>threshold){
      return losing.get(maxIndex);
    }
    return cell.getCurrentState().getState();
  }

  private void createLoseToMap() {
    ArrayList<Integer> current = new ArrayList<>();
    int numLoseTo = numberOfTypes/2;
    int currentLastNumber = numLoseTo;
    for(int x = 0; x<numLoseTo; x++){
      current.add(x+1);
    }
    for(int x = 0; x<numberOfTypes; x++){
      loseTo.put(x, new ArrayList<>(current));
      current.remove(0);
      currentLastNumber++;
      if(currentLastNumber>maxState){
        currentLastNumber=0;
      }
      current.add(currentLastNumber);
    }
  }
}