package View;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * this class depicts good use of modularity as it divides up the UI classes job of adding and loading sims into
 * a separate class similar to ControlPanel
 */
public class UserSelectDisplay extends Stage {

  private static final String STYLESHEET = "resources/default.css";

  private static final String RESOURCES = "resources";

  private final ResourceBundle myResources;
  private final ComboBox<String> myDropDown;
  private final ControlPanel myControlPanel;
  private final ArrayList<String> mySims;
  private Button mySimAddButton;


  /**
   * create an object specializing in simulation laoding
   * @param resources
   * @param controls
   * @param sims
   */
  public UserSelectDisplay(ResourceBundle resources, ControlPanel controls,
      ArrayList<String> sims) {
    this.myResources = resources;
    this.myControlPanel = controls;
    this.mySims = sims;
    myDropDown = new ComboBox<>();
    myDropDown.setValue("Switch Simulation");
    mySimAddButton = controls.makeButton("ADDSIMCOMMAND", event -> addSim());
    initSimSelect(event -> setSim(), event -> addSim(), mySims);
  }

  /**
   * retrieve simulation text chosen by user from dropdown and return the string
   * @return string representing simulation file name
   */
  public String setSim() {
    String st = myDropDown.getValue();
    myControlPanel.setSimLoad(true);
    return st;
  }

  private void addSim() {
    Stage stage = new Stage();
    new Simulator(stage);
  }

  /**
   * @return a populated dropdown box housing all of the viable simulation files
   */
  public Node getDropDown() {
    return myDropDown;
  }

  /**
   * return a button that alllows for addition of new simulations
   * @return
   */
  public Node getSimAddButton() {
    return mySimAddButton;
  }

  private void initSimSelect(EventHandler<ActionEvent> handler, EventHandler<ActionEvent> handler2,
      ArrayList<String> simNames) {
    myDropDown.setOnAction(handler);
    for (String s : simNames) {
      myDropDown.getItems().add(s);
    }
  }

}
