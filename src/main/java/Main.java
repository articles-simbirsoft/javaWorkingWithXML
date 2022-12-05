import models.screen.Screen;
import models.screenDom.AttributeDom;
import models.screenDom.ButtonDom;
import models.screenDom.DDom;
import models.screenDom.ScreenDom;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException, ParserConfigurationException, SAXException {
        exampleJaxb();
        ScreenDom screen = new ScreenDom();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse("./body.xml");

        Node root = document.getFirstChild();
        NamedNodeMap attributes = root.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            screen.getAttributes().put(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
        }
        NodeList childNode = root.getChildNodes();
        for (int i = 0; i < childNode.getLength(); i++) {
            Node element = childNode.item(i);
            if (element.getNodeName().equals("Title")) {
                screen.setTitle(element.getTextContent());
            }
            if (element.getNodeName().equals("Description")) {
                screen.setDescription(element.getTextContent());
            }
        }

        NodeList rootList = document.getDocumentElement().getElementsByTagName("Button");
        for (int j = 0; j < rootList.getLength(); j++) {
            attributes = rootList.item(j).getAttributes();
            screen.getButtonList().add(new ButtonDom());
            for (int i = 0; i < attributes.getLength(); i++) {
                screen.getButtonList().get(j).getAttributes().put(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
            }
            childNode = rootList.item(j).getChildNodes();
            for (int i = 0; i < childNode.getLength(); i++) {
                Node element = childNode.item(i);
                NamedNodeMap attr = element.getAttributes();
                if (element.getNodeName().equals("T")) {
                    screen.getButtonList().get(j).setT(element.getTextContent());
                }
                if (element.getNodeName().equals("D")) {
                    DDom d = new DDom();
                    d.setD(element.getTextContent());
                    if (attr.getLength() != 0) {
                        for (int k = 0; k < attr.getLength(); k++) {
                            d.getAttributes().put(attr.item(k).getNodeName(), attr.item(k).getNodeValue());
                        }
                    }
                    screen.getButtonList().get(j).getDList().add(d);
                }
            }
        }

        rootList = document.getDocumentElement().getElementsByTagName("Attribute");
        for (int j = 0; j < rootList.getLength(); j++) {
            AttributeDom attributeDom = new AttributeDom();
            attributes = rootList.item(j).getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                attributeDom.getAttributes().put(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
            }
            childNode = rootList.item(j).getChildNodes();
            for (int i = 0; i < childNode.getLength(); i++) {
                Node element = childNode.item(i);
                if (element.getNodeName().equals("V")) {
                    attributeDom.setV(element.getTextContent());
                }
            }
            screen.getAttributeList().add(attributeDom);
        }
        System.out.println(screen);
    }

    public static void exampleJaxb() throws JAXBException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("./body.xml"));
        String body = br.lines().collect(Collectors.joining());
        StringReader reader = new StringReader(body);
        JAXBContext context = JAXBContext.newInstance(Screen.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Screen screen = (Screen) unmarshaller.unmarshal(reader);
        System.out.println(screen);

        StringWriter writer = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        screen.setTitle("Изменили заглавие");
        screen.getButtonList().get(0).getDList().get(0).setColor("black");
        screen.getButtonList().get(0).getDList().get(0).setD("Цвет кнопки черный");
        marshaller.marshal(screen, writer);
        System.out.println(writer);
    }
}
