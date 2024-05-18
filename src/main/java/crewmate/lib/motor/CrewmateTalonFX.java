package crewmate.lib.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import crewmate.lib.motor.MotorConfig.ControlType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import java.util.Optional;

public class CrewmateTalonFX implements CrewmateMotor {
    private TalonFX controller;
    private double setpoint;

    public CrewmateTalonFX(MotorConfig config) {
        controller = new TalonFX(config.canID);

        // Set motor type configuration
        controller.setInverted(config.reversed.orElse(false));
        config.currentLimit.ifPresent(this::setCurrentLimit);
        setBrakeMode(config.brakeMode.orElse(false));

        // PID settings
        config.p.ifPresent(this::setP);
        config.i.ifPresent(this::setI);
        config.d.ifPresent(this::setD);

        setpoint = 0;
        var driveConfig = new TalonFXConfiguration();
        driveConfig.CurrentLimits.SupplyCurrentLimit = 40.0;
        driveConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        controller.getConfigurator().apply(driveConfig);
    }

    public CrewmateTalonFX(int canID, MotorConfig.Type motorType) {
        this(MotorConfig.motorBasic(canID, motorType));
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
        return controller.getPosition().getValueAsDouble();
    }

    @Override
    public double getVelocity() {
        return controller.getVelocity().getValueAsDouble();
    }

    @Override
    public double getCurrent() {
        return controller.getSupplyCurrent().getValueAsDouble();
    }

    @Override
    public Controller getControllerType() {
        return Controller.TALONFX;
    }
    // TODO: Add support for CANCoder, and implmenet pid
    @Override
    public void setP(double p) {
    }

    @Override
    public void setI(double i) {

    }

    @Override
    public void setD(double d) {

    }

    @Override
    public double getP() {
        return 0;
    }

    @Override
    public double getI() {
        return 0;
    }

    @Override
    public double getD() {
        return 0;
    }

    @Override
    public void setSetpoint(double setpoint, ControlType controlType) {
        this.setpoint = setpoint;
    }

    @Override
    public double getSetpoint() {
        return setpoint;
    }

    @Override
    public void setCurrentLimit(int limit) {
        var driveConfig = new TalonFXConfiguration();
        driveConfig.CurrentLimits.SupplyCurrentLimit = limit;
        driveConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        controller.getConfigurator().apply(driveConfig);
    }

    @Override
    public void setBrakeMode(boolean enable) {
        var config = new MotorOutputConfigs();
        config.Inverted = InvertedValue.CounterClockwise_Positive;
        config.NeutralMode = enable ? NeutralModeValue.Brake : NeutralModeValue.Coast;
        controller.getConfigurator().apply(config);
    }
}
