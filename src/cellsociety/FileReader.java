package cellsociety;

import java.io.IOException;
import javax.print.DocFlavor.READER;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import org.xml.sax.SAXException;

/**
 * simulation size
 * probabilities
 * five titles
 * initial states (segregation)
 *
 */

public class FileReader{

    private Element simElement;

    private String simType;
    private String rows;
    private String columns;
    private String file;

    public FileReader(String fileName){
        if(!setElement(fileName)){
            System.out.println("NO SIMULATION ELEMENT IN FILE");
        }
        simType = getValue("type", simElement);
        rows = getValue("rows", simElement);
        columns = getValue("columns", simElement);


    }

    public boolean setElement(String fileName){

        try{

            file = fileName;
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
            System.out.println("FILE IS NOT CORRECT");
            return false;
        }
        catch(Exception e){
            System.out.println("FILE IS NOT CORRECT");
            return false;
        }

    }

    public int getIntValue(String parameter){
        return Integer.parseInt(getValue(parameter,simElement));
    }

    public double getDoubleValue(String parameter) {
        return Double.parseDouble(getValue(parameter,simElement));
    }

    public String getString(String parameter) {
        return getValue(parameter,simElement);
    }



    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
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


}
