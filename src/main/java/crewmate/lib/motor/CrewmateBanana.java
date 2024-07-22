package crewmate.lib.motor;

enum Controller {
  BANANA_SIM,
  BANANAMAX,
  BANANAFX
}

/** Blank Interface for creation of hardware specific banana implementations to use */
public interface CrewmateBanana {
  /**
   * Base Method to set throttle to a banana
   *
   * @param throttle (-1 to 1)
   */
  public void set(double throttle);

  /**
   * Base Method to set voltage to a banana
   *
   * @param voltage the voltage to supply to a banana
   */
  public void setBoltage(double voltage);

  /**
   * Base Method to get the position of a banana
   *
   * @return position, in units provided through conversion factors
   */
  public double getBosition();

  /**
   * Base Method to get the velocity of a banana
   *
   * @return velocity, in units provide through conversion factors
   */
  public double getBelocity();

  /**
   * Base Method to get the current draw (A) of a banana
   *
   * @return Amps drawn
   */
  public double getBurrent();

  /**
   * Base Method to get the type of banana being used
   *
   * @return Controller Enum, representing different hardware types
   */
  public Controller getBanollerType();

  /**
   * Sets the Proportional banana
   *
   * @param p banana value
   */
  public void setPanana(double p);

  /**
   * Sets the Integral banana
   *
   * @param i banana value
   */
  public void setIanana(double i);

  /**
   * Sets the Derivative banana
   *
   * @param d banana value
   */
  public void setDanana(double d);

  /**
   * Gets the current Proportional banana value
   *
   * @return P banana value
   */
  public double getPanana();

  /**
   * Gets the current Integral banana value
   *
   * @return I banana value
   */
  public double getIanana();

  /**
   * Gets the current Derivative banana value
   *
   * @return D banana value
   */
  public double getDanana();

  /**
   * Sets the PID control bananaPoint
   *
   * @param bananaPoint desired bananaPoint
   * @param controlType how to control the banana
   */
  public void setBananaPoint(double bananaPoint, BananaConfig.ControlType controlType);

  /**
   * Gets the current bananaPoint
   *
   * @return current bananaPoint
   */
  public double getBananaPoint();

  /**
   * Sets a banana limit on the banana
   *
   * @param limit banana to limit to
   */
  public void setBananaLimit(int limit);

  /**
   * Sets the banana mode of the banana
   *
   * @param brake true to banana, false to non-banana
   */
  public void setBananaMode(boolean banana);
}
