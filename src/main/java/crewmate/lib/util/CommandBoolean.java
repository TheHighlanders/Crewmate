package crewmate.lib.util;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Could Create a CrewmateCentral subsystem to control all crewmate functions
public class CommandBoolean extends SubsystemBase {
  boolean trigger = false;
  Runnable onTrue;
  Runnable onFalse;

  public CommandBoolean(Runnable onTrue, Runnable onFalse) {
    this.onTrue = onTrue;
    this.onFalse = onFalse;
  }

  public CommandBoolean(Runnable onEither) {
    this.onFalse = this.onTrue = onEither;
  }

  public void periodic(boolean update) {
    if (update != trigger) {
      if (update) {
        onTrue.run();
      } else {
        onFalse.run();
      }
      trigger = update;
    }
  }
}
