package View;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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

    public UserInterface(Stage stage, String language, ArrayList<String> simNames, Timeline animation) {
        this.mySims = simNames;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        myControlPanel = new ControlPanel(myResources, animation);
        myDisplay = new UserSelectDisplay(myResources, myControlPanel, mySims);
        stage.setTitle(myResources.getString("SIMTITLE"));
    }
    public Scene setupUI(Group viewGroup) {
        BorderPane bp = new BorderPane();
        bp.setTop(myDisplay.getDropDown());
        bp.setCenter(viewGroup);
        bp.setBottom(myControlPanel);
        Scene myScene = new Scene(bp, WIDTH, HEIGHT);
        myScene.getStylesheets().add(STYLESHEET);
        return myScene;
    }
    public String getSim() {
        return myDisplay.setSim();
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
