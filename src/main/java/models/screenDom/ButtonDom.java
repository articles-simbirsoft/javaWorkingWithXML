package models.screenDom;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ButtonDom {
    private HashMap<String, String> attributes = new HashMap<>();
    private String t;
    private List<DDom> dList = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("        <Button ");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(entry.getKey())
                    .append("='")
                    .append(entry.getValue())
                    .append("' ");
        }
        sb.append("> \n");
        sb.append("            <T>");
        sb.append((this.t == null) ? "null" : this.t);
        sb.append("</T> \n");
        for(DDom d : dList){
            sb.append(d);
        }
        sb.append("        </Button> \n");
        return sb.toString();
    }
}
