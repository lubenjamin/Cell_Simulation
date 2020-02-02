package cellsociety;


import ControllerPackage.Controller;
import ControllerPackage.FireController;
import ControllerPackage.GameOfLifeController;
import ControllerPackage.PercolationController;
import ControllerPackage.PredPreyController;
import ControllerPackage.SegregationController;
import View.UserInterface;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {


  public static final int FRAMES_PER_SECOND = 1;

  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private Controller currentController;
  private UserInterface UI;
  private int mySim;
  private ArrayList<String> simNames;
  /**
   * Start of the program.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Group viewGroup = new Group();
    simNames = new ArrayList<>();
    simNames.add("fire");
    simNames.add("perc");
    Timeline myAnimation = new Timeline();
    UI = new UserInterface(stage, "English", simNames, myAnimation);
    stage.setScene(UI.setupUI(viewGroup));
    stage.show();

    mySim = UI.setSim();

    FileReader reader = new FileReader("percolation.xml");
    
    currentController = new PredPreyController(viewGroup, reader);

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(frame);
    myAnimation.play();

  }

  private void step() {
    if (UI.isSimLoaded) {
      if (!UI.isPaused || UI.isStep) {
        currentController.updateSim();
        UI.isStep = false;
      }
      if (UI.isReset) {
        currentController.resetSim();
        UI.isReset = false;
      }
    }
  }

}
