// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package crewmate.lib;

import edu.wpi.first.wpilibj.XboxController;

import java.util.Map;
import java.util.function.Supplier;

import crewmate.lib.CMControllerConfig.ActionType;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * A version of {@link XboxController} with {@link Trigger} factories for
 * command-based.
 *
 * @see XboxController
 */
@SuppressWarnings("MethodName")
public class CMController extends CommandGenericHID {
  private final XboxController m_hid;
  private final Map<Trigger, Map<ActionType, Command>> buttonActions;

  /**
   * Construct an instance of a controller.
   *
   * @param port The port index on the Driver Station that the controller is
   *             plugged into.
   */
  public CMController(int port) {
    super(port);
    m_hid = new XboxController(port);
    this.buttonActions = null;
  }

  public CMController(int port, CMControllerConfig config) {
    super(port);
    m_hid = new XboxController(port);
    this.buttonActions = config.getButtonActions();
  }

  public void handleButtonActions() {
    for (Map.Entry<Trigger, Map<ActionType, Command>> entry : buttonActions.entrySet()) {
      Trigger trigger = entry.getKey();
      Map<ActionType, Command> actionMap = entry.getValue();

      for (Map.Entry<ActionType, Command> actionEntry : actionMap.entrySet()) {
        ActionType actionType = actionEntry.getKey();
        Command action = actionEntry.getValue();
        switch (actionType) {
          case WHILE_TRUE:
            trigger.whileTrue(action);
            break;
          case WHILE_FALSE:
            trigger.whileFalse(action);
            break;
          case ON_TRUE:
            trigger.onTrue(action);
            break;
          case ON_FALSE:
            trigger.onFalse(action);
            break;
          case TOGGLE_ON_TRUE:
            trigger.toggleOnTrue(action);
            break;
          case TOGGLE_ON_FALSE:
            trigger.toggleOnFalse(action);
            break;
        }
      }
    }
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
    return a(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  /**
   * Constructs a Trigger instance around the A button's digital signal.
   *
   * @param loop the event loop instance to attach the event to.
   * @return a Trigger instance representing the A button's digital signal
   *         attached
   *         to the given loop.
   */
  public Trigger a(EventLoop loop) {
    return m_hid.a(loop).castTo(Trigger::new);
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
    return b(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.b(loop).castTo(Trigger::new);
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
    return x(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.x(loop).castTo(Trigger::new);
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
    return y(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.y(loop).castTo(Trigger::new);
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
    return leftBumper(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.leftBumper(loop).castTo(Trigger::new);
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
    return rightBumper(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.rightBumper(loop).castTo(Trigger::new);
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
    return back(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.back(loop).castTo(Trigger::new);
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
    return start(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.start(loop).castTo(Trigger::new);
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
    return leftStick(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.leftStick(loop).castTo(Trigger::new);
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
    return rightStick(CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.rightStick(loop).castTo(Trigger::new);
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
    return m_hid.leftTrigger(threshold, loop).castTo(Trigger::new);
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
    return leftTrigger(threshold, CommandScheduler.getInstance().getDefaultButtonLoop());
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
    return m_hid.rightTrigger(threshold, loop).castTo(Trigger::new);
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
    return rightTrigger(threshold, CommandScheduler.getInstance().getDefaultButtonLoop());
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
