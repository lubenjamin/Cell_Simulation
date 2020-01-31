package cellsociety;

import cellsociety.View.UserInterface;
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
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group viewGroup = new Group();
        UserInterface UI = new UserInterface(stage, viewGroup, "English");
    }
}
