package crewmate.lib.util;

import crewmate.lib.Library.Constants;
import crewmate.lib.motor.CrewmateMotor;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.littletonrobotics.junction.networktables.LoggedDashboardNumber;

public class LoggedTunablePID {
  private static final String tableKey = "TunablePID";
  private final CrewmateMotor motor;
  private final String key;

  private boolean hasDefault = false;
  private double defaultP;
  private double defaultI;
  private double defaultD;

  private LoggedDashboardNumber kP;
  private LoggedDashboardNumber kI;
  private LoggedDashboardNumber kD;

  private double lastP;
  private double lastI;
  private double lastD;

  public LoggedTunablePID(CrewmateMotor motor, String dashboardKey) {
    this.motor = motor;
    this.key = tableKey + "/" + dashboardKey;
  }

  public LoggedTunablePID(
      CrewmateMotor motor, String dashboardKey, double defaultP, double defaultI, double defaultD) {
    this(motor, dashboardKey);
    initDefault(defaultP, defaultI, defaultD);
  }

  public void initDefault(double defaultP, double defaultI, double defaultD) {
    if (!hasDefault) {
      hasDefault = true;
      this.defaultP = defaultP;
      this.defaultI = defaultI;
      this.defaultD = defaultD;

      motor.setP(defaultP);
      motor.setI(defaultI);
      motor.setD(defaultD);

      if (Constants.tuningMode) {
        kP = new LoggedDashboardNumber(key + "/kP", defaultP);
        kI = new LoggedDashboardNumber(key + "/kI", defaultI);
        kD = new LoggedDashboardNumber(key + "/kD", defaultD);

        lastP = kP.get();
        lastI = kI.get();
        lastD = kD.get();
      }
    }
  }

  public boolean UpdateIfChanged() {
    if (!hasDefault) return false;

    if (Constants.tuningMode) {
      double currentP = kP.get();
      double currentI = kI.get();
      double currentD = kD.get();

      if (currentP != lastP || currentI != lastI || currentD != lastD) {
        updatePID();
        lastP = currentP;
        lastI = currentI;
        lastD = currentD;
        return true;
      }
    }
    return false;
  }

  public void updatePID() {
    if (!hasDefault) return;

    if (Constants.tuningMode) {
      motor.setP(kP.get());
      motor.setI(kI.get());
      motor.setD(kD.get());
    } else {
      motor.setP(defaultP);
      motor.setI(defaultI);
      motor.setD(defaultD);
    }
  }

  public void bindToScheduler() {
    CommandScheduler.getInstance()
        .onCommandExecute(
            (command) -> {
              this.UpdateIfChanged();
            });
  }

  public CrewmateMotor getMotor() {
    return motor;
  }
}
