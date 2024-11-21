package crewmate.lib.util;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Could Create a CrewmateCentral subsystem to control all crewmate functions
public class CommandBoolean extends SubsystemBase {
  boolean trigger = false;
  Runnable onTrue;
  Runnable onFalse;

  public CommandBoolean(Runnable onTrue, Runnable onFalse, boolean initState) {
    this.onTrue = onTrue;
    this.onFalse = onFalse;
    trigger = initState;
  }

  public void periodic() {
    
      if (trigger) {
        onTrue.run();
      } else {
        onFalse.run();
      }
      trigger = !trigger;
  }
}
