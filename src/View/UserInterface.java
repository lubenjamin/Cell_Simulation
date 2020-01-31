package View;

import cellsociety.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.ResourceBundle;

public class UserInterface {
    private static final String TITLE = "Simulation_Team05";
    private static final int HEIGHT = 300; // temp
    private static final int WIDTH = 300; //temp
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
    public static final String STYLESHEET = "resources/default.css";
    public static final String BLANK = " ";
    private ResourceBundle myResources;
    private Button myPauseButton = new Button();
    private Button myPlayButton = new Button();
    private Button myResetButton = new Button();
    private Button myStepButton = new Button();

    public UserInterface(Stage stage, String language) {
        stage.setTitle(TITLE);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    }
    public Scene setupUI(Group viewGroup) {
        BorderPane bp = new BorderPane();
        bp.setCenter(viewGroup);
        bp.setBottom(initControls());
        Scene myScene = new Scene(bp, 600, 600);
        myScene.getStylesheets().add(STYLESHEET);
        return myScene;
    }
    private Node initControls() {
        VBox v = new VBox();
        HBox h = new HBox();
        myPlayButton = makeButton("PLAYCOMMAND", event -> stop());
        myPauseButton = makeButton("PAUSECOMMAND", event -> stop());
        myResetButton = makeButton("RESETCOMMAND", event -> stop());
        myStepButton = makeButton("UPDATECOMMAND", event -> stop());
        v.getChildren().add(myPlayButton);
        v.getChildren().add(myPauseButton);
        v.getChildren().add(myStepButton);
        v.getChildren().add(myResetButton);
        h.getChildren().add(v);
        h.getChildren().add(makeSlider());
        return h;
    }
    private Slider makeSlider() {
        Slider mySlider = new Slider();
        mySlider.setMin(0);
        mySlider.setMax(100);
        mySlider.setValue(25);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMajorTickUnit(50);
        mySlider.setMinorTickCount(5);
        mySlider.setBlockIncrement(10);
        return mySlider;
    }
    private Button makeButton (String property, EventHandler<ActionEvent> handler) {
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
    private void stop() {
    }
}
