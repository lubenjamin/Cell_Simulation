package View;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
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

    private ResourceBundle myResources;

    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";

    private Timeline myAnimation;
    private PredPreyGraph myGraph;
    private HBox panel;

    public boolean isPaused;
    public boolean isReset;
    public boolean isStep;
    public boolean isSimLoaded;

    public ControlPanel(ResourceBundle resources, Timeline animation) {
        this.myResources = resources;
        this.myAnimation = animation;
        this.isPaused=true;
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
//    public void addPredPreyGraph() {
//        myGraph = new PredPreyGraph();
//        panel.setSpacing(50);
//        panel.getChildren().add(myGraph);
//    }
    private Node initControlPanel() {
        VBox vb = new VBox();
        myPlayButton = makeButton("PLAYCOMMAND", event -> checkPlay());
        myPauseButton = makeButton("PAUSECOMMAND", event -> checkPause());
        myResetButton = makeButton("RESETCOMMAND", event -> checkReset());
        myStepButton = makeButton("UPDATECOMMAND", event -> checkUpdate());
        mySlider = makeSlider(event -> handleSlider());
        vb.getChildren().add(myPlayButton);
        vb.getChildren().add(myPauseButton);
        vb.getChildren().add(myStepButton);
        vb.getChildren().add(myResetButton);
        panel.getChildren().add(vb);
        panel.getChildren().add(mySlider);
        return panel;
    }
    private Slider makeSlider(EventHandler<MouseEvent> handler) {
        Slider mySlider = new Slider();
        mySlider.setOnMouseReleased(handler);
        mySlider.setMin(0);
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
    private void checkPause() {
        this.isPaused = true;
    }
    private void checkPlay() {
        if (isSimLoaded) {
            this.isPaused = false;
        }
    }
    private void checkReset() {
        if (isSimLoaded) {
            this.isReset = true;
            this.isPaused = true;
        }
    }
    private void checkUpdate() { this.isStep = true;
    }
}
