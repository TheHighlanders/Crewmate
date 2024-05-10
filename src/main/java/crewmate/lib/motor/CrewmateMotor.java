package crewmate.lib.motor;

enum Controller{
    SIM,
    SPARKMAX,
    TALONFX
}

public interface CrewmateMotor {
    /** 
     * Base Method to set throttle to a motor
     * @param throttle
     */
    public void set(double throttle);
    /**
     * Base Method to set voltage to a motor
     * @param voltage
     */
    public void setVoltage(double voltage);

    /**
     * Base Method to get the position of a motor
     * @return position, in units provided through conversion factors
     */
    public double getPosition();
    
    /** 
     * Base Method to get the velocity of a motor
     * @return velocity, in units provide through conversion factors
    */
    public double getVelocity();

    /**
     * Base Method to get voltage of a motor
     * @return Voltage of a motor
     */
    public double getVoltage();

    /**
     * Base Method to get the current draw (A) of a motor
     * @return Amps drawn
     */
    public double getCurrent();

    /**
     * Base Method to get the type of motor being used
     * @return Controller Enum, representing different hardware types
     */
    public Controller getControllerType();
}