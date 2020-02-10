package utils;

import javax.xml.parsers.*;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class FileReader{

    private Element simElement;

    private final String rows;
    private final String columns;
    private String errorMessage;

    public FileReader(String fileName) throws XMLException {
        setElement(fileName);
        rows = getValue("rows", simElement);
        columns = getValue("columns", simElement);
    }

    public boolean setElement(String fileName){

        try{
            File simulation = new File("data/" + fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(simulation);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("simulation");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    simElement = (Element) node;
                    return true;
                }
            }
            return false;
        }
        catch(RuntimeException | ParserConfigurationException | SAXException | IOException e){
            throw new XMLException("INVALID FILE: "+fileName, fileName);
        }
    }

    public int getIntValue(String parameter){
        try {
            return Integer.parseInt(getValue(parameter, simElement));
        }catch(NullPointerException e){
            popUp(parameter);
            //throw new parameterException(errorMessage, parameter);
            return 0;
        }
    }


    public double getDoubleValue(String parameter) {
        try {
            return Double.parseDouble(getValue(parameter, simElement));
        }catch(NullPointerException e){
            popUp(parameter);
            //throw new parameterException(errorMessage, parameter);
            return 0;
        }
    }

    public String getString(String parameter) {
        try {
            return getValue(parameter, simElement);
        }catch(NullPointerException e){
            errorMessage = parameter+" parameter is invalid";
            popUp(parameter);
            //throw new parameterException(errorMessage, parameter);
            return null;
        }
    }

    private void popUp(String parameter) {
        errorMessage = parameter + " parameter is invalid";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Parameter Error");
        alert.setHeaderText(errorMessage);
        Platform.runLater(alert::showAndWait);
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodes.item(0);
        return node.getNodeValue();
    }

    public int getRows(){
        return Integer.parseInt(rows);
    }

    public int getColumns(){
        return Integer.parseInt(columns);
    }
}
