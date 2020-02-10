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

    private final String simType;
    private final String rows;
    private final String columns;
    private String errorMessage;
    private boolean isSimLoaded;

    public FileReader(String fileName) throws XMLException {
        setElement(fileName);
        simType = getValue("type", simElement);
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
            errorMessage = parameter+" parameter is invalid";
            throw new parameterException(parameter+" parameter is invalid", parameter);
        }
    }

    public double getDoubleValue(String parameter) {
        try {
            return Double.parseDouble(getValue(parameter, simElement));
        }catch(NullPointerException e){
            errorMessage = parameter+" parameter is invalid";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");

            Platform.runLater(alert::showAndWait);
            throw new parameterException(parameter+" parameter is invalid", parameter);
        }
    }

    public String getString(String parameter) {
        try {
            return getValue(parameter, simElement);
        }catch(NullPointerException e){
            errorMessage = parameter+" parameter is invalid";
            throw new parameterException(parameter+" parameter is invalid", parameter);
        }
    }



    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodes.item(0);
        return node.getNodeValue();
    }

    public String getSimType(){
        return simType;
    }

    public int getRows(){
        return Integer.parseInt(rows);
    }

    public int getColumns(){
        return Integer.parseInt(columns);
    }

    public String getErrorMessage(){
        return errorMessage;
    }

}
