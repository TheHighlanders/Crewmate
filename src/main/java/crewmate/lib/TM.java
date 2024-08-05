package crewmate.lib;

import java.util.Map;
import java.util.HashMap;

public class TM {
    private Map<XboxButton, String> mappings;

    public TM(Object[][] mappingArray) {
        this.mappings = new HashMap<>();
        for (Object[] mapping : mappingArray) {
            XboxButton button;
            if (mapping.length != 2) {
                throw new IllegalArgumentException("Each mapping should have exactly two elements.");
            }
            if (mapping[0] instanceof String) {
                button = XboxButton.valueOf((String) mapping[0]);
            } else if (mapping[0] instanceof XboxButton) {
                button = (XboxButton) mapping[0];
            }
            else {
                throw new IllegalArgumentException("First element of each mapping should be a String or XboxButton.");
            }

            String triggerName = (String) mapping[1];
            mappings.put(button, triggerName);
        }
    }

    public Map<XboxButton, String> getMappings() {
        return mappings;
    }
}