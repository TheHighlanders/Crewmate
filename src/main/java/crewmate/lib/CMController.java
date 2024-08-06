package crewmate.lib;

import edu.wpi.first.wpilibj.XboxController;

import java.util.Map;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import java.util.HashMap;

/**
 * A version of {@link XboxController} with {@link DynamicTrigger} factories for
 * command-based.
 *
 * @see XboxController
 */
@SuppressWarnings("MethodName")
public class CMController extends CommandGenericHID {
  private final XboxController m_hid;
  private final Map<XboxButton, DynamicTrigger> triggerMap;

  /**
   * Construct an instance of a controller.
   *
   * @param port The port index on the Driver Station that the controller is
   *             plugged into.
   */
  public CMController(int port) {
    super(port);
    m_hid = new XboxController(port);
    triggerMap = new HashMap<XboxButton, DynamicTrigger>();
  }

  /**
   * Map triggers to specific names
   */
  public void mapTrigger(XboxButton button, DynamicTrigger trigger) {
    triggerMap.put(button, trigger);
  }

  /**
   * Map triggers to specific names using a map
   */
  public void mapTriggers(Map<XboxButton, DynamicTrigger> triggers) {
    triggerMap.putAll(triggers);
  }

  /**
   * Map triggers to specific names using a trigger map
   */
  public void mapTriggers(TM mappings) {
    mappings.getMappings().forEach((button, triggerName) -> {
        DynamicTrigger dynamicTrigger = getButtonTrigger(button);
        dynamicTrigger.setName(triggerName);
        triggerMap.put(button, dynamicTrigger);
    });
}

  /**
   * Get a trigger associated with a specific Xbox button.
   * 
   * @param button The Xbox button to get the trigger for.
   * @return The DynamicTrigger object associated with the given name, or null if
   *         not
   *         found.
   */
  public DynamicTrigger getTrigger(XboxButton button) {
    return triggerMap.get(button);
  }

  /**
   * Gets the first DynamicTrigger with the given name in the trigger map.
   * 
   * @param triggerName The name of the trigger to retrieve.
   * @return The DynamicTrigger object associated with the given name, or null if
   *         not
   *         found.
   */
  public DynamicTrigger getTrigger(String triggerName) {
    return triggerMap.values().stream()
        .filter(trigger -> triggerName.equals(trigger.getName()))
        .findFirst()
        .orElse(null);
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
  public Map<XboxButton, DynamicTrigger> getTriggerMap() {
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

  public DynamicTrigger getButtonTrigger(XboxButton button, EventLoop loop) {
    switch (button) {
      case A:
        return m_hid.a(loop).castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case B:
        return m_hid.b(loop).castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case X:
        return m_hid.x(loop).castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case Y:
        return m_hid.y(loop).castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case LEFT_BUMPER:
        return m_hid.leftBumper(loop)
            .castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case RIGHT_BUMPER:
        return m_hid.rightBumper(loop)
            .castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case BACK:
        return m_hid.back(loop)
            .castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case START:
        return m_hid.start(loop)
            .castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case LEFT_STICK:
        return m_hid.leftStick(loop)
            .castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case RIGHT_STICK:
        return m_hid.rightStick(loop)
            .castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      default:
        throw new IllegalArgumentException("Unknown button: " + button);
    }
  }

  public DynamicTrigger getButtonTrigger(XboxButton button, double threshold, EventLoop loop) {
    switch (button) {
      case LEFT_TRIGGER:
        return m_hid.leftTrigger(threshold, loop)
            .castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      case RIGHT_TRIGGER:
        return m_hid.rightTrigger(threshold, loop)
            .castTo((eventLoop, condition) -> new DynamicTrigger(eventLoop, condition, button.name()));
      default:
        throw new IllegalArgumentException("Unknown button: " + button);
    }
  }

  public DynamicTrigger getButtonTrigger(XboxButton button, double threshold) {
    return getButtonTrigger(button, threshold, CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public DynamicTrigger getButtonTrigger(XboxButton button) {
    return getButtonTrigger(button, CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  /**
   * Constructs a DynamicTrigger instance around the A button's digital signal.
   *
   * @return a DynamicTrigger instance representing the A button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #a(EventLoop)
   */
  public DynamicTrigger a() {
    return getButtonTrigger(XboxButton.A);
  }

  /**
   * Constructs a DynamicTrigger instance around the A button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the A button's dmake igital
   *         signal
   *         attached
   *         to the given loop.
   */
  public DynamicTrigger a(EventLoop loop) {
    return getButtonTrigger(XboxButton.A, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the B button's digital signal.
   *
   * @return a DynamicTrigger instance representing the B button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #b(EventLoop)
   */
  public DynamicTrigger b() {
    return getButtonTrigger(XboxButton.B);
  }

  /**
   * Constructs a DynamicTrigger instance around the B button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the B button's digital signal
   *         attached
   *         to the given loop.
   */
  public DynamicTrigger b(EventLoop loop) {
    return getButtonTrigger(XboxButton.B, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the X button's digital signal.
   *
   * @return a DynamicTrigger instance representing the X button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #x(EventLoop)
   */
  public DynamicTrigger x() {
    return getButtonTrigger(XboxButton.X);
  }

  /**
   * Constructs a DynamicTrigger instance around the X button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the X button's digital signal
   *         attached
   *         to the given loop.
   */
  public DynamicTrigger x(EventLoop loop) {
    return getButtonTrigger(XboxButton.X, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the Y button's digital signal.
   *
   * @return a DynamicTrigger instance representing the Y button's digital signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #y(EventLoop)
   */
  public DynamicTrigger y() {
    return getButtonTrigger(XboxButton.Y);
  }

  /**
   * Constructs a DynamicTrigger instance around the Y button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the Y button's digital signal
   *         attached
   *         to the given loop.
   */
  public DynamicTrigger y(EventLoop loop) {
    return getButtonTrigger(XboxButton.Y, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the left bumper button's digital
   * signal.
   *
   * @return a DynamicTrigger instance representing the left bumper button's
   *         digital
   *         signal attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #leftBumper(EventLoop)
   */
  public DynamicTrigger leftBumper() {
    return getButtonTrigger(XboxButton.LEFT_BUMPER);
  }

  /**
   * Constructs a DynamicTrigger instance around the left bumper button's digital
   * signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the left bumper button's
   *         digital
   *         signal attached
   *         to the given loop.
   */
  public DynamicTrigger leftBumper(EventLoop loop) {
    return getButtonTrigger(XboxButton.LEFT_BUMPER, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the right bumper button's digital
   * signal.
   *
   * @return a DynamicTrigger instance representing the right bumper button's
   *         digital
   *         signal attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #rightBumper(EventLoop)
   */
  public DynamicTrigger rightBumper() {
    return getButtonTrigger(XboxButton.RIGHT_BUMPER);
  }

  /**
   * Constructs a DynamicTrigger instance around the right bumper button's digital
   * signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the right bumper button's
   *         digital
   *         signal attached
   *         to the given loop.
   */
  public DynamicTrigger rightBumper(EventLoop loop) {
    return getButtonTrigger(XboxButton.RIGHT_BUMPER, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the back button's digital signal.
   *
   * @return a DynamicTrigger instance representing the back button's digital
   *         signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #back(EventLoop)
   */
  public DynamicTrigger back() {
    return getButtonTrigger(XboxButton.BACK);
  }

  /**
   * Constructs a DynamicTrigger instance around the back button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the back button's digital
   *         signal
   *         attached
   *         to the given loop.
   */
  public DynamicTrigger back(EventLoop loop) {
    return getButtonTrigger(XboxButton.BACK, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the start button's digital
   * signal.
   *
   * @return a DynamicTrigger instance representing the start button's digital
   *         signal
   *         attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #start(EventLoop)
   */
  public DynamicTrigger start() {
    return getButtonTrigger(XboxButton.START);
  }

  /**
   * Constructs a DynamicTrigger instance around the start button's digital
   * signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the start button's digital
   *         signal
   *         attached
   *         to the given loop.
   */
  public DynamicTrigger start(EventLoop loop) {
    return getButtonTrigger(XboxButton.START, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the left stick button's digital
   * signal.
   *
   * @return a DynamicTrigger instance representing the left stick button's
   *         digital
   *         signal attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #leftStick(EventLoop)
   */
  public DynamicTrigger leftStick() {
    return getButtonTrigger(XboxButton.LEFT_STICK);
  }

  /**
   * Constructs a DynamicTrigger instance around the left stick button's digital
   * signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the left stick button's
   *         digital
   *         signal attached
   *         to the given loop.
   */
  public DynamicTrigger leftStick(EventLoop loop) {
    return getButtonTrigger(XboxButton.LEFT_STICK, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the right stick button's digital
   * signal.
   *
   * @return a DynamicTrigger instance representing the right stick button's
   *         digital
   *         signal attached
   *         to the {@link CommandScheduler#getDefaultButtonLoop() default
   *         scheduler button loop}.
   * @see #rightStick(EventLoop)
   */
  public DynamicTrigger rightStick() {
    return getButtonTrigger(XboxButton.RIGHT_STICK);
  }

  /**
   * Constructs a DynamicTrigger instance around the right stick button's digital
   * signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a DynamicTrigger instance representing the right stick button's
   *         digital
   *         signal attached
   *         to the given loop.
   */
  public DynamicTrigger rightStick(EventLoop loop) {
    return getButtonTrigger(XboxButton.RIGHT_STICK, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the axis value of the left
   * trigger. The
   * returned
   * trigger will be true when the axis value is greater than {@code threshold}.
   *
   * @param threshold the minimum axis value for the returned
   *                  {@link DynamicTrigger} to
   *                  be true. This value
   *                  should be in the range [0, 1] where 0 is the unpressed state
   *                  of the axis.
   * @param loop      the event loop instance to attach the DynamicTrigger to.
   * @return a DynamicTrigger instance that is true when the left trigger's axis
   *         exceeds
   *         the provided
   *         threshold, attached to the given event loop
   */
  public DynamicTrigger leftTrigger(double threshold, EventLoop loop) {
    return getButtonTrigger(XboxButton.LEFT_TRIGGER, threshold, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the axis value of the left
   * trigger. The
   * returned
   * trigger will be true when the axis value is greater than {@code threshold}.
   *
   * @param threshold the minimum axis value for the returned
   *                  {@link DynamicTrigger} to
   *                  be true. This value
   *                  should be in the range [0, 1] where 0 is the unpressed state
   *                  of the axis.
   * @return a DynamicTrigger instance that is true when the left trigger's axis
   *         exceeds
   *         the provided
   *         threshold, attached to the
   *         {@link CommandScheduler#getDefaultButtonLoop() default scheduler
   *         button loop}.
   */
  public DynamicTrigger leftTrigger(double threshold) {
    return getButtonTrigger(XboxButton.LEFT_TRIGGER, threshold);
  }

  /**
   * Constructs a DynamicTrigger instance around the axis value of the left
   * trigger. The
   * returned trigger
   * will be true when the axis value is greater than 0.5.
   *
   * @return a DynamicTrigger instance that is true when the left trigger's axis
   *         exceeds
   *         0.5, attached to
   *         the {@link CommandScheduler#getDefaultButtonLoop() default scheduler
   *         button loop}.
   */
  public DynamicTrigger leftTrigger() {
    return leftTrigger(0.5);
  }

  /**
   * Constructs a DynamicTrigger instance around the axis value of the right
   * trigger. The
   * returned
   * trigger will be true when the axis value is greater than {@code threshold}.
   *
   * @param threshold the minimum axis value for the returned
   *                  {@link DynamicTrigger} to
   *                  be true. This value
   *                  should be in the range [0, 1] where 0 is the unpressed state
   *                  of the axis.
   * @param loop      the event loop instance to attach the DynamicTrigger to.
   * @return a DynamicTrigger instance that is true when the right trigger's axis
   *         exceeds
   *         the provided
   *         threshold, attached to the given event loop
   */
  public DynamicTrigger rightTrigger(double threshold, EventLoop loop) {
    return getButtonTrigger(XboxButton.RIGHT_TRIGGER, threshold, loop);
  }

  /**
   * Constructs a DynamicTrigger instance around the axis value of the right
   * trigger. The
   * returned
   * trigger will be true when the axis value is greater than {@code threshold}.
   *
   * @param threshold the minimum axis value for the returned
   *                  {@link DynamicTrigger} to
   *                  be true. This value
   *                  should be in the range [0, 1] where 0 is the unpressed state
   *                  of the axis.
   * @return a DynamicTrigger instance that is true when the right trigger's axis
   *         exceeds
   *         the provided
   *         threshold, attached to the
   *         {@link CommandScheduler#getDefaultButtonLoop() default scheduler
   *         button loop}.
   */
  public DynamicTrigger rightTrigger(double threshold) {
    return getButtonTrigger(XboxButton.RIGHT_TRIGGER, threshold);
  }

  /**
   * Constructs a DynamicTrigger instance around the axis value of the right
   * trigger. The
   * returned trigger
   * will be true when the axis value is greater than 0.5.
   *
   * @return a DynamicTrigger instance that is true when the right trigger's axis
   *         exceeds
   *         0.5, attached to
   *         the {@link CommandScheduler#getDefaultButtonLoop() default scheduler
   *         button loop}.
   */
  public DynamicTrigger rightTrigger() {
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

  // public void generateControllerImage(String outputPath) {
  // List<String> command = new ArrayList<>();
  // command.add("python");
  // command.add("DrawController.py");
  // command.add(outputPath);

  // for (Map.Entry<XboxButton, DynamicTrigger> entry : triggerMap.entrySet()) {
  // String buttonName = entry.getKey().toString();
  // String triggerName = entry.getValue().getName();
  // command.add("--custom_labels");
  // command.add(buttonName + "=" + triggerName);
  // }

  // ProcessBuilder processBuilder = new ProcessBuilder(command);
  // try {
  // Process process = processBuilder.start();
  // int exitCode = process.waitFor();
  // if (exitCode == 0) {
  // System.out.println("Controller image generated successfully.");
  // } else {
  // System.err.println("Error generating controller image. Exit code: " +
  // exitCode);
  // }
  // } catch (IOException | InterruptedException e) {
  // e.printStackTrace();
  // }
  // }
}
