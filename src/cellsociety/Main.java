package cellsociety;

import cellsociety.View.UserInterface;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    private static final int FRAMES_PER_SECOND = 100;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        UserInterface UI = new UserInterface(stage);

        
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }
    private void step(){

    }
}
