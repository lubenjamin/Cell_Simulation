import View.Simulator;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.XMLException;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage){
    new Simulator(primaryStage, false, null);
  }
}
