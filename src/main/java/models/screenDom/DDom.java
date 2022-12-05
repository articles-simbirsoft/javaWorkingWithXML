package models.screenDom;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class DDom {
    private HashMap<String, String> attributes = new HashMap<>();
    private String d;

    public DDom withD(String value) {
        this.d = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("            <D");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(" ")
                    .append(entry.getKey())
                    .append(" ='")
                    .append(entry.getValue())
                    .append("'");
        }
        sb.append(">");
        sb.append((this.d == null) ? "null" : this.d);
        sb.append("</D> \n");
        return sb.toString();
    }
}
