package View;

import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;

public class ControlPanel extends Group {

  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String STYLESHEET = "resources/default.css";
  private static final String language = "English";

  //private final ResourceBundle myResources;
  private final ResourceBundle myResources;
  private final Timeline myAnimation;
  private final HBox panel;
  private Button myPauseButton = new Button();
  private Button myPlayButton = new Button();
  private Button myResetButton = new Button();
  private Button myStepButton = new Button();
  private Slider mySlider = new Slider();
  private PredPreyGraph myGraph;
  private boolean isPaused;
  private boolean isReset;
  private boolean isStep;
  private boolean isSimLoaded;

  /**
   * create a control panel object that houses basic animation functionality of UI
   * start initially paused
   * @param animation
   */
  public ControlPanel(Timeline animation) {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    this.myAnimation = animation;
    this.isPaused = true;
    this.panel = new HBox();
    getChildren().add(initControlPanel());
  }

  /**
   * create a button object with a label retreived from a .properties file
   * @param property - name of label in .properties file
   * @param handler - corresponds with function of button
   * @return a new button object wiht customized label and function
   */
  public Button makeButton(String property, EventHandler<ActionEvent> handler) {
    // represent all supported image suffixes
    final String IMAGEFILE_SUFFIXES = String
        .format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
    Button result = new Button();
    String label = myResources.getString(property);
    if (label.matches(IMAGEFILE_SUFFIXES)) {
      result.setGraphic(new ImageView(
          new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_FOLDER + label))));
    } else {
      result.setText(label);
    }
    result.setOnAction(handler);
    return result;
  }

  private Node initControlPanel() {
    HBox result = new HBox();
    myPlayButton = makeButton("PLAYCOMMAND", event -> setPlay());
    myPauseButton = makeButton("PAUSECOMMAND", event -> setPause());
    myResetButton = makeButton("RESETCOMMAND", event -> checkReset());
    myStepButton = makeButton("UPDATECOMMAND", event -> checkUpdate());
    mySlider = makeSlider(event -> handleSlider());
    result.getChildren().add(myPlayButton);
    result.getChildren().add(myPauseButton);
    result.getChildren().add(myStepButton);
    result.getChildren().add(myResetButton);
    panel.getChildren().add(result);
    panel.getChildren().add(mySlider);
    return panel;
  }

  private Slider makeSlider(EventHandler<MouseEvent> handler) {
    Slider mySlider = new Slider();
    mySlider.setOnMouseReleased(handler);
    mySlider.setMin(1);
    mySlider.setMax(50);
    mySlider.setValue(15);
    mySlider.setShowTickLabels(true);
    mySlider.setShowTickMarks(true);
    mySlider.setMajorTickUnit(5);
    mySlider.setMinorTickCount(1);
    mySlider.setBlockIncrement(10);
    myAnimation.setRate(mySlider.getValue());
    return mySlider;
  }

  private void handleSlider() {
    myAnimation.setRate(mySlider.getValue());
  }

  /**
   * set the simulation state to pause
   */
  public void setPause() {
    isPaused = true;
  }

  private void setPlay() {
    if (isSimLoaded) {
      isPaused = false;
    }
  }

  private void checkReset() {
    if (isSimLoaded) {
      isReset = true;
      isPaused = true;
    }
  }

  private void checkUpdate() {
    isStep = true;
  }

  /**
   * @return boolean representing whether or not a simulation has been loaded
   */
  public boolean getSimLoadStatus() {
    return isSimLoaded;
  }

  /**
   * @return boolean representing whether or not simulation is paused
   */
  public boolean getPauseStatus() {
    return this.isPaused;
  }

  /**
   * @return boolean representing if a reset command has been issued by user
   */
  public boolean getResetStatus() {
    return this.isReset;
  }

  /**
   * @return boolean representing if a stepping command has been issued by user
   */
  public boolean getUpdateStatus() {
    return this.isStep;
  }

  /**
   * reset controlpanel back to default reset and step states
   */
  public void resetControl() {
    this.isReset = false;
    this.isStep = false;
  }

  /**
   * set control panel with boolean representing whether or not a sim has been loaded
   * @param b
   */
  public void setSimLoad(boolean b) {
    this.isSimLoaded = b;
  }
}
