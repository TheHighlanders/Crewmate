package crewmate.lib.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CommandBooleanTest {

  @Test
  void periodic_OnTrueAndOnFalseRunnables_ExecutesCorrectly() {
      boolean[] flags = {false, false};
      CommandBoolean cb = new CommandBoolean(() -> flags[0] = true, () -> flags[1] = true, true);

      cb.periodic();
      assertTrue(flags[0]);
      assertFalse(flags[1]);

      flags[0] = false;
      cb.periodic();
      assertFalse(flags[0]);
      assertTrue(flags[1]);
  }

  @Test
  void periodic_NoRedundantRunnable_ExecutesCorrectly() {
      boolean[] flag = {false};
      CommandBoolean cb = new CommandBoolean(() -> flag[0] = true, () -> {}, true);

      cb.periodic();
      assertTrue(flag[0]);

      flag[0] = false;
      cb.periodic();
      assertFalse(flag[0]);
  }
}
