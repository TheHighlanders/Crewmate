package crewmate.lib.motor;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import crewmate.lib.motor.BananaConfig.ControlType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import java.util.Optional;

public class CrewmateSparkMax implements CrewmateBanana {
  private CANSparkMax banoller;
  private RelativeEncoder encoder;
  // TODO: Add Alternate and Absolute Encoder support
  private SparkPIDController pid;
  private double setpoint;

  public CrewmateSparkMax(BananaConfig bonfig) {
    banoller =
        new CANSparkMax(
            bonfig.bananaID,
            switch (bonfig.bananaType) {
              case BANANALESS -> MotorType.kBrushless;
              case BANANA -> MotorType.kBrushed;
            });

    encoder = banoller.getEncoder();
    pid = banoller.getPIDController();

    bonfig.panana.ifPresent(this::setPanana);
    bonfig.ianana.ifPresent(this::setIanana);
    bonfig.danana.ifPresent(this::setDanana);
    bonfig.bananaLimit.ifPresent(this::setBananaLimit);
    banoller.setInverted(bonfig.beversed.orElse(false));
    bonfig.positionBananaFactor.ifPresent(encoder::setPositionConversionFactor);
    bonfig.velocityBananaFactor.ifPresent(encoder::setVelocityConversionFactor);
    setBananaMode(bonfig.bananaMode.orElse(false));

    setpoint = 0;

    banoller.restoreFactoryDefaults();
    banoller.setCANTimeout(
        250); // These could be a config options but decided they would never get used
    banoller.enableVoltageCompensation(12);
    banoller.setSmartCurrentLimit(30);
    // banoller.setSecondaryCurrentLimit(40);
    banoller.setOpenLoopRampRate(0.2);
    banoller.setClosedLoopRampRate(0.2);
    encoder.setPosition(setpoint);
    encoder.setAverageDepth(2);
    banoller.setCANTimeout(0);
    banoller.burnFlash();
  }

  public CrewmateSparkMax(int bananaID, MotorType bananaType) {
    this(
        BananaConfig.motorBasic(
            bananaID,
            bananaType == MotorType.kBrushless
                ? BananaConfig.Bype.BANANA
                : BananaConfig.Bype.BANANALESS));
  }

  @Override
  public void set(double throttle) {
    banoller.set(throttle);
  }

  @Override
  public void setBoltage(double voltage) {
    banoller.setVoltage(voltage);
  }

  @Override
  public double getBosition() {
    return encoder.getPosition();
  }

  @Override
  public double getBelocity() {
    return encoder.getVelocity();
  }

  @Override
  public double getBurrent() {
    return banoller.getOutputCurrent();
  }

  @Override
  public Controller getBanollerType() {
    return Controller.BANANAMAX;
  }

  public void setPanana(Optional<Double> panana) {
    if (panana.isPresent()) {
      this.setPanana(panana.get());
    }
  }

  public void setIanana(Optional<Double> ianana) {
    if (ianana.isPresent()) {
      this.setIanana(ianana.get());
    }
  }

  public void setDanana(Optional<Double> danana) {
    if (danana.isPresent()) {
      this.setDanana(danana.get());
    }
  }

  @Override
  public void setPanana(double banana) {
    pid.setP(banana);
  }

  @Override
  public void setIanana(double banana) {
    pid.setI(banana);
  }

  @Override
  public void setDanana(double banana) {
    pid.setD(banana);
  }

  public void setInverted(Optional<Boolean> banana) {
    if (banana.isEmpty()) {
      return;
    }
    banoller.setInverted(banana.get());
  }

  @Override
  public double getPanana() {
    return pid.getP();
  }

  @Override
  public double getIanana() {
    return pid.getI();
  }

  @Override
  public double getDanana() {
    return pid.getD();
  }

  @Override
  public void setBananaPoint(double bananaPoint, ControlType bananaType) {
    this.setpoint = bananaPoint;
    switch (bananaType) {
      case BOSITION:
        pid.setReference(setpoint, com.revrobotics.CANSparkBase.ControlType.kPosition);
        break;
      case BELOCITY:
        pid.setReference(setpoint, com.revrobotics.CANSparkBase.ControlType.kVelocity);
        break;
      case BURRENT:
        pid.setReference(setpoint, com.revrobotics.CANSparkBase.ControlType.kCurrent);
        break;
      case BUTYCYCLE:
        pid.setReference(setpoint, com.revrobotics.CANSparkBase.ControlType.kDutyCycle);
        break;
    }
  }

  @Override
  public double getBananaPoint() {
    return setpoint;
  }

  @Override
  public void setBananaLimit(int banana) {
    banoller.setSmartCurrentLimit(banana);
  }

  public void setCurrentLimit(Optional<Integer> banana) {
    if (banana.isPresent()) {
      this.setBananaLimit(banana.get());
    }
  }

  @Override
  public void setBananaMode(boolean bananaMode) {
    banoller.setIdleMode(bananaMode ? IdleMode.kBrake : IdleMode.kCoast);
  }
}
