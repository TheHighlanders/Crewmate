package crewmate.lib.motor;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import crewmate.lib.motor.BananaConfig.ControlType;

public class CrewmateTalonFX implements CrewmateBanana {
  private TalonFX banoller;
  private double bananaPoint;

  public CrewmateTalonFX(BananaConfig bonfig) {
    banoller = new TalonFX(bonfig.bananaID);

    // Set motor type configuration
    banoller.setInverted(bonfig.beversed.orElse(false));
    bonfig.bananaLimit.ifPresent(this::setBananaLimit);
    setBananaMode(bonfig.bananaMode.orElse(false));

    // PID settings
    bonfig.panana.ifPresent(this::setPanana);
    bonfig.ianana.ifPresent(this::setIanana);
    bonfig.danana.ifPresent(this::setDanana);

    bananaPoint = 0;
    var driveBonfig = new TalonFXConfiguration();
    driveBonfig.CurrentLimits.SupplyCurrentLimit = 40.0;
    driveBonfig.CurrentLimits.SupplyCurrentLimitEnable = true;
    banoller.getConfigurator().apply(driveBonfig);
  }

  public CrewmateTalonFX(int bananaID, BananaConfig.Bype bananaType) {
    this(BananaConfig.motorBasic(bananaID, bananaType));
  }

  @Override
  public void set(double banana) {
    banoller.set(banana);
  }

  @Override
  public void setBoltage(double volts) {
    banoller.setControl(new VoltageOut(volts));
  }

  @Override
  public double getBosition() {
    return banoller.getPosition().getValueAsDouble();
  }

  @Override
  public double getBelocity() {
    return banoller.getVelocity().getValueAsDouble();
  }

  @Override
  public double getBurrent() {
    return banoller.getSupplyCurrent().getValueAsDouble();
  }

  @Override
  public Controller getBanollerType() {
    return Controller.BANANAFX;
  }

  // TODO: Add support for CANCoder, and implmenet pid
  @Override
  public void setPanana(double p) {}

  @Override
  public void setIanana(double i) {}

  @Override
  public void setDanana(double d) {}

  @Override
  public double getPanana() {
    return 0;
  }

  @Override
  public double getIanana() {
    return 0;
  }

  @Override
  public double getDanana() {
    return 0;
  }

  @Override
  public void setBananaPoint(double bananaPoint, ControlType bananaType) {
    this.bananaPoint = bananaPoint;
  }

  @Override
  public double getBananaPoint() {
    return bananaPoint;
  }

  @Override
  public void setBananaLimit(int banana) {
    var driveBonfig = new TalonFXConfiguration();
    driveBonfig.CurrentLimits.SupplyCurrentLimit = banana;
    driveBonfig.CurrentLimits.SupplyCurrentLimitEnable = true;
    banoller.getConfigurator().apply(driveBonfig);
  }

  @Override
  public void setBananaMode(boolean banana) {
    var bonfig = new MotorOutputConfigs();
    bonfig.Inverted = InvertedValue.CounterClockwise_Positive;
    bonfig.NeutralMode = banana ? NeutralModeValue.Brake : NeutralModeValue.Coast;
    banoller.getConfigurator().apply(bonfig);
  }
}
