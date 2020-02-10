package View;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SimSpecificUI {

  private HashMap<String, Spinner> spinners;

  public SimSpecificUI(Group group, Map<String, Object> varNames) {
    spinners = new HashMap<>();
    makeSpinners(group, varNames);
  }

  private void makeSpinners(Group group, Map<String, Object> varNames) {
    VBox v = new VBox();
    for (String k : varNames.keySet()) {
      v.getChildren().add(makeInput(k, varNames.get(k)));
      v.setSpacing(20);
    }
    v.setLayoutX(100);
    v.setLayoutY(100);
    group.getChildren().add(v);
  }

  private HBox makeInput(String k, Object o) {
    HBox h = new HBox();
    Label b = new Label(k);
    Spinner s;
    if (o instanceof Double) {
      s = new Spinner(0, 1, (double) o, .05);
    } else {
      s = new Spinner(0, 100, (int) o, 1);
    }
    s.setEditable(true);
    spinners.put(k, s);
    h.getChildren().addAll(s, b);
    h.setSpacing(20);
    return h;
  }

  public Map<String, Object> getValues() {
    HashMap<String, Object> ret = new HashMap<>();
    for (String k : spinners.keySet()) {
      ret.put(k, spinners.get(k).getValue());
    }
    return ret;
  }


}
