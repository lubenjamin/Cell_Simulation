package cellsociety;

import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;

/**
 * simulation size
 * probabilities
 * five titles
 * initial states (segregation)
 *
 */

public class FileReader{

    private String simType;
    private String rows;
    private String columns;

    public FileReader(String fileName) throws Exception {
        File simulation = new File("data/" + fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(simulation);
        doc.getDocumentElement().normalize();

        NodeList nodes = doc.getElementsByTagName("stock");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                simType = getValue("type", element);
                rows = getValue("rows", element);
                columns = getValue("columns", element);
            }
        }
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
