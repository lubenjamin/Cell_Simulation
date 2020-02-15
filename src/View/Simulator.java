package View;

import controllerPackage.Controller;
import controllerPackage.FireController;
import controllerPackage.GameOfLifeController;
import controllerPackage.PercolationController;
import controllerPackage.PredPreyController;
import controllerPackage.RockPaperScissors;
import controllerPackage.SegregationController;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.FileReader;


/**
 * This code is essentially the 'main' of the program. It initializes many of the core objects needed
 * for the program to work. The variables and method names make functions and variable purposes clear and easy to read
 * This class also is a part of what is needed to extend the project and add new simulations. To add a new sim, part of the
 * process would be adding a new case to the switch in the checkSimName method
 */
public class Simulator {

  private final int FRAMES_PER_SECOND = 1;
  private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

  private final String EXTENSION = ".xml";
  private final String BASECASE = "Switch Simulation";

  private final Group viewGroup = new Group();
  private final Group simUIGroup = new Group();

  private Controller currentController = null;
  private UserInterface UI;
  private ControlPanel myControlPanel;
  private Scene myScene;
  private Timeline myAnimation;
  private PredPreyGraph myGraph = null;

  private String mySim;
  private String myNewSim;
  private ArrayList<String> simNames;



  /**
   * Start of the program.
   * initialze timeline and controlpanel
   * scan data folder for xml files
   */
  public Simulator(Stage stage) {
    getFileNames();
    myAnimation = new Timeline();
    myControlPanel = new ControlPanel(myAnimation);
    UI = new UserInterface(stage, "English", simNames, myControlPanel);

    myScene = UI.setupUI(viewGroup, simUIGroup);

    stage.setScene(myScene);
    stage.show();

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step();
    });
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(frame);
    myAnimation.play();
  }


  private void step() {
    myNewSim = UI.getSim();
    if (!myNewSim.equals(BASECASE) && !myNewSim.equals(mySim)) {
      makeNewSim();
    }
    if (myControlPanel.getSimLoadStatus() && mySim != null && currentController != null) {
      if (!myControlPanel.getPauseStatus() || myControlPanel.getUpdateStatus()) {
        currentController.updateSim();
        myControlPanel.resetControl();
      }
      if (myControlPanel.getResetStatus()) {
        resetSimStep();
      }
    }
  }

  private void resetSimStep() {
    if (myGraph != null) {
      myGraph.clear();
    }
    currentController.resetSim();
    myControlPanel.resetControl();
  }

  private void makeNewSim() {
    if (currentController != null) {
      viewGroup.getChildren().clear();
      simUIGroup.getChildren().clear();
    }
    UI.removeGraph();
    myControlPanel.setPause();
    FileReader reader = new FileReader(myNewSim + EXTENSION);
    mySim = myNewSim;
    checkSimName(reader.getString("type"), reader);
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

  private void checkSimName(String name, FileReader reader) {
    if (name == null) {
      currentController = null;
      name = "";
    }
    switch (name) {
      case "Percolation":
        currentController = new PercolationController(viewGroup, reader, simUIGroup);
        break;
      case "Segregation":
        currentController = new SegregationController(viewGroup, reader, simUIGroup);
        break;
      case "Fire":
        currentController = new FireController(viewGroup, reader, simUIGroup);
        break;
      case "GameOfLife":
        currentController = new GameOfLifeController(viewGroup, reader, simUIGroup);
        break;
      case "PredatorPrey":
        myGraph = UI.addPredChart();
        currentController = new PredPreyController(viewGroup, reader, simUIGroup, myGraph);
        break;
      case "RockPaperScissors":
        currentController = new RockPaperScissors(viewGroup, reader, simUIGroup);
        break;
    }
  }
}

