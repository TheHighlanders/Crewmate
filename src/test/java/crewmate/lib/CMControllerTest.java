package crewmate.lib;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CMControllerTest {

    private CMController controller;

    @BeforeEach
    public void setUp() {
        controller = new CMController(0);
    }

    @Test
    public void testMapTriggersWithValidMappings() {
        Object[][] mappings = {
            {"trigger1", controller.a()},
            {"trigger2", controller.b()}
        };
        controller.mapTriggers(mappings);
        
        Map<String, Trigger> triggerMap = controller.getTriggerMap();
        assertEquals(2, triggerMap.size());
        assertTrue(triggerMap.containsKey("trigger1"));
        assertTrue(triggerMap.containsKey("trigger2"));
    }

    @Test
    public void testMapTriggersWithEmptyMappings() {
        Object[][] mappings = {};
        controller.mapTriggers(mappings);
        
        Map<String, Trigger> triggerMap = controller.getTriggerMap();
        assertTrue(triggerMap.isEmpty());
    }

    @Test
    public void testMapTriggersWithInvalidMappings() {
        Object[][] mappings = {
            {"trigger1", controller.a()},
            {123, "Not a Trigger"},
            {"trigger2", controller.b()},
            {"invalidTrigger", "Not a Trigger"}
        };
        controller.mapTriggers(mappings);
        
        Map<String, Trigger> triggerMap = controller.getTriggerMap();
        assertEquals(2, triggerMap.size());
        assertTrue(triggerMap.containsKey("trigger1"));
        assertTrue(triggerMap.containsKey("trigger2"));
        assertFalse(triggerMap.containsKey("invalidTrigger"));
    }

    @Test
    public void testMapTriggersWithDuplicateKeys() {
        Trigger trigger1 = controller.a();
        Trigger trigger2 = controller.b();
        Object[][] mappings = {
            {"trigger1", trigger1},
            {"trigger1", trigger2}
        };
        controller.mapTriggers(mappings);
        
        Map<String, Trigger> triggerMap = controller.getTriggerMap();
        assertEquals(1, triggerMap.size());
        assertSame(trigger2, triggerMap.get("trigger1"));
    }

    @Test
    public void testMapTriggersWithNullValues() {
        Object[][] mappings = {
            {"trigger1", null},
            {"trigger2", controller.a()},
            {null, controller.b()}
        };
        controller.mapTriggers(mappings);
        
        Map<String, Trigger> triggerMap = controller.getTriggerMap();
        assertEquals(1, triggerMap.size());
        assertTrue(triggerMap.containsKey("trigger2"));
    }
}
