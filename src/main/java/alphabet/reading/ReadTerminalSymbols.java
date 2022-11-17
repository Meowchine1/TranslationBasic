package alphabet.reading;

import alphabet.entity.Category;
import alphabet.entity.Leksem;
import alphabet.entity.LeksemType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReadTerminalSymbols {
    private static int id = 0;
    public ArrayList<Leksem> keyWords = new ArrayList<>();
    public ArrayList<Leksem> operators = new ArrayList<>();

    public ReadTerminalSymbols(String filename){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));
            doc.getDocumentElement().normalize();

            NodeList keys = doc.getElementsByTagName("KEY");
            keyWords.addAll(getKeys(keys));
            NodeList specialRel = doc.getElementsByTagName("rel");
            operators.addAll(getLeksems(specialRel, LeksemType.REL));
            NodeList specialAs = doc.getElementsByTagName("as");
            operators.addAll(getLeksems(specialAs, LeksemType.AS));
            NodeList specialAo = doc.getElementsByTagName("ao");
            operators.addAll(getLeksems(specialAo, LeksemType.AO));


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public ReadTerminalSymbols() {}

    private ArrayList<Leksem> getKeys( NodeList nodeList){
        ArrayList<Leksem> leksems = new ArrayList<>();
        Boolean isDelimiter = false;
        Category category = Category.KEYWORD;
        for (int temp = 0; temp < nodeList.getLength(); temp++) {

            Node node = nodeList.item(temp);
            Element element = (Element) node;
            NodeList elem = element.getElementsByTagName("el");

            for(int i = 0; i< elem.getLength();i++){
                LeksemType type = switch (elem.item(i).getTextContent().toLowerCase()) {
                    case "input" -> LeksemType.INPUT;
                    case "output" -> LeksemType.OUTPUT;
                    case "do" -> LeksemType.DO;
                    case "until" -> LeksemType.UNTIL;
                    case "loop" -> LeksemType.LOOP;
                    default -> LeksemType.ERROR_TYPE;
                };
                leksems.add(new Leksem(id++,elem.item(i).getTextContent(), category, type, isDelimiter));
            }
        }

        return leksems;
    }

    private ArrayList<Leksem> getLeksems(NodeList nodeList, LeksemType type){
        ArrayList<Leksem> leksems = new ArrayList<>();
        Boolean isDelimiter = true;
        for (int temp = 0; temp < nodeList.getLength(); temp++) {

            Node node = nodeList.item(temp);
                Element element = (Element) node;
                NodeList elem = element.getElementsByTagName("el");

                for(int i = 0; i< elem.getLength();i++){
                    String value = switch (elem.item(i).getTextContent()) {
                        case "l" -> "<";
                        case "le" -> "<=";
                        case "g" -> ">";
                        case "ge" -> ">=";
                        case "e" -> "==";
                        case "ne" -> "<>";
                        case "plus" -> "+";
                        case "minus" -> "-";
                        case "assign" -> "=";
                        default -> "";
                    };
                    leksems.add(new Leksem(id++,value, Category.OPERATOR, type, isDelimiter));
                }
        }

        return leksems;
    }



}