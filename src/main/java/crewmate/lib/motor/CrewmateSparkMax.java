package crewmate.lib.motor;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import crewmate.lib.motor.MotorConfig.ControlType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class CrewmateSparkMax implements CrewmateMotor {
  private final CANSparkMax controller;
  private final RelativeEncoder encoder;
  private final SparkPIDController pid;
  private final AtomicReference<Double> setpoint; // Thread-safe reference to the setpoint

  private volatile double lastPosition = 0.0;
  private volatile double lastVelocity = 0.0;
  private static final int DEFAULT_CAN_TIMEOUT_MS = 250;
  private static final double DEFAULT_VOLTAGE_COMPENSATION = 12.0;
  private static final int DEFAULT_CURRENT_LIMIT = 30;
  private static final double DEFAULT_RAMP_RATE = 0.2;
  private static final int DEFAULT_ENCODER_AVERAGE_DEPTH = 2;

  public CrewmateSparkMax(MotorConfig config) {
    controller = new CANSparkMax(
        config.canID,
        config.motorType == MotorConfig.Type.BRUSHLESS ? MotorType.kBrushless : MotorType.kBrushed);

    encoder = controller.getEncoder();
    pid = controller.getPIDController();
    setpoint = new AtomicReference<>(0.0);

    initializeController(config);
    configureController();
    applyOptimizedSettings();
  }
  //TODO: why not make motorType a bool to prevent unnnecessary imports
  public CrewmateSparkMax(int canID, MotorType motorType) {
    this(
        MotorConfig.motorBasic(
            canID,
            motorType == MotorType.kBrushless
                ? MotorConfig.Type.BRUSHLESS
                : MotorConfig.Type.BRUSHED));
  }

  private void initializeController(MotorConfig config) {
    controller.restoreFactoryDefaults();
    config.p.ifPresent(this::setP);
    config.i.ifPresent(this::setI);
    config.d.ifPresent(this::setD);
    config.currentLimit.ifPresent(this::setCurrentLimit);
    controller.setInverted(config.reversed.orElse(false));
    config.positionConversionFactor.ifPresent(encoder::setPositionConversionFactor);
    config.velocityConversionFactor.ifPresent(encoder::setVelocityConversionFactor);
    setBrakeMode(config.brakeMode.orElse(false));
  }

  private void configureController() {
    controller.setCANTimeout(DEFAULT_CAN_TIMEOUT_MS);
    controller.enableVoltageCompensation(DEFAULT_VOLTAGE_COMPENSATION);
    controller.setSmartCurrentLimit(DEFAULT_CURRENT_LIMIT);
    controller.setOpenLoopRampRate(DEFAULT_RAMP_RATE);
    controller.setClosedLoopRampRate(DEFAULT_RAMP_RATE);
  }

  private void applyOptimizedSettings() {
    encoder.setPosition(setpoint.get());
    encoder.setAverageDepth(DEFAULT_ENCODER_AVERAGE_DEPTH);
    controller.setCANTimeout(0);
    controller.burnFlash();
  }

  public void setPID(double p, double i, double d) {
    pid.setP(p);
    pid.setI(i);
    pid.setD(d);
  }

  @Override
  public void set(double throttle) {
    controller.set(throttle);
  }

  @Override
  public void setVoltage(double voltage) {
    controller.setVoltage(voltage);
  }

  @Override
  public double getPosition() {
    lastPosition = encoder.getPosition();
    return lastPosition;
  }

  @Override
  public double getVelocity() {
    lastVelocity = encoder.getVelocity();
    return lastVelocity;
  }

  @Override
  public double getCurrent() {
    return controller.getOutputCurrent();
  }

  @Override
  public Controller getControllerType() {
    return Controller.SPARKMAX;
  }

  public void setP(Optional<Double> p) {
    if (p.isPresent()) {
      this.setP(p.get());
    }
  }

  public void setI(Optional<Double> i) {
    if (i.isPresent()) {
      this.setI(i.get());
    }
  }

  public void setD(Optional<Double> d) {
    if (d.isPresent()) {
      this.setD(d.get());
    }
  }

  @Override
  public void setP(double p) {
    pid.setP(p);
  }

  @Override
  public void setI(double i) {
    pid.setI(i);
  }

  @Override
  public void setD(double d) {
    pid.setD(d);
  }

  public void setInverted(Optional<Boolean> inverted) {
    if (inverted.isEmpty()) {
      return;
    }
    controller.setInverted(inverted.get());
  }

  @Override
  public double getP() {
    return pid.getP();
  }

  @Override
  public double getI() {
    return pid.getI();
  }

  @Override
  public double getD() {
    return pid.getD();
  }

  @Override
  public void setSetpoint(double setpoint, ControlType controlType) {
    this.setpoint.set(setpoint);
    var sparkControlType = switch (controlType) {
      case POSITION -> com.revrobotics.CANSparkBase.ControlType.kPosition;
      case VELOCITY -> com.revrobotics.CANSparkBase.ControlType.kVelocity;
      case CURRENT -> com.revrobotics.CANSparkBase.ControlType.kCurrent;
      case DUTYCYCLE -> com.revrobotics.CANSparkBase.ControlType.kDutyCycle;
    };
    pid.setReference(setpoint, sparkControlType);
  }

  @Override
  public double getSetpoint() {
    return setpoint.get();
  }

  @Override
  public void setCurrentLimit(int limit) {
    controller.setSmartCurrentLimit(limit);
  }

  public void setCurrentLimit(Optional<Integer> limit) {
    if (limit.isPresent()) {
      this.setCurrentLimit(limit.get());
    }
  }

  @Override
  public void setBrakeMode(boolean brakeMode) {
    controller.setIdleMode(brakeMode ? IdleMode.kBrake : IdleMode.kCoast);
  }
}
