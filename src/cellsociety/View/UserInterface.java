package cellsociety.View;

import cellsociety.Model;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserInterface {
    private static final String TITLE = "Simulation_Team05";
    private static final double HEIGHT = 300; // temp
    private static final double WIDTH = 300; //temp
    private Scene userScene;

    public UserInterface(Stage stage, Group viewGroup) {
        stage.setTitle(TITLE);
        setupUI(stage, viewGroup);

    }
    private void setupUI(Stage stage, Group viewGroup) {
        userScene = new Scene(initControls(viewGroup), 1000, 600, Color.LIGHTGRAY);
        stage.setScene(userScene);
        stage.show();
    }
    private Pane initControls(Group viewGroup) {
        BorderPane bp = new BorderPane();
        Model m = new Model(WIDTH, HEIGHT);
        View view = new View(viewGroup, WIDTH, HEIGHT, m);
        Button b1 = new Button("Test1");
        Button b2 = new Button("Test2");
        Slider s1 = new Slider();
        s1.setMin(0);
        s1.setMax(100);
        s1.setValue(25);
        s1.setShowTickLabels(true);
        s1.setShowTickMarks(true);
        s1.setMajorTickUnit(50);
        s1.setMinorTickCount(5);
        s1.setBlockIncrement(10);
        VBox v = new VBox();
        HBox h = new HBox();
        h.setSpacing(20);
        h.setAlignment(Pos.BASELINE_CENTER);
        v.setSpacing(20);
        v.getChildren().add(b1);
        v.getChildren().add(b2);
        h.getChildren().add(s1);
        bp.setBottom(h);
        bp.setLeft(v);
        bp.setCenter(viewGroup);

        return bp;
    }
}
