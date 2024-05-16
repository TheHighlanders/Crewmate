package crewmate.lib.motor;

import com.revrobotics.CANSparkBase.IdleMode;
import java.util.Optional;

public class MotorConfig {
  public enum Type {
    BRUSHED,
    BRUSHLESS
  }

  public enum ControlType {
    POSITION,
    VELOCITY,
    CURRENT,
    DUTYCYCLE
  }

  int canID;
  Type motorType;
  Optional<Integer> currentLimit;
  Optional<Double> p;
  Optional<Double> i;
  Optional<Double> d;
  Optional<Boolean> reversed;
  Optional<Double> positionConversionFactor;
  Optional<Double> velocityConversionFactor;
  Optional<IdleMode> idleMode;

  public MotorConfig(
      int canID,
      Type motorType,
      Optional<Integer> currentLimit,
      Optional<Double> p,
      Optional<Double> i,
      Optional<Double> d,
      Optional<Boolean> reversed,
      Optional<Double> positionConversionFactor,
      Optional<Double> velocityConversionFactor,
      Optional<IdleMode> idleMode) {
    this.canID = canID;
    this.motorType = motorType;
    this.currentLimit = currentLimit;
    this.p = p;
    this.i = i;
    this.d = d;
    this.reversed = reversed;
    this.positionConversionFactor = positionConversionFactor;
    this.velocityConversionFactor = velocityConversionFactor;
    this.idleMode = idleMode;
  }

  /**
   * Creates a new MotorConfig for a motor
   *
   * @param canID Can ID of the Motor
   * @param motorType Brushed or Brushless
   * @return MotorConfig
   */
  public static MotorConfig motorBasic(int canID, Type motorType) {
    return new MotorConfig(
        canID,
        motorType,
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.of(IdleMode.kCoast));
  }

  /**
   * Creates a new MotorConfig for a motor with an attached gearbox Velocity will be in rpm
   *
   * @param canID Can ID of the Motor
   * @param motorType Brushed or Brushless
   * @param gearboxRatio Ratio of attached gearboxes (rotation/rotation)
   * @return MotorConfig
   */
  public static MotorConfig motorBasicGearbox(int canID, Type motorType, double gearboxRatio) {
    return new MotorConfig(
        canID,
        motorType,
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.of(gearboxRatio),
        Optional.of(gearboxRatio / 60.0d),
        Optional.of(IdleMode.kCoast));
  }

  public static MotorConfig motorPID(
      int canID, Type motorType, double gearboxRatio, double p, double i, double d) {
    return new MotorConfig(
        canID,
        motorType,
        Optional.empty(),
        Optional.of(p),
        Optional.of(i),
        Optional.of(d),
        Optional.empty(),
        Optional.of(gearboxRatio),
        Optional.of(gearboxRatio / 60.0d),
        Optional.of(IdleMode.kCoast));
  }

  public MotorConfig setReversed(boolean reversed) {
    this.reversed = Optional.of(reversed);
    return this;
  }

  public MotorConfig setCurrentLimit(int currentLimit) {
    this.currentLimit = Optional.of(currentLimit);
    return this;
  }
}
