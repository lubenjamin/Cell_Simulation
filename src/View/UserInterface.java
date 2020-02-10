package View;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UserInterface {

  private static final int HEIGHT = 600;
  private static final int WIDTH = 850;

  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  private static final String STYLESHEET = "resources/default.css";

  private final ResourceBundle myResources;

  private final ArrayList<String> mySims;
  private final ControlPanel myControlPanel;
  private final UserSelectDisplay myDisplay;
  private HBox hb = new HBox();
  private HBox hb2 = new HBox();
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

  public Scene setupUI(Group viewGroup, Group simGroup) {
    hb2.setAlignment(Pos.CENTER);
    hb.setSpacing(450);
    hb.getChildren().add(myDisplay.getDropDown());
    hb.getChildren().add(myDisplay.getDropDown2());
    bp.setTop(hb);
    bp.setRight(hb2);
    bp.setLeft(simGroup);
    bp.setCenter(viewGroup);
    bp.setBottom(myControlPanel);
    BorderPane.setAlignment(simGroup, Pos.CENTER);
    BorderPane.setMargin(simGroup, new Insets(12, 12, 12, 12));
    BorderPane.setAlignment(myControlPanel, Pos.CENTER);
    BorderPane.setMargin(myControlPanel, new Insets(12, 12, 12, 12));
    Scene myScene = new Scene(bp, WIDTH, HEIGHT);
    myScene.getStylesheets().add(STYLESHEET);
    return myScene;
  }
  public String getSim() {
    return myDisplay.setSim();
  }

  public PredPreyGraph addPredChart() {
    myGraph = new PredPreyGraph();
    hb2.getChildren().add(myGraph);
    return myGraph;
  }

  public void removeGraph() {
    hb2.getChildren().remove(myGraph);
  }


}
