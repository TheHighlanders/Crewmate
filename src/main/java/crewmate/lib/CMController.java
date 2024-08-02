package crewmate.lib;

import edu.wpi.first.wpilibj.XboxController;

import java.util.Map;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.HashMap;

/**
 * A version of {@link XboxController} with {@link Trigger} factories for
 * command-based.
 *
 * @see XboxController
 */
@SuppressWarnings("MethodName")
public class CMController extends CommandGenericHID {
  private final XboxController m_hid;
  private final Map<String, Trigger> triggerMap;

  public static enum TriggerType {
    ON_TRUE,
    ON_FALSE,
    WHILE_TRUE,
    WHILE_FALSE,
    TOGGLE_ON_TRUE,
    TOGGLE_ON_FALSE
  }

  

  /**
   * Construct an instance of a controller.
   *
   * @param port The port index on the Driver Station that the controller is
   *             plugged into.
   */
  public CMController(int port) {
    super(port);
    m_hid = new XboxController(port);
    triggerMap = new HashMap<String, Trigger>();
  }

  /**
   * Map triggers to specific names
   */
  public void mapTrigger(String triggerName, Trigger trigger) {
    triggerMap.put(triggerName, trigger);
  }

  /**
   * Map triggers to specific names using a map
   */
  public void mapTriggers(Map<String, Trigger> triggers) {
    triggerMap.putAll(triggers);
  }

  /**
   * Map triggers to specific names using an array of objects
   */
  public void mapTriggers(Object[][] mappings) {
    for (Object[] mapping : mappings) {
      if (mapping.length == 2 && mapping[0] instanceof String && mapping[1] instanceof Trigger) {
        String triggerName = (String) mapping[0];
        Trigger trigger = (Trigger) mapping[1];
        triggerMap.put(triggerName, trigger);
      }
    }
  }

  /**
   * Getter for the mapped triggers
   */
  public Trigger getTrigger(String triggerName) {
    return triggerMap.get(triggerName);
  }

  /**
   * Binds a command to a named trigger with a specified trigger type.
   * 
   * @param triggerName The name of the trigger to bind to.
   * @param command     The command to bind.
   * @param triggerType The type of trigger binding to use.
   */
  public void bind(String triggerName, Command command, TriggerType triggerType) {
    Trigger trigger = getTrigger(triggerName);
    if (trigger != null) {
      switch (triggerType) {
        case ON_TRUE:
          trigger.onTrue(command);
          break;
        case ON_FALSE:
          trigger.onFalse(command);
          break;
        case WHILE_TRUE:
          trigger.whileTrue(command);
          break;
        case WHILE_FALSE:
          trigger.whileFalse(command);
          break;
        case TOGGLE_ON_TRUE:
          trigger.toggleOnTrue(command);
          break;
        case TOGGLE_ON_FALSE:
          trigger.toggleOnFalse(command);
          break;
      }
    } else {
      System.out.println("Warning: Trigger '" + triggerName + "' not found.");
    }
  }

  /**
   * Binds a command to a named trigger with ON_TRUE as the default trigger type.
   * 
   * @param triggerName The name of the trigger to bind to.
   * @param command     The command to bind.
   */
  public void bind(String triggerName, Command command) {
    bind(triggerName, command, TriggerType.ON_TRUE);
  }

  /**
   * Get the underlying GenericHID object.
   *
   * @return the wrapped GenericHID object
   */
  @Override
  public XboxController getHID() {
    return m_hid;
  }


  /**
   * Get the trigger map
   *
   * @return the trigger map
   */
  public Map<String, Trigger> getTriggerMap() {
    return triggerMap;
  }

  /**
   * Set the rumble output for the underlying HID. The DS currently supports 2
   * rumble values,
   * left rumble and
   * right rumble.
   *
   * @param type  Which rumble value to set
   * @param value The normalized value (0 to 1) to set the rumble to
   */
  public void setRumble(RumbleType type, double value) {
    m_hid.setRumble(type, value);
  }

  public Trigger getButtonTrigger(XboxButton button, EventLoop loop) {
    switch (button) {
      case A:
        return m_hid.a(loop).castTo(Trigger::new);
      case B:
        return m_hid.b(loop).castTo(Trigger::new);
      case X:
        return m_hid.x(loop).castTo(Trigger::new);
      case Y:
        return m_hid.y(loop).castTo(Trigger::new);
      case LEFT_BUMPER:
        return m_hid.leftBumper(loop).castTo(Trigger::new);
      case RIGHT_BUMPER:
        return m_hid.rightBumper(loop).castTo(Trigger::new);
      case BACK:
        return m_hid.back(loop).castTo(Trigger::new);
      case START:
        return m_hid.start(loop).castTo(Trigger::new);
      case LEFT_STICK:
        return m_hid.leftStick(loop).castTo(Trigger::new);
      case RIGHT_STICK:
        return m_hid.rightStick(loop).castTo(Trigger::new);
      default:
        throw new IllegalArgumentException("Unknown button: " + button);
    }
  }

  public Trigger getButtonTrigger(XboxButton button, double threshold, EventLoop loop) {
    switch (button) {
      case LEFT_TRIGGER:
        return m_hid.leftTrigger(threshold, loop).castTo(Trigger::new);
      case RIGHT_TRIGGER:
        return m_hid.rightTrigger(threshold, loop).castTo(Trigger::new);
      default:
        throw new IllegalArgumentException("Unknown button: " + button);
    }
  }

  public Trigger getButtonTrigger(XboxButton button, double threshold) {
    return getButtonTrigger(button, threshold, CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger getButtonTrigger(XboxButton button) {
    return getButtonTrigger(button, CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  /**
   * Constructs a Trigger instance around the A button's digital signal.
   *
   * @return a Trigger instance representing the A button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #a(EventLoop)
   */
  public Trigger a() {
    return getButtonTrigger(XboxButton.A);
  }

  /**
   * Constructs a Trigger instance around the A button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the A button's dmake igital signal
   *         attached
   *         to the given loop.
   */
  public Trigger a(EventLoop loop) {
    return getButtonTrigger(XboxButton.A, loop);
  }

  /**
   * Constructs a Trigger instance around the B button's digital signal.
   *
   * @return a Trigger instance representing the B button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #b(EventLoop)
   */
  public Trigger b() {
    return getButtonTrigger(XboxButton.B);
  }

  /**
   * Constructs a Trigger instance around the B button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the B button's digital signal
   *         attached
   *         to the given loop.
   */
  public Trigger b(EventLoop loop) {
    return getButtonTrigger(XboxButton.B, loop);
  }

  /**
   * Constructs a Trigger instance around the X button's digital signal.
   *
   * @return a Trigger instance representing the X button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #x(EventLoop)
   */
  public Trigger x() {
    return getButtonTrigger(XboxButton.X);
  }

  /**
   * Constructs a Trigger instance around the X button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the X button's digital signal
   *         attached
   *         to the given loop.
   */
  public Trigger x(EventLoop loop) {
    return getButtonTrigger(XboxButton.X, loop);
  }

  /**
   * Constructs a Trigger instance around the Y button's digital signal.
   *
   * @return a Trigger instance representing the Y button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #y(EventLoop)
   */
  public Trigger y() {
    return getButtonTrigger(XboxButton.Y);
  }

  /**
   * Constructs a Trigger instance around the Y button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the Y button's digital signal
   *         attached
   *         to the given loop.
   */
  public Trigger y(EventLoop loop) {
    return getButtonTrigger(XboxButton.Y, loop);
  }

  /**
   * Constructs a Trigger instance around the left bumper button's digital signal.
   *
   * @return a Trigger instance representing the left bumper button's digital
   *         signal attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #leftBumper(EventLoop)
   */
  public Trigger leftBumper() {
    return getButtonTrigger(XboxButton.LEFT_BUMPER);
  }

  /**
   * Constructs a Trigger instance around the left bumper button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the left bumper button's digital
   *         signal attached
   *         to the given loop.
   */
  public Trigger leftBumper(EventLoop loop) {
    return getButtonTrigger(XboxButton.LEFT_BUMPER, loop);
  }

  /**
   * Constructs a Trigger instance around the right bumper button's digital
   * signal.
   *
   * @return a Trigger instance representing the right bumper button's digital
   *         signal attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #rightBumper(EventLoop)
   */
  public Trigger rightBumper() {
    return getButtonTrigger(XboxButton.RIGHT_BUMPER);
  }

  /**
   * Constructs a Trigger instance around the right bumper button's digital
   * signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the right bumper button's digital
   *         signal attached
   *         to the given loop.
   */
  public Trigger rightBumper(EventLoop loop) {
    return getButtonTrigger(XboxButton.RIGHT_BUMPER, loop);
  }

  /**
   * Constructs a Trigger instance around the back button's digital signal.
   *
   * @return a Trigger instance representing the back button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #back(EventLoop)
   */
  public Trigger back() {
    return getButtonTrigger(XboxButton.BACK);
  }

  /**
   * Constructs a Trigger instance around the back button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the back button's digital signal
   *         attached
   *         to the given loop.
   */
  public Trigger back(EventLoop loop) {
    return getButtonTrigger(XboxButton.BACK, loop);
  }

  /**
   * Constructs a Trigger instance around the start button's digital signal.
   *
   * @return a Trigger instance representing the start button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #start(EventLoop)
   */
  public Trigger start() {
    return getButtonTrigger(XboxButton.START);
  }

  /**
   * Constructs a Trigger instance around the start button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the start button's digital signal
   *         attached
   *         to the given loop.
   */
  public Trigger start(EventLoop loop) {
    return getButtonTrigger(XboxButton.START, loop);
  }

  /**
   * Constructs a Trigger instance around the left stick button's digital signal.
   *
   * @return a Trigger instance representing the left stick button's digital
   *         signal attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #leftStick(EventLoop)
   */
  public Trigger leftStick() {
    return getButtonTrigger(XboxButton.LEFT_STICK);
  }

  /**
   * Constructs a Trigger instance around the left stick button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the left stick button's digital
   *         signal attached
   *         to the given loop.
   */
  public Trigger leftStick(EventLoop loop) {
    return getButtonTrigger(XboxButton.LEFT_STICK, loop);
  }

  /**
   * Constructs a Trigger instance around the right stick button's digital signal.
   *
   * @return a Trigger instance representing the right stick button's digital
   *         signal attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #rightStick(EventLoop)
   */
  public Trigger rightStick() {
    return getButtonTrigger(XboxButton.RIGHT_STICK);
  }

  /**
   * Constructs a Trigger instance around the right stick button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the right stick button's digital
   *         signal attached
   *         to the given loop.
   */
  public Trigger rightStick(EventLoop loop) {
    return getButtonTrigger(XboxButton.RIGHT_STICK, loop);
  }

  /**
   * Constructs a Trigger instance around the axis value of the left trigger. The
   * returned
   * trigger will be true when the axis value is greater than {@code threshold}.
   *
   * @param threshold the minimum axis value for the returned {@link Trigger} to
   *                  be true. This value
   *                  should be in the range [0, 1] where 0 is the unpressed state
   *                  of the axis.
   * @param loop      the event loop instance to attach the Trigger to.
   * @return a Trigger instance that is true when the left trigger's axis exceeds
   *         the provided
   *         threshold, attached to the given event loop
   */
  public Trigger leftTrigger(double threshold, EventLoop loop) {
    return getButtonTrigger(XboxButton.LEFT_TRIGGER, threshold, loop);
  }

  /**
   * Constructs a Trigger instance around the axis value of the left trigger. The
   * returned
   * trigger will be true when the axis value is greater than {@code threshold}.
   *
   * @param threshold the minimum axis value for the returned {@link Trigger} to
   *                  be true. This value
   *                  should be in the range [0, 1] where 0 is the unpressed state
   *                  of the axis.
   * @return a Trigger instance that is true when the left trigger's axis exceeds
   *         the provided
   *         threshold, attached to the
   *         {@link CommandScheduler#getDefaultButtonLoop() default scheduler
   *         button loop}.
   */
  public Trigger leftTrigger(double threshold) {
    return getButtonTrigger(XboxButton.LEFT_TRIGGER, threshold);
  }

  /**
   * Constructs a Trigger instance around the axis value of the left trigger. The
   * returned trigger
   * will be true when the axis value is greater than 0.5.
   *
   * @return a Trigger instance that is true when the left trigger's axis exceeds
   *         0.5, attached to
   *         the {@link CommandScheduler#getDefaultButtonLoop() default scheduler
   *         button loop}.
   */
  public Trigger leftTrigger() {
    return leftTrigger(0.5);
  }

  /**
   * Constructs a Trigger instance around the axis value of the right trigger. The
   * returned
   * trigger will be true when the axis value is greater than {@code threshold}.
   *
   * @param threshold the minimum axis value for the returned {@link Trigger} to
   *                  be true. This value
   *                  should be in the range [0, 1] where 0 is the unpressed state
   *                  of the axis.
   * @param loop      the event loop instance to attach the Trigger to.
   * @return a Trigger instance that is true when the right trigger's axis exceeds
   *         the provided
   *         threshold, attached to the given event loop
   */
  public Trigger rightTrigger(double threshold, EventLoop loop) {
    return getButtonTrigger(XboxButton.RIGHT_TRIGGER, threshold, loop);
  }

  /**
   * Constructs a Trigger instance around the axis value of the right trigger. The
   * returned
   * trigger will be true when the axis value is greater than {@code threshold}.
   *
   * @param threshold the minimum axis value for the returned {@link Trigger} to
   *                  be true. This value
   *                  should be in the range [0, 1] where 0 is the unpressed state
   *                  of the axis.
   * @return a Trigger instance that is true when the right trigger's axis exceeds
   *         the provided
   *         threshold, attached to the
   *         {@link CommandScheduler#getDefaultButtonLoop() default scheduler
   *         button loop}.
   */
  public Trigger rightTrigger(double threshold) {
    return getButtonTrigger(XboxButton.RIGHT_TRIGGER, threshold);
  }

  /**
   * Constructs a Trigger instance around the axis value of the right trigger. The
   * returned trigger
   * will be true when the axis value is greater than 0.5.
   *
   * @return a Trigger instance that is true when the right trigger's axis exceeds
   *         0.5, attached to
   *         the {@link CommandScheduler#getDefaultButtonLoop() default scheduler
   *         button loop}.
   */
  public Trigger rightTrigger() {
    return rightTrigger(0.5);
  }

  /**
   * Get the X axis value of left side of the controller.
   *
   * @return The axis value.
   */
  public double getLeftX() {
    return m_hid.getLeftX();
  }

  /**
   * Get the X axis value of right side of the controller.
   *
   * @return The axis value.
   */
  public double getRightX() {
    return m_hid.getRightX();
  }

  /**
   * Get the Y axis value of left side of the controller.
   *
   * @return The axis value.
   */
  public double getLeftY() {
    return m_hid.getLeftY();
  }

  /**
   * Get the Y axis value of right side of the controller.
   *
   * @return The axis value.
   */
  public double getRightY() {
    return m_hid.getRightY();
  }

  /**
   * Get the left trigger axis value of the controller. Note that this axis is
   * bound to the
   * range of [0, 1] as opposed to the usual [-1, 1].
   *
   * @return The axis value.
   */
  public double getLeftTriggerAxis() {
    return m_hid.getLeftTriggerAxis();
  }

  /**
   * Get the right trigger axis value of the controller. Note that this axis is
   * bound to the
   * range of [0, 1] as opposed to the usual [-1, 1].
   *
   * @return The axis value.
   */
  public double getRightTriggerAxis() {
    return m_hid.getRightTriggerAxis();
  }
}
