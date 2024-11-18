package crewmate.lib.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LatchedBooleanTest {

    @Test
    void update_NewValueTrueLastValueFalse_ReturnsTrue() {
        LatchedBoolean latchedBoolean = new LatchedBoolean();
        boolean result = latchedBoolean.update(true);
        assertTrue(result);
        result = latchedBoolean.update(true);
        assertFalse(result);
    }

    @Test
    void update_NewValueFalseLastValueTrue_ReturnsFalse() {
        LatchedBoolean latchedBoolean = new LatchedBoolean();
        latchedBoolean.update(true);
        boolean result = latchedBoolean.update(false);
        assertFalse(result);
    }

    @Test
    void update_NewValueFalseLastValueFalse_ReturnsFalse() {
        LatchedBoolean latchedBoolean = new LatchedBoolean();
        boolean result = latchedBoolean.update(false);
        assertFalse(result);
    }

    @Test
    void unlatch_SetsLastValueToFalse() {
        LatchedBoolean latchedBoolean = new LatchedBoolean();
        latchedBoolean.update(true);
        latchedBoolean.unlatch();
        boolean result = latchedBoolean.update(true);
        assertTrue(result);
    }
}
