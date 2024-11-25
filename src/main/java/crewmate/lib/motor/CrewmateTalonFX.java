package crewmate.lib.motor;

import java.util.Optional;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import crewmate.lib.motor.MotorConfig.ControlType;
import java.util.concurrent.atomic.AtomicReference;

public class CrewmateTalonFX implements CrewmateMotor {
  private final TalonFX controller;
  private final AtomicReference<Double> setpoint; // Thread-safe reference to the setpoint

  private volatile double lastPosition = 0.0;
  private volatile double lastVelocity = 0.0;
  private static final double DEFAULT_CURRENT_LIMIT = 40.0;
  private TalonFXConfiguration config;

  public CrewmateTalonFX(MotorConfig config) {
    controller = new TalonFX(config.canID);
    setpoint = new AtomicReference<>(0.0);
    this.config = new TalonFXConfiguration();

    initializeController(config);
    configureController();
    applyOptimizedSettings();
  }

  private void initializeController(MotorConfig config) {
    controller.setInverted(config.reversed.orElse(false));
    config.currentLimit.ifPresent(this::setCurrentLimit);
    setBrakeMode(config.brakeMode.orElse(false));

    setPID(config.p, config.i, config.d);
  }

  private void configureController() {
    this.config.CurrentLimits.SupplyCurrentLimit = DEFAULT_CURRENT_LIMIT;
    this.config.CurrentLimits.SupplyCurrentLimitEnable = true;
  }

  private void applyOptimizedSettings() {
    controller.getConfigurator().apply(this.config);
  }

  // Reducde can bus operations vs setting each individually
  public void setPID(double p, double i, double d) {
    this.config.Slot0.kP = p;
    this.config.Slot0.kI = i;
    this.config.Slot0.kD = d;
    controller.getConfigurator().apply(this.config);
  }

  public void setPID(Optional<Double> p, Optional<Double> i, Optional<Double> d) {
    this.config.Slot0.kP = p.orElse(this.config.Slot0.kP);
    this.config.Slot0.kI = i.orElse(this.config.Slot0.kI);
    this.config.Slot0.kD = d.orElse(this.config.Slot0.kD);
    controller.getConfigurator().apply(this.config);
  }

  @Override
  public void set(double throttle) {
    controller.set(throttle);
  }

  @Override
  public void setVoltage(double volts) {
    controller.setControl(new VoltageOut(volts));
  }

  @Override
  public double getPosition() {
    lastPosition = controller.getPosition().getValueAsDouble();
    return lastPosition;
  }

  @Override
  public double getVelocity() {
    lastVelocity = controller.getVelocity().getValueAsDouble();
    return lastVelocity;
  }

  @Override
  public double getCurrent() {
    return controller.getSupplyCurrent().getValueAsDouble();
  }

  @Override
  public Controller getControllerType() {
    return Controller.TALONFX;
  }

  @Override
  public void setP(double p) {
    this.config.Slot0.kP = p;
    controller.getConfigurator().apply(this.config);
  }

  @Override
  public void setI(double i) {
    this.config.Slot0.kI = i;
    controller.getConfigurator().apply(this.config);
  }

  @Override
  public void setD(double d) {
    this.config.Slot0.kD = d;
    controller.getConfigurator().apply(this.config);
  }

  @Override
  public double getP() {
    return this.config.Slot0.kP;
  }

  @Override
  public double getI() {
    return this.config.Slot0.kI;
  }

  @Override
  public double getD() {
    return this.config.Slot0.kD;
  }

  @Override
  public void setSetpoint(double setpoint, ControlType controlType) {
    this.setpoint.set(setpoint);

    var request = switch (controlType) {
      case POSITION -> new com.ctre.phoenix6.controls.PositionDutyCycle(setpoint);
      case VELOCITY -> new com.ctre.phoenix6.controls.VelocityDutyCycle(setpoint);
      case CURRENT -> new com.ctre.phoenix6.controls.TorqueCurrentFOC(setpoint);
      case DUTYCYCLE -> new com.ctre.phoenix6.controls.DutyCycleOut(setpoint);
    };

    controller.setControl(request);
  }

  @Override
  public double getSetpoint() {
    return setpoint.get();
  }

  @Override
  public void setCurrentLimit(int limit) {
    this.config.CurrentLimits.SupplyCurrentLimit = limit;
    this.config.CurrentLimits.SupplyCurrentLimitEnable = true;
    controller.getConfigurator().apply(this.config);
  }

  @Override
  public void setBrakeMode(boolean enable) {
    var motorConfig = new MotorOutputConfigs();
    motorConfig.Inverted = InvertedValue.CounterClockwise_Positive;
    motorConfig.NeutralMode = enable ? NeutralModeValue.Brake : NeutralModeValue.Coast;
    controller.getConfigurator().apply(motorConfig);
  }
}
