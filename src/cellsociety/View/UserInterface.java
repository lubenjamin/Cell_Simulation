package cellsociety.View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserInterface {
    private static final String TITLE = "Simulation_Team05";
    private static final double HEIGHT = 600; // temp
    private static final double WIDTH = 600; //temp
    private Scene userScene;

    public UserInterface(Stage stage) {
        stage.setTitle(TITLE);
        setupUI(stage);

    }
    private void setupUI(Stage stage) {
        userScene = new Scene(initControls(), WIDTH, HEIGHT, Color.LIGHTGRAY);
        stage.setScene(userScene);
        stage.show();
    }
    private Pane initControls() {
        BorderPane bp = new BorderPane();
        Button b1 = new Button("Test1");
        Button b2 = new Button("Test2");
        VBox v = new VBox();
        v.setSpacing(20);
        v.getChildren().add(b1);
        v.getChildren().add(b2);
        bp.setLeft(v);
        return bp;
    }
}
