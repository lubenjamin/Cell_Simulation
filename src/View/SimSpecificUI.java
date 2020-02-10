package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SimSpecificUI {
  private HashMap<String, Spinner> spinners;

  public SimSpecificUI(Group group, Map<String, Object> varNames){
   makeSpinners(group, varNames);
  }

  private void makeSpinners(Group group,  Map<String, Object> varNames) {
    VBox v = new VBox();
    spinners = new HashMap<>();
    for(String k : varNames.keySet()){
      HBox h = new HBox();
      Label b = new Label(k);
      Object o = varNames.get(k);
      Spinner s;
      if(o instanceof Double){
        s = new Spinner(0,1, (double) varNames.get(k), .05);
      }
      else{
        s = new Spinner(0,100, (int) varNames.get(k), 1);
      }

      s.setEditable(true);
      spinners.put(k, s);
      h.getChildren().addAll(s, b);
      v.getChildren().add(h);
    }
    v.setLayoutX(100);
    v.setLayoutY(100);
    group.getChildren().add(v);
  }

  public Map<String, Object> getValues(){
    HashMap <String, Object> ret = new HashMap<>();
    for (String k : spinners.keySet()){
      ret.put(k, spinners.get(k).getValue());
    }
    return ret;
  }


}
