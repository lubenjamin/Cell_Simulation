package View;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.util.ResourceBundle;

public class ControlPanel extends Group {

    private Button myPauseButton = new Button();
    private Button myPlayButton = new Button();
    private Button myResetButton = new Button();
    private Button myStepButton = new Button();
    private Slider mySlider = new Slider();

    //private final ResourceBundle myResources;

    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";

    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

    private static final String STYLESHEET = "resources/default.css";
    private static final String language = "English";

    private final ResourceBundle myResources;

    private final Timeline myAnimation;
    private PredPreyGraph myGraph;
    private final HBox panel;

    private boolean isPaused;
    private boolean isReset;
    private boolean isStep;
    private boolean isSimLoaded;

    public ControlPanel(Timeline animation) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.myAnimation = animation;
        this.isPaused = true;
        this.panel = new HBox();
        getChildren().add(initControlPanel());
    }
    public Button makeButton (String property, EventHandler<ActionEvent> handler) {
        // represent all supported image suffixes
        final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
        Button result = new Button();
        String label = myResources.getString(property);
        if (label.matches(IMAGEFILE_SUFFIXES)) {
            result.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_FOLDER + label))));
        }
        else {
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
    public boolean getSimLoadStatus() {
        return isSimLoaded;
    }
    public boolean getPauseStatus() {
        return this.isPaused;
    }
    public boolean getResetStatus() {
        return this.isReset;
    }
    public boolean getUpdateStatus() {
        return this.isStep;
    }
    public void resetControl() {
        this.isReset = false;
        this.isStep = false;
    }
    public void setSimLoad(boolean b) { this.isSimLoaded = b; }
}
