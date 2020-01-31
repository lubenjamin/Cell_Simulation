package cellsociety;

import View.UserInterface;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * Start of the program.
     */
    private KeyFrame myFrame;
    private Timeline animation = new Timeline();
    private double MILLISECOND_DELAY = 1000.0;
    private double SECOND_DELAY = 100.0;

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group viewGroup = new Group();
        UserInterface UI = new UserInterface(stage, "English");
        stage.setScene(UI.setupUI(viewGroup));
        stage.show();
    }
}
