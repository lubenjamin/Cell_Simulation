package View;

import controllerPackage.Controller;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ResourceBundle;

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

    public UserInterface(Stage stage, String language, ArrayList<String> simNames, ControlPanel panel, boolean b) {
        this.mySims = simNames;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.myControlPanel = panel;
        myDisplay = new UserSelectDisplay(myResources, myControlPanel, mySims, b);
        stage.setTitle(myResources.getString("SIMTITLE"));
    }
    public Scene setupUI(Group viewGroup) {
        BorderPane bp = new BorderPane();
        hb.getChildren().add(myDisplay.getDropDown());
        hb.getChildren().add(myDisplay.getDropDown2());
        bp.setTop(hb);
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
        myGraph = myControlPanel.addPredPreyGraph();
        hb.getChildren().add(myGraph);
        return myGraph;
    }
    public void removeGraph() {
        hb.getChildren().remove(myGraph);
    }



}
