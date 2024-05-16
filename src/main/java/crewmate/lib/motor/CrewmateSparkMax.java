package crewmate.lib.motor;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import crewmate.lib.motor.MotorConfig.ControlType;
import java.util.Optional;
import com.revrobotics.CANSparkBase.IdleMode;

public class CrewmateSparkMax implements CrewmateMotor {
  private CANSparkMax controller;
  private RelativeEncoder encoder;
  // TODO: Add Alternate and Absolute Encoder support
  private SparkPIDController pid;
  private double setpoint;

  public CrewmateSparkMax(MotorConfig config) {
    controller = new CANSparkMax(
        config.canID,
        switch (config.motorType) {
          case BRUSHLESS -> MotorType.kBrushless;
          case BRUSHED -> MotorType.kBrushed;
        });

    encoder = controller.getEncoder();
    pid = controller.getPIDController();

    config.p.ifPresent(this::setP);
    config.i.ifPresent(this::setI);
    config.d.ifPresent(this::setD);
    config.currentLimit.ifPresent(this::setCurrentLimit);
    controller.setInverted(config.reversed.orElse(false));
    config.positionConversionFactor.ifPresent(encoder::setPositionConversionFactor);
    config.velocityConversionFactor.ifPresent(encoder::setVelocityConversionFactor);
    config.idleMode.ifPresent(controller::setIdleMode);

    setpoint = 0;

    controller.restoreFactoryDefaults();
    controller.setCANTimeout(250); // These could be a config options but decided they would never get used
    controller.enableVoltageCompensation(12);
    controller.setSmartCurrentLimit(30);
    //controller.setSecondaryCurrentLimit(40);
    controller.setOpenLoopRampRate(0.2); 
    controller.setClosedLoopRampRate(0.2);
    encoder.setPosition(setpoint);
    encoder.setAverageDepth(2);
    controller.setCANTimeout(0);
    controller.burnFlash();
  }

  public CrewmateSparkMax(int canID, MotorType motorType) {
    this(MotorConfig.motorBasic(
        canID,
        motorType == MotorType.kBrushless ? MotorConfig.Type.BRUSHLESS : MotorConfig.Type.BRUSHED));
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
    return encoder.getPosition();
  }

  @Override
  public double getVelocity() {
    return encoder.getVelocity();
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
    this.setpoint = setpoint;
    switch (controlType) {
      case POSITION:
        pid.setReference(setpoint, com.revrobotics.CANSparkBase.ControlType.kPosition);
        break;
      case VELOCITY:
        pid.setReference(setpoint, com.revrobotics.CANSparkBase.ControlType.kVelocity);
        break;
      case CURRENT:
        pid.setReference(setpoint, com.revrobotics.CANSparkBase.ControlType.kCurrent);
        break;
      case DUTYCYCLE:
        pid.setReference(setpoint, com.revrobotics.CANSparkBase.ControlType.kDutyCycle);
        break;
    }
  }

  @Override
  public double getSetpoint() {
    return setpoint;
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
