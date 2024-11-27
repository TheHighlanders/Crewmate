package crewmate.lib.motor;

enum Controller {
  SIM,
  SPARKMAX,
  TALONFX
}

/** Blank Interface for creation of hardware specific motor implementations to use */
public interface CrewmateMotor {
  /**
   * Base Method to set throttle to a motor
   *
   * @param throttle (-1 to 1)
   */
  public void set(double throttle);

  /**
   * Base Method to set voltage to a motor
   *
   * @param voltage the voltage to supply to a motor
   */
  public void setVoltage(double voltage);

  /**
   * Base Method to get the position of a motor
   *
   * @return position, in units provided through conversion factors
   */
  public double getPosition();

  /**
   * Base Method to get the velocity of a motor
   *
   * @return velocity, in units provide through conversion factors
   */
  public double getVelocity();

  /**
   * Base Method to get the current draw (A) of a motor
   *
   * @return Amps drawn
   */
  public double getCurrent();

  /**
   * Base Method to get the type of motor being used
   *
   * @return Controller Enum, representing different hardware types
   */
  public Controller getControllerType();

  /**
   * Sets the Proportional gain
   *
   * @param p gain value
   */
  public void setP(double p);

  /**
   * Sets the Integral gaim
   *
   * @param i gain value
   */
  public void setI(double i);

  /**
   * Sets the Derivative gain
   *
   * @param d gain value
   */
  public void setD(double d);

  /**
   * Gets the current Proportional gain value
   *
   * @return P gain value
   */
  public double getP();

  /**
   * Gets the current Integral gain value
   *
   * @return I gain value
   */
  public double getI();

  /**
   * Gets the current Derivative gain value
   *
   * @return D gain value
   */
  public double getD();

  /**
   * Sets the PID control setpoint
   *
   * @param setpoint desired setpoint
   * @param controlType how to control the motor
   */
  public void setSetpoint(double setpoint, MotorConfig.ControlType controlType);

  /**
   * Gets the current setpoint
   *
   * @return current setpoint
   */
  public double getSetpoint();

  /**
   * Sets a current limit on the motor
   *
   * @param limit current to limit to
   */
  public void setCurrentLimit(int limit);

  /**
   * Sets the brake mode of the motor
   *
   * @param brake true to brake, false to coast
   */
  public void setBrakeMode(boolean brake);
}
