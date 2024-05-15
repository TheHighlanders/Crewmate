package crewmate.lib.motor;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import crewmate.lib.motor.MotorConfig.ControlType;
import java.util.Optional;

public class CrewmateSparkMax implements CrewmateMotor {
  private CANSparkMax controller;
  private RelativeEncoder encoder;
  // TODO: Add Alternate and Absolute Encoder support
  private SparkPIDController pid;
  private double setpoint;

  public CrewmateSparkMax(int canID, MotorType motorType) {
    controller = new CANSparkMax(canID, motorType);
    encoder = controller.getEncoder();

    pid = controller.getPIDController();
  }

  public CrewmateSparkMax(MotorConfig config) {
    switch (config.motorType) {
      case BRUSHLESS:
        controller = new CANSparkMax(config.canID, MotorType.kBrushless);
        break;
      case BRUSHED:
        controller = new CANSparkMax(config.canID, MotorType.kBrushed);
        break;
    }

    encoder = controller.getEncoder();
    pid = controller.getPIDController();

    setP(config.p);
    setI(config.i);
    setD(config.d);

    setCurrentLimit(config.currentLimit);

    setInverted(config.reversed);
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
  public void setCurrentLimit(double limit) {
    controller.setSmartCurrentLimit((int) limit);
  }

  public void setCurrentLimit(Optional<Double> limit) {
    if (limit.isPresent()) {
      this.setCurrentLimit(limit.get());
    }
  }
}
