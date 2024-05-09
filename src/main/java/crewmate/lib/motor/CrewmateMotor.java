package crewmate.lib.motor;

enum Controller{
    SIM,
    SPARKMAX,
    TALONFX
}

public interface CrewmateMotor {
    public void set(double throttle);
    public void setVoltage(double voltage);

    public double getPosition();
    public double getVelocity();
    public double getVoltage();
    public double getCurrent();

    public Controller getControllerType();
}