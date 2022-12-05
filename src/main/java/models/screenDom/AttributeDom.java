package models.screenDom;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AttributeDom {
    private HashMap<String, String> attributes = new HashMap<>();
    private String v;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("        <Attribute ");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(entry.getKey())
                    .append("='")
                    .append(entry.getValue())
                    .append("' ");
        }
        sb.append("> \n");
        sb.append("            <V>");
        sb.append((this.v == null) ? "null" : this.v);
        sb.append("</V> \n");
        sb.append("        </Attribute> \n");
        return sb.toString();
    }
}
