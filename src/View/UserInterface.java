package View;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UserInterface {

  private static final int HEIGHT = 600;
  private static final int WIDTH = 600;

  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  private static final String STYLESHEET = "resources/default.css";

  private final ResourceBundle myResources;

  private final ArrayList<String> mySims;
  private final ControlPanel myControlPanel;
  private final UserSelectDisplay myDisplay;
  private HBox hb = new HBox();
  private PredPreyGraph myGraph;
  private BorderPane bp = new BorderPane();

  public UserInterface(Stage stage, String language, ArrayList<String> simNames, ControlPanel panel,
      boolean isFirstSimulation) {
    this.mySims = simNames;
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    this.myControlPanel = panel;

    stage.setTitle(myResources.getString("SIMTITLE"));
    myDisplay = new UserSelectDisplay(myResources, myControlPanel, mySims, isFirstSimulation);
  }
<<<<<<< HEAD
  public Scene setupUI(Group viewGroup) {
=======

  public Scene setupUI(Group viewGroup, Group simGroup) {
    BorderPane bp = new BorderPane();
>>>>>>> 24e7fa1f0bde3c6cc594e9dd9e8ea526a98c6a0e
    hb.getChildren().add(myDisplay.getDropDown());
    hb.getChildren().add(myDisplay.getDropDown2());
    bp.setTop(hb);
    bp.setLeft(simGroup);
    bp.setCenter(viewGroup);
    bp.setBottom(myControlPanel);

    Scene myScene = new Scene(bp, WIDTH, HEIGHT);
    myScene.getStylesheets().add(STYLESHEET);
    return myScene;
  }
  public String getSim() {
    return myDisplay.setSim();
  }

  public PredPreyGraph addPredChart() {
    myGraph = new PredPreyGraph();
    hb.getChildren().add(myGraph);
    return myGraph;
  }

  public void removeGraph() {
    hb.getChildren().remove(myGraph);
  }


}
