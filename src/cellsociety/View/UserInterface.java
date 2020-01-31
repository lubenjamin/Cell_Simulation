package cellsociety.View;

import cellsociety.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.ResourceBundle;

public class UserInterface {
    private static final String TITLE = "Simulation_Team05";
    private static final double HEIGHT = 300; // temp
    private static final double WIDTH = 300; //temp
    private static final String RESOURCES = "resources";
    private static final String BASE_PACKAGE = "cellsociety";
    public static final String DEFAULT_RESOURCE_PACKAGE = "." + RESOURCES + ".";
    public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
    public static final String STYLESHEET = "default.css";
    public static final String BLANK = " ";
    private ResourceBundle myResources;
    private Scene userScene;
    private Button myPauseButton = new Button("Test1");
    private Button myPlayButton = new Button("Test2");

    public UserInterface(Stage stage, Group viewGroup, String language) {
        stage.setTitle(TITLE);
        myResources = ResourceBundle.getBundle(BASE_PACKAGE + DEFAULT_RESOURCE_PACKAGE + language);
        setupUI(stage, viewGroup);

    }
    private void setupUI(Stage stage, Group viewGroup) {
        userScene = new Scene(initControls(viewGroup), 1000, 600, Color.LIGHTGRAY);
        stage.setScene(userScene);
        stage.show();
    }
    private Pane initControls(Group viewGroup) {
        BorderPane bp = new BorderPane();
        Model m = new Model((int) WIDTH, (int) HEIGHT);
        View view = new View(viewGroup, WIDTH, HEIGHT, m);
        VBox v = new VBox();
        HBox h = new HBox();
        myPlayButton = makeButton("PLAYCOMMAND", event -> stop());
        myPauseButton = makeButton("PAUSECOMMAND", event -> stop());
        v.getChildren().add(myPauseButton);
        v.getChildren().add(myPlayButton);
        h.getChildren().add(makeSlider());
        bp.setBottom(h);
        bp.setLeft(v);
        bp.setCenter(viewGroup);
        return bp;
    }
    private Slider makeSlider() {
        Slider s1 = new Slider();
        s1.setMin(0);
        s1.setMax(100);
        s1.setValue(25);
        s1.setShowTickLabels(true);
        s1.setShowTickMarks(true);
        s1.setMajorTickUnit(50);
        s1.setMinorTickCount(5);
        s1.setBlockIncrement(10);
        return s1;
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
