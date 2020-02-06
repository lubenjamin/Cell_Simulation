package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserSelectDisplay extends Stage {
    private static final String STYLESHEET = "resources/default.css";

    private Button mySegButton = new Button();
    private Button myPercButton = new Button();
    private Button myLifeButton = new Button();
    private Button myFireButton = new Button();
    private Button myPredButton = new Button();

    private final ComboBox<String> myDropDown;

    private final ResourceBundle myResources;
    private final ControlPanel myControlPanel;
    private final ArrayList<String> mySims;

    public UserSelectDisplay(ResourceBundle resources, ControlPanel controls, ArrayList<String> sims) {
        this.myResources = resources;
        this.myControlPanel = controls;
        this.mySims = sims;
        myDropDown = new ComboBox<>();
        initUserSelectDisplay();
        initSimSelect(event -> setSim(), mySims);
    }
    public String setSim() {
        String st = myDropDown.getValue();
//        if (st == "PredatorPrey") {
//            myControlPanel.addPredPreyGraph();
//        }
        myControlPanel.isSimLoaded=true;
        return st;
    }
    public ComboBox<String> getDropDown() {
        return myDropDown;
    }
    private void initUserSelectDisplay() {
            setTitle(myResources.getString("CHOICETITLE"));
            HBox v = new HBox();
            mySegButton = myControlPanel.makeButton("SIM1", event -> pickSim(mySegButton));
            myPercButton = myControlPanel.makeButton("SIM2", event -> pickSim(myPercButton));
            myLifeButton = myControlPanel.makeButton("SIM3", event -> pickSim(myLifeButton));
            myFireButton = myControlPanel.makeButton("SIM4", event -> pickSim(myFireButton));
            myPredButton = myControlPanel.makeButton("SIM5", event -> pickSim(myPredButton));
            v.getChildren().add(mySegButton);
            v.getChildren().add(myPercButton);
            v.getChildren().add(myLifeButton);
            v.getChildren().add(myFireButton);
            v.getChildren().add(myPredButton);
            Scene s = new Scene(v);
            s.getStylesheets().add(STYLESHEET);
            setScene(s);
            showAndWait();
    }
    private void pickSim(Button b) {
        String ret = b.getText();
//        if (ret.equals("PredatorPrey")) {
//            myControlPanel.addPredPreyGraph();
//        }
        myDropDown.setValue(ret);
        setSim();
        close();
    }
    private void initSimSelect(EventHandler<ActionEvent> handler, ArrayList<String> simNames) {
        myDropDown.setOnAction(handler);
        for (String s : simNames) {
            myDropDown.getItems().add(s);
        }
    }

}
