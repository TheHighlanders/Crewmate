package crewmate.lib.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeadbandTest {

    @Test
    void inDeadband_InputWithinWindow_ReturnsTrue() {
        assertTrue(Deadband.inDeadband(0.5, 1.0));
        assertTrue(Deadband.inDeadband(-0.5, 1.0));
    }

    @Test
    void inDeadband_InputOutsideWindow_ReturnsFalse() {
        assertFalse(Deadband.inDeadband(1.5, 1.0));
        assertFalse(Deadband.inDeadband(-1.5, 1.0));
    }

    @Test
    void inDeadband_InputOnWindowBoundary_ReturnsTrue() {
        assertTrue(Deadband.inDeadband(1.0, 1.0));
        assertTrue(Deadband.inDeadband(-1.0, 1.0));
    }

    @Test
    void inDeadband_ZeroWindow_ReturnsTrue() {
        assertTrue(Deadband.inDeadband(0.0, 0.0));
    }

    @Test
    void inDeadband_NegativeWindow_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Deadband.inDeadband(0.5, -1.0));
    }
}
