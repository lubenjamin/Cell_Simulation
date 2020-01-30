package cellsociety;

import cellsociety.View.View;
import javafx.scene.Group;


public class Controller {
  private Model currentModel;
  private View currentView;
  public Controller (Group simGroup){
    currentModel = new Model(100,100);
    currentView = new View(simGroup, 100, 100, currentModel);
  }

  public void updateSim(){
    updateGrid();
    swtichGridStates();
    updateView();
  }
  private void updateGrid(){

  }
  private void swtichGridStates(){

  }
  private void updateView(){

  }


}
