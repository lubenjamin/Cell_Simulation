package View;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserSelectDisplay extends Stage {

  private static final String STYLESHEET = "resources/default.css";

//    private Button mySegButton = new Button();
//    private Button myPercButton = new Button();
//    private Button myLifeButton = new Button();
//    private Button myFireButton = new Button();
//    private Button myPredButton = new Button();

  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";


  private final ResourceBundle myResources;
  private final ComboBox<String> myDropDown;
  //private final ComboBox<String> myDropDown2;
  private final ControlPanel myControlPanel;
  private final ArrayList<String> mySims;
  private Button mySimAddButton = new Button();
  private File myFirstSim;

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
  public ComboBox<String> getDropDown() {
    return myDropDown;
  }

  /**
   * return a button that alllows for addition of new simulations
   * @return
   */
  public Button getSimAddButton() {
    return mySimAddButton;
  }

  private void initSimSelect(EventHandler<ActionEvent> handler, EventHandler<ActionEvent> handler2,
      ArrayList<String> simNames) {
    myDropDown.setOnAction(handler);
    //myDropDown2.setOnAction(handler2);
    for (String s : simNames) {
      myDropDown.getItems().add(s);
      // myDropDown2.getItems().add(s);
    }
  }

}
