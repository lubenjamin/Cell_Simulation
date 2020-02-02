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

import java.io.File;
import java.util.ArrayList;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {


  public static final int FRAMES_PER_SECOND = 1;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

  private static final String EXTENSION = ".xml";

  private Controller currentController;
  private UserInterface UI;
  private Stage firstSim;

  private String mySim;
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

    File folder = new File("data/");
    File[] listOfFiles = folder.listFiles();

    simNames = new ArrayList<>();
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(EXTENSION)) {
        simNames.add(listOfFiles[i].getName().split(EXTENSION)[0]);
      }
    }
    
    Timeline myAnimation = new Timeline();
    UI = new UserInterface(stage, "English", simNames, myAnimation);
    stage.setScene(UI.setupUI(viewGroup));
    stage.show();

    FileReader reader = new FileReader(UI.setSim()+EXTENSION);

    if (reader.getSimType()!=null && reader.getSimType().equals("Segregation")){
      currentController = new PercolationController(viewGroup, reader);
    }
    else{
      currentController = new PercolationController(viewGroup, reader);
    }

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(frame);
    myAnimation.play();

  }

  private void step() {
    mySim = UI.setSim();
    if (UI.isSimLoaded && mySim != null) {
      if (!UI.isPaused || (UI.isPaused && UI.isStep)) {
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
