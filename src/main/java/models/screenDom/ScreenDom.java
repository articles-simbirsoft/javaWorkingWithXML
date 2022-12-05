package models.screenDom;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class ScreenDom {
    private HashMap<String, String> attributes = new HashMap<>();
    private String title;
    private String description;
    private List<ButtonDom> buttonList = new ArrayList<>();
    private List<AttributeDom> attributeList = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<Screen ");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(entry.getKey()).append("='").append(entry.getValue()).append("'");
        }
        sb.append("> \n");
        sb.append("    <Title>");
        sb.append((this.title == null) ? "null" : this.title);
        sb.append("</Title> \n");
        sb.append("    <Description>");
        sb.append((this.description == null) ? "null" : this.description);
        sb.append("</Description> \n");
        sb.append("    <Buttons> \n");
        for (ButtonDom button : buttonList) {
            sb.append(button);
        }
        sb.append("    </Buttons> \n");
        sb.append("    <Attributes> \n");
        for (AttributeDom attribute : attributeList) {
            sb.append(attribute);
        }
        sb.append("    </Attributes> \n");
        sb.append("</Screen");
        return sb.toString();
    }
}
