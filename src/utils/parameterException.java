package utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class parameterException extends NullPointerException {
    public parameterException(String message, Object ... values){
        super(String.format(message, values));
    }

    public void showError (String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        //alert.showAndWait();
        Platform.runLater(alert::showAndWait);
    }
}
