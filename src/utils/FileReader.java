package utils;

import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * FileReader is the basis of all xml file reading for the program
 * All simulation information is received from an xml file via FileReader
 * When invalid or missing data is present within an xml file the error is handled via an error popup alert
 */
public class FileReader {

  private final String rows;
  private final String columns;
  private Element simElement;
  private String errorMessage;

  /**
   * FileReader constructor creates an element and uses that element to find rows and columns
   * @param fileName the name of file wanting to be read
   */
  public FileReader(String fileName) throws XMLException {
    setElement(fileName);
    rows = getValue("rows", simElement);
    columns = getValue("columns", simElement);
  }

  /**
   * getValue returns the value found in the element that is identified by the tag
   * the tag is usually a certain parameter the controller wants to run the simulation
   * @param tag the name of the parameter wanting to be read
   * @param element the element the parameter will be found in
   * @return the int, double, string, etc value that is associated with that tag parameter
   */
  private static String getValue(String tag, Element element) {
    NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
    Node node = nodes.item(0);
    return node.getNodeValue();
  }

  /**
   * setElement identifies the element to be used to find all the wanted parameters
   * if the xml does not contain the simulation tag an XMLException is thrown showing that the file is invalid and cannot be read
   * @param fileName the name of the file that wants to be read
   * @return the element that includes the parameters and parameter values associated with the simulation file
   */
  public boolean setElement(String fileName) {

    try {
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
    } catch (RuntimeException | ParserConfigurationException | SAXException | IOException e) {
      throw new XMLException("INVALID FILE: " + fileName, fileName);
    }
  }

  /**
   * getIntValue takes in a parameter and returns the integer value associated with the parameter in the file
   * @param parameter the name of the parameter wanting to be found
   * @return the integer value of the parameter as found in the file
   */
  public int getIntValue(String parameter) {
    try {
      return Integer.parseInt(getValue(parameter, simElement));
    } catch (NullPointerException e) {
      popUp(parameter);
      //throw new parameterException(errorMessage, parameter);
      return 0;
    }
  }

  /**
   * getDoubleValue takes in a parameter and returns the double value associated with the parameter in the file
   * @param parameter the name of the parameter wanting to be found
   * @return the double value of the parameter as found in the file
   */
  public double getDoubleValue(String parameter) {
    try {
      return Double.parseDouble(getValue(parameter, simElement));
    } catch (NullPointerException e) {
      popUp(parameter);
      //throw new parameterException(errorMessage, parameter);
      return 0;
    }
  }

  /**
   * getString takes in a parameter and returns the string associated with the parameter in the file
   * @param parameter the name of the parameter wanting to be found
   * @return the string of the parameter as found in the file
   */
  public String getString(String parameter) {
    try {
      return getValue(parameter, simElement);
    } catch (NullPointerException e) {
      popUp(parameter);
      //throw new parameterException(errorMessage, parameter);
      return null;
    }
  }

  /**
   * popUp is triggered when a parameter wanting to be read is invalid or missing
   * when catching the error an error pop up appears informing the user that the parameter is invalid within the file trying to be read
   * @param parameter the parameter that is invalid or does not exist in the file
   */
  private void popUp(String parameter) {
    errorMessage = parameter + " parameter is invalid";
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Parameter Error");
    alert.setHeaderText(errorMessage);
    Platform.runLater(alert::showAndWait);
  }

  /**
   * simple getter method that retrieves rows value
   * @return the number of rows in the specified simulation
   */
  public int getRows() {
    return Integer.parseInt(rows);
  }

  /**
   * simple getter method that retrieves columns value
   * @return the number of columns in the specified simulation
   */
  public int getColumns() {
    return Integer.parseInt(columns);
  }

  /**
   * checks if a certain parameter exists within a file
   * @param parameter the parameter wanting to see if exists
   * @return true if the value of the parameter is able to be retrieved, false otherwise
   */
  public boolean checkExists(String parameter) {
    try {
      getValue(parameter, simElement);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
