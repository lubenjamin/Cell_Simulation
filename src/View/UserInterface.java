package View;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserInterface {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 600;

    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
    private static final String STYLESHEET = "resources/default.css";

    private ResourceBundle myResources;

    private Button mySegButton = new Button();
    private Button myPercButton = new Button();
    private Button myLifeButton = new Button();
    private Button myFireButton = new Button();
    private Button myPredButton = new Button();

    private ComboBox<String> myDropDown = new ComboBox();

    private ArrayList<String> mySims;
    private Stage firstSim = new Stage();
    private ControlPanel myControlPanel;

    public UserInterface(Stage stage, String language, ArrayList<String> simNames, Timeline animation) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.mySims = simNames;
        myControlPanel = new ControlPanel(myResources, animation);
        stage.setTitle(myResources.getString("SIMTITLE"));
    }
    public Scene setupUI(Group viewGroup) {
        BorderPane bp = new BorderPane();
        initFirstSim();
        initSimSelect(event -> setSim(), mySims);
        bp.setTop(myDropDown);
        bp.setCenter(viewGroup);
        bp.setBottom(myControlPanel);
        Scene myScene = new Scene(bp, WIDTH, HEIGHT);
        myScene.getStylesheets().add(STYLESHEET);
        return myScene;
    }
    private void initFirstSim() {
        firstSim.setTitle(myResources.getString("CHOICETITLE"));
       HBox v = new HBox();
        mySegButton = makeButton("SIM1", event -> pickSim(mySegButton));
        myPercButton = makeButton("SIM2", event -> pickSim(myPercButton));
        myLifeButton = makeButton("SIM3", event -> pickSim(myLifeButton));
        myFireButton = makeButton("SIM4", event -> pickSim(myFireButton));
        myPredButton = makeButton("SIM5", event -> pickSim(myPredButton));
        v.getChildren().add(mySegButton);
        v.getChildren().add(myPercButton);
        v.getChildren().add(myLifeButton);
        v.getChildren().add(myFireButton);
        v.getChildren().add(myPredButton);
        Scene s = new Scene(v);
        s.getStylesheets().add(STYLESHEET);
        firstSim.setScene(s);
        firstSim.showAndWait();
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
    private void pickSim(Button b) {
        String ret = b.getText();
//        if (ret.equals("PredatorPrey")) {
//            myControlPanel.addPredPreyGraph();
//        }
        myDropDown.setValue(ret);
        setSim();
        firstSim.close();
    }
    private void initSimSelect(EventHandler<ActionEvent> handler, ArrayList<String> simNames) {
        myDropDown.setOnAction(handler);
        for (String s : simNames) {
            myDropDown.getItems().add(s);
        }
    }
    public String setSim() {
        String st = myDropDown.getValue();
//        if (st == "PredatorPrey") {
//            myControlPanel.addPredPreyGraph();
//        }
        myControlPanel.isSimLoaded=true;
        return st;
    }
    public boolean getPauseStatus() {
        return myControlPanel.isPaused;
    }
    public boolean getLoadStatus() {
        return myControlPanel.isSimLoaded;
    }
    public boolean getResetStatus() {
        return myControlPanel.isReset;
    }
    public boolean getStepStatus() {
        return myControlPanel.isStep;
    }
    public void setControlReset(boolean bool) {
        myControlPanel.isReset = bool;
    }
    public void setControlPause(boolean bool) {
        myControlPanel.isPaused = bool;
    }
    public void setControlStep(boolean bool) {
        myControlPanel.isStep = bool;
    }


}
