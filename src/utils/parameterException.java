package utils;

public class parameterException extends NullPointerException {
    public parameterException(String message, Object ... values){
        super(String.format(message, values));
    }
}
