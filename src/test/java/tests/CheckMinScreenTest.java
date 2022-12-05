package tests;

import helper.ButtonsFactory;
import models.screenDom.AttributeDom;
import models.screenDom.ButtonDom;
import models.screenDom.DDom;
import models.screenDom.ScreenDom;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class CheckMinScreenTest {
    ButtonsFactory bf = new ButtonsFactory();
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void checkDisplayButtonsOfMainScreenTest() throws ParserConfigurationException, IOException, SAXException {
        ScreenDom screenDom = getScreen();
        checkButtonsOfResponse(softAssert, screenDom, bf.getNewOfferButton(),
                bf.getReturnProductButton(), bf.getHistoryButton());
        softAssert.assertAll();
    }

    public SoftAssert checkButtonsOfResponse(SoftAssert softAssert, ScreenDom screen, ButtonDom... verifiableButtons) {
        List<ButtonDom> buttonList = screen.getButtonList();
        for (ButtonDom verifiableButton : verifiableButtons) {
            List<DDom> dList = verifiableButton.getDList();
            ButtonDom button = buttonList.stream()
                    .filter(b -> ((b.getT().equals((verifiableButton.getT())))))
                    .findFirst()
                    .orElse(null);
            if (button == null) {
                softAssert.fail("В ответе на экране '" + screen.getTitle() + "' отсутствует кнопка '" + verifiableButton.getT() + "'.");
            } else {
                for (DDom d : dList) {
                    DDom dDom = button
                            .getDList().stream()
                            .filter(dd -> ((dd.getD().equals(d.getD()))))
                            .findFirst()
                            .orElse(null);
                    if (dDom == null) {
                        softAssert.fail("В ответе на экране '" + screen.getTitle() + "' в кнопке '" + verifiableButton.getT() + "' отсутствует подсказка '" + d.getD() + "'.");
                    }
                }
            }
        }
        return softAssert;
    }

    private ScreenDom getScreen() throws ParserConfigurationException, IOException, SAXException {
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
        return screen;
    }
}
