package utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class parameterException extends NullPointerException {
    public parameterException(String message, Object ... values){
        super(String.format(message, values));
    }
}
