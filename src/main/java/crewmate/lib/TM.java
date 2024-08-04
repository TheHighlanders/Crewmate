package crewmate.lib;

import java.util.Map;
import java.util.HashMap;

public class TM {
    private Map<XboxButton, String> mappings;

    public TM(Object[][] mappingArray) {
        this.mappings = new HashMap<>();
        for (Object[] mapping : mappingArray) {
            XboxButton button = XboxButton.valueOf((String) mapping[0]);
            String triggerName = (String) mapping[1];
            mappings.put(button, triggerName);
        }
    }

    public Map<XboxButton, String> getMappings() {
        return mappings;
    }
}