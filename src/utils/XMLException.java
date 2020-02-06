package utils;

import java.io.FileNotFoundException;

public class XMLException extends FileNotFoundException {
    public XMLException(String message, Object ... values){
        super(String.format(message,values));
    }
}
