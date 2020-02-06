package View;

import controllerPackage.Controller;
import controllerPackage.FireController;
import controllerPackage.GameOfLifeController;
import controllerPackage.PercolationController;
import controllerPackage.PredPreyController;
import controllerPackage.SegregationController;
import View.UserInterface;
import utils.FileReader;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.XMLException;

import java.io.File;


/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Simulator {


  public static final int FRAMES_PER_SECOND = 1;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

  private static final String EXTENSION = ".xml";

  private Controller currentController;
  private UserInterface UI;
  private final Group viewGroup = new Group();

  private String mySim;
  private String myNewSim;
  private ArrayList<String> simNames;

  /**
   * Start of the program.
   */
  public void initialize(Stage stage){
    getFileNames();

    Timeline myAnimation = new Timeline();
    UI = new UserInterface(stage, "English", simNames, myAnimation);
    stage.setScene(UI.setupUI(viewGroup));
    stage.show();

    FileReader reader = new FileReader(UI.getSim() + EXTENSION);
    mySim = reader.getSimType();
    checkSimName(mySim, reader, false);

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
        step();
    });

    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(frame);
    myAnimation.play();
  }

  private void step(){
    myNewSim = UI.getSim();
    if (!myNewSim.equals(mySim)) {
      currentController.clear();
      UI.setControlPause(true);
      FileReader reader = new FileReader(myNewSim + EXTENSION);
      mySim = reader.getSimType();
      checkSimName(mySim, reader, true);
    }
    if (UI.getLoadStatus() && mySim != null) {
      if (!UI.getPauseStatus() || (UI.getPauseStatus() && UI.getStepStatus())) {

        currentController.updateSim();
        UI.setControlStep(false);
      }
      if (UI.getResetStatus()) {
        currentController.resetSim();
        UI.setControlReset(false);
      }
    }
  }

  private void getFileNames() {
    File folder = new File("data/");
    File[] listOfFiles = folder.listFiles();
    simNames = new ArrayList<>();
    assert listOfFiles != null;
    for (File listOfFile : listOfFiles) {
      if (listOfFile.isFile() && listOfFile.getName().contains(EXTENSION)) {
        simNames.add(listOfFile.getName().split(EXTENSION)[0]);
      }
    }
  }

  private void checkSimName(String name, FileReader reader, boolean isUpdating) {
    switch (name) {
      case "Percolation":
        currentController = new PercolationController(viewGroup, reader);
        break;
      case "Segregation":
        currentController = new SegregationController(viewGroup, reader);
        break;
      case "Fire":
        currentController = new FireController(viewGroup, reader);
        break;
      case "GameOfLife":
        currentController = new GameOfLifeController(viewGroup, reader);
        break;
      case "PredatorPrey":
        currentController = new PredPreyController(viewGroup, reader);
        break;

    }
    if (isUpdating) {
      mySim = name.toLowerCase();
    }

  }
}
