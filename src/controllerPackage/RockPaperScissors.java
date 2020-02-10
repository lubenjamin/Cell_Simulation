package controllerPackage;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Group;
import utils.Cell;
import utils.FileReader;

public class RockPaperScissors extends Controller {

  private int numberOfTypes;
  private int threshold;
  private int randomChanceThresh;
  private HashMap<Integer, ArrayList<Integer>> loseTo;

  public RockPaperScissors(Group simGroup, FileReader reader, Group simUIGroup) {
    super(simGroup, reader, simUIGroup);
    loseTo = new HashMap<>();
    createLoseToMap();
  }


  @Override
  protected HashMap<String, Object> getSimParamsForUi() {
    HashMap<String, Object> ret = new HashMap<>();
    ret.put("numberOfTypes", numberOfTypes);
    ret.put("threshold", threshold);
    ret.put("randomChanceThresh", randomChanceThresh);
    return ret;
  }

  @Override
  protected void setSimParamsFromUI() {
    HashMap<String, Object> values = (HashMap<String, Object>) simUI.getValues();
    numberOfTypes = (int) values.get("numberOfTypes");
    threshold = (int) values.get("threshold");
    randomChanceThresh = (int) values.get("randomChanceThresh");
    maxState = numberOfTypes - 1;
    createLoseToMap();
  }
  @Override
  protected void setSimParams() {
    numberOfTypes = reader.getIntValue("numberOfTypes");
    threshold = reader.getIntValue("threshold");
    randomChanceThresh = reader.getIntValue("randomChanceThresh");
    spacing = reader.getDoubleValue("spacing");
    maxState = numberOfTypes - 1;
  }

  @Override
  protected void initializeCellState(Cell cell) {
    int type = (int) (random.nextDouble() * numberOfTypes);
    cell.setCurrentState(new State(whichPie(cell.getX(), cell.getY())));
  }

  @Override
  protected void updateCell(int x, int y) {
    Cell cell = currentModel.getCell(x, y);
    int nextType = getNextType(cell);
    cell.setNextState(new State(nextType));
  }

  private int getNextType(Cell cell) {
    int[] countOfLosingNeighbors = getCountOfNeighbors(cell);
    int maxVal = 0;
    ArrayList<Integer> possibleIndex = new ArrayList<>();
    for(int x =0; x<countOfLosingNeighbors.length; x++){
      if (countOfLosingNeighbors[x] > maxVal) {
        possibleIndex.clear();
        possibleIndex.add(x);
        maxVal = countOfLosingNeighbors[x];
      }
      if(countOfLosingNeighbors[x]==maxVal){
        possibleIndex.add(x);
      }
    }
    int thresh = threshold;
    if(randomChanceThresh>0){
      thresh = threshold + random.nextInt(randomChanceThresh);
    }
    if (maxVal > thresh) {
      return possibleIndex.get(random.nextInt(possibleIndex.size()));
    }
    return cell.getCurrentState().getState();
  }

  private int[] getCountOfNeighbors(Cell cell) {
    ArrayList<Cell> neigh = (ArrayList<Cell>) currentModel.getMooreNeighborhood(cell.getX(), cell.getY());
    ArrayList<Integer> statesLoseList = loseTo.get(cell.getCurrentState().getState());
    int[] ret = new int[numberOfTypes];

    for (Cell x : neigh) {
      int index = statesLoseList.indexOf(x.getCurrentState().getState());
      if (index >= 0) {
        ret[x.getCurrentState().getState()] = ret[x.getCurrentState().getState()] + 1;
      }
    }

    return ret;
  }

  private void createLoseToMap() {
    ArrayList<Integer> current = new ArrayList<>();
    int numLoseTo = numberOfTypes / 2;
    int currentLastNumber = numLoseTo;
    for (int x = 0; x < numLoseTo; x++) {
      current.add(x + 1);
    }
    for (int x = 0; x < numberOfTypes; x++) {
      loseTo.put(x, new ArrayList<>(current));
      current.remove(0);
      currentLastNumber++;
      if (currentLastNumber > maxState) {
        currentLastNumber = 0;
      }
      current.add(currentLastNumber);
    }
  }

  private int whichPie(int x, int y){

    double xC = x-WIDTH_CELLS/2.0;
    double yC = HEIGHT_CELLS/2.0-y;
    double length = Math.sqrt(yC*yC + xC*xC);
    double angle;
    if(xC == 0){
      xC = 1;
    }
    angle = Math.acos( xC/length);
    if(yC<0){
      angle = -1* angle;
    }
    if(angle<0){
      angle = angle + Math.PI*2;
    }
    double sections = Math.PI*2.0/numberOfTypes;
    return (int) (angle/sections);

  }
}