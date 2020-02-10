package View;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
  private final ComboBox<String> myDropDown2;


  private final ControlPanel myControlPanel;
  private final ArrayList<String> mySims;
  private File myFirstSim;

  private boolean state;

  public UserSelectDisplay(ResourceBundle resources, ControlPanel controls, ArrayList<String> sims,
      boolean b) {
    this.myResources = resources;
    this.myControlPanel = controls;
    this.mySims = sims;
    this.state = b;
    myDropDown = new ComboBox<>();
    myDropDown.setValue("Switch Simulation");
    myDropDown2 = new ComboBox<>();
    myDropDown2.setValue("Add Simulation");
    if (!b) {
      initUserSelectDisplay();
    }
    initSimSelect(event -> setSim(), event -> addSim(), mySims);
  }

  public String setSim() {
    String st = myDropDown.getValue();
    myControlPanel.setSimLoad(true);
    return st;
  }

  public String addSim() {
    String st = myDropDown2.getValue();
    if (!st.equals("Add Simulation")) {
      Stage stage = new Stage();
      Simulator s = new Simulator(stage, true, st);
    }
    return st;
  }

  public ComboBox<String> getDropDown() {
    return myDropDown;
  }

  public ComboBox<String> getDropDown2() {
    return myDropDown2;
  }

  private void initUserSelectDisplay() {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Load a new XML file");
    chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    myFirstSim = chooser.showOpenDialog(this);
    String ret = myFirstSim.getName().split(".xml")[0];
    myDropDown.setValue(ret);
    setSim();
  }
//    private void initUserSelectDisplay() {
//            setTitle(myResources.getString("CHOICETITLE"));
//            HBox v = new HBox();
//            mySegButton = myControlPanel.makeButton("SIM1", event -> pickSim(mySegButton));
//            myPercButton = myControlPanel.makeButton("SIM2", event -> pickSim(myPercButton));
//            myLifeButton = myControlPanel.makeButton("SIM3", event -> pickSim(myLifeButton));
//            myFireButton = myControlPanel.makeButton("SIM4", event -> pickSim(myFireButton));
//            myPredButton = myControlPanel.makeButton("SIM5", event -> pickSim(myPredButton));
//            v.getChildren().add(mySegButton);
//            v.getChildren().add(myPercButton);
//            v.getChildren().add(myLifeButton);
//            v.getChildren().add(myFireButton);
//            v.getChildren().add(myPredButton);
//            Scene s = new Scene(v);
//            s.getStylesheets().add(STYLESHEET);
//            setScene(s);
//            showAndWait();
//    }

  //    private String pickSim(Button b) {
//        String ret = b.getText();
//        myDropDown.setValue(ret);
//        setSim();
//        close();
//        return ret;
//    }
  private void initSimSelect(EventHandler<ActionEvent> handler, EventHandler<ActionEvent> handler2,
      ArrayList<String> simNames) {
    myDropDown.setOnAction(handler);
    myDropDown2.setOnAction(handler2);
    for (String s : simNames) {
      myDropDown.getItems().add(s);
      myDropDown2.getItems().add(s);
    }
  }

}
