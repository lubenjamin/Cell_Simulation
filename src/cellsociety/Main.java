package cellsociety;


import ControllerPackage.Controller;
import ControllerPackage.PercolationController;
import ControllerPackage.SegregationController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import View.UserInterface;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  private static final int FRAMES_PER_SECOND = 8;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private Controller currentController;
  private UserInterface UI;
  /**
   * Start of the program.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Group viewGroup = new Group();
    UI = new UserInterface(stage, "English");
    stage.setScene(UI.setupUI(viewGroup));
    stage.show();

    FileReader reader = new FileReader("segregation.xml");

    if(reader.getSimType()!=null && reader.getSimType().equals("Segregation")){
      currentController = new SegregationController(viewGroup, reader);
    }
    else{
      currentController = new PercolationController(viewGroup, reader);
    }


    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

  }

  private void step() {
    if (! UI.isPaused) {
      currentController.updateSim();
    }

  }

}
