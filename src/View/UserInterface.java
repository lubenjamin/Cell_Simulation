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

  /**
   * create a UI object that houses all animation function and simulation loading functionality
   * @param stage
   * @param language
   * @param simNames
   * @param panel
   */
  public UserInterface(Stage stage, String language, ArrayList<String> simNames,
      ControlPanel panel) {
    this.mySims = simNames;
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    this.myControlPanel = panel;

    stage.setTitle(myResources.getString("SIMTITLE"));
    myDisplay = new UserSelectDisplay(myResources, myControlPanel, mySims);
  }

  /**
   * initialize main visuals of UI including hbox and vbox housing buttons, sliders,
   * and a spot for sim specific ui
   * @param viewGroup
   * @param simGroup
   * @return
   */
  public Scene setupUI(Group viewGroup, Group simGroup) {
    hb2.setAlignment(Pos.CENTER);
    hb.setSpacing(450);
    hb.getChildren().add(myDisplay.getDropDown());
    hb.getChildren().add(myDisplay.getSimAddButton());
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

  /**
   * @return the sim chosen by user through the dropdownbox
   */
  public String getSim() {
    return myDisplay.setSim();
  }

  /**
   * add a predpreygraph object to the hbox group to be visualized
   * @return a physical graph object to be used in simulator
   */
  public PredPreyGraph addPredChart() {
    myGraph = new PredPreyGraph();
    hb2.getChildren().add(myGraph);
    return myGraph;
  }

  /**
   * remove predpreygraph object from the UI hbox
   */
  public void removeGraph() {
    hb2.getChildren().remove(myGraph);
  }


}
