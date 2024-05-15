package crewmate.lib.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandBooleanTest {

    // @Test
    // void periodic_OnTrueAndOnFalseRunnables_ExecutesCorrectly() {
    //     boolean[] flags = {false, false};
    //     CommandBoolean cb = new CommandBoolean(() -> flags[0] = true, () -> flags[1] = true);
        
    //     cb.periodic(true);
    //     assertTrue(flags[0]);
    //     assertFalse(flags[1]);
        
    //     flags[0] = false;
    //     cb.periodic(false);
    //     assertFalse(flags[0]); 
    //     assertTrue(flags[1]);
    // }

    // @Test
    // void periodic_SingleOnEitherRunnable_ExecutesCorrectly() {
    //     boolean[] flag = {false};
    //     CommandBoolean cb = new CommandBoolean(() -> flag[0] = true);

    //     cb.periodic(true);
    //     assertTrue(flag[0]);

    //     flag[0] = false;
    //     cb.periodic(false);  
    //     assertTrue(flag[0]);
    // }

    // @Test
    // void periodic_NoRedundantRunnable_ExecutesCorrectly() {
    //     boolean[] flag = {false};
    //     CommandBoolean cb = new CommandBoolean(() -> flag[0] = true, () -> {});

    //     cb.periodic(true);
    //     assertTrue(flag[0]);

    //     flag[0] = false;
    //     cb.periodic(true);
    //     assertFalse(flag[0]);
    // }
}
