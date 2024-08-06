package crewmate.lib;

import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.event.EventLoop;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class DynamicTrigger implements BooleanSupplier {
    private final BooleanSupplier m_condition;
    private final EventLoop m_loop;
    private String name;

    /**
     * Creates a new DynamicTrigger based on the given condition.
     *
     * @param loop      The loop instance that polls this DynamicTrigger.
     * @param condition the condition represented by this DynamicTrigger
     * @param name      the identifier for this DynamicTrigger
     */
    public DynamicTrigger(EventLoop loop, BooleanSupplier condition, String name) {
        m_loop = requireNonNullParam(loop, "loop", "Trigger");
        m_condition = requireNonNullParam(condition, "condition", "Trigger");
    }

    /**
     * Creates a new DynamicTrigger based on the given condition.
     *
     * <p>
     * Polled by the default scheduler button loop.
     *
     * @param condition the condition represented by this DynamicTrigger
     * @param name      the identifier for this DynamicTrigger
     */
    public DynamicTrigger(BooleanSupplier condition, String name) {
        this(CommandScheduler.getInstance().getDefaultButtonLoop(), condition, name);
    }

    /**
     * Starts the given command whenever the condition changes from `false` to
     * `true`.
     *
     * @param command the command to start
     * @return this DynamicTrigger, so calls can be chained
     */
    public DynamicTrigger onTrue(Command command) {
        requireNonNullParam(command, "command", "onTrue");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();

                        if (!m_pressedLast && pressed) {
                            command.schedule();
                        }

                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    public DynamicTrigger onTrue(Runnable runnable) {
        requireNonNullParam(runnable, "runnable", "onTrue");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();
                        if (!m_pressedLast && pressed) {
                            runnable.run();
                        }
                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    /**
     * Starts the given command whenever the condition changes from `true` to
     * `false`.
     *
     * @param command the command to start
     * @return this DynamicTrigger, so calls can be chained
     */
    public DynamicTrigger onFalse(Command command) {
        requireNonNullParam(command, "command", "onFalse");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();

                        if (m_pressedLast && !pressed) {
                            command.schedule();
                        }

                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    public DynamicTrigger onFalse(Runnable runnable) {
        requireNonNullParam(runnable, "runnable", "onFalse");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();
                        if (m_pressedLast && !pressed) {
                            runnable.run();
                        }
                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    /**
     * Starts the given command when the condition changes to `true` and cancels it
     * when the condition
     * changes to `false`.
     *
     * <p>
     * Doesn't re-start the command if it ends while the condition is still `true`.
     * If the command
     * should restart, see {@link edu.wpi.first.wpilibj2.command.RepeatCommand}.
     *
     * @param command the command to start
     * @return this DynamicTrigger, so calls can be chained
     */
    public DynamicTrigger whileTrue(Command command) {
        requireNonNullParam(command, "command", "whileTrue");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();

                        if (!m_pressedLast && pressed) {
                            command.schedule();
                        } else if (m_pressedLast && !pressed) {
                            command.cancel();
                        }

                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    public DynamicTrigger whileTrue(Runnable runnable) {
        requireNonNullParam(runnable, "runnable", "whileTrue");
        m_loop.bind(
                new Runnable() {
                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();
                        if (pressed) {
                            runnable.run();
                        }
                    }
                });
        return this;
    }

    /**
     * Starts the given command when the condition changes to `false` and cancels it
     * when the
     * condition changes to `true`.
     *
     * <p>
     * Doesn't re-start the command if it ends while the condition is still `false`.
     * If the command
     * should restart, see {@link edu.wpi.first.wpilibj2.command.RepeatCommand}.
     *
     * @param command the command to start
     * @return this DynamicTrigger, so calls can be chained
     */
    public DynamicTrigger whileFalse(Command command) {
        requireNonNullParam(command, "command", "whileFalse");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();

                        if (m_pressedLast && !pressed) {
                            command.schedule();
                        } else if (!m_pressedLast && pressed) {
                            command.cancel();
                        }

                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    public DynamicTrigger whileFalse(Runnable runnable) {
        requireNonNullParam(runnable, "runnable", "whileFalse");
        m_loop.bind(
                new Runnable() {
                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();
                        if (!pressed) {
                            runnable.run();
                        }
                    }
                });
        return this;
    }

    /**
     * Toggles a command when the condition changes from `false` to `true`.
     *
     * @param command the command to toggle
     * @return this DynamicTrigger, so calls can be chained
     */
    public DynamicTrigger toggleOnTrue(Command command) {
        requireNonNullParam(command, "command", "toggleOnTrue");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();

                        if (!m_pressedLast && pressed) {
                            if (command.isScheduled()) {
                                command.cancel();
                            } else {
                                command.schedule();
                            }
                        }

                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    public DynamicTrigger toggleOnTrue(Runnable runnable) {
        requireNonNullParam(runnable, "runnable", "toggleOnTrue");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();
                    private boolean m_isRunning = false;

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();
                        if (!m_pressedLast && pressed) {
                            m_isRunning = !m_isRunning;
                        }
                        if (m_isRunning) {
                            runnable.run();
                        }
                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    /**
     * Toggles a command when the condition changes from `true` to `false`.
     *
     * @param command the command to toggle
     * @return this DynamicTrigger, so calls can be chained
     */
    public DynamicTrigger toggleOnFalse(Command command) {
        requireNonNullParam(command, "command", "toggleOnFalse");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();

                        if (m_pressedLast && !pressed) {
                            if (command.isScheduled()) {
                                command.cancel();
                            } else {
                                command.schedule();
                            }
                        }

                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    public DynamicTrigger toggleOnFalse(Runnable runnable) {
        requireNonNullParam(runnable, "runnable", "toggleOnFalse");
        m_loop.bind(
                new Runnable() {
                    private boolean m_pressedLast = m_condition.getAsBoolean();
                    private boolean m_isRunning = false;

                    @Override
                    public void run() {
                        boolean pressed = m_condition.getAsBoolean();
                        if (m_pressedLast && !pressed) {
                            m_isRunning = !m_isRunning;
                        }
                        if (m_isRunning) {
                            runnable.run();
                        }
                        m_pressedLast = pressed;
                    }
                });
        return this;
    }

    @Override
    public boolean getAsBoolean() {
        return m_condition.getAsBoolean();
    }

    /**
     * Composes two DynamicTriggers with logical AND.
     *
     * @param DynamicTrigger the condition to compose with
     * @return A DynamicTrigger which is active when both component DynamicTriggers
     *         are active.
     */
    public DynamicTrigger and(BooleanSupplier DynamicTrigger, String name) {
        return new DynamicTrigger(m_loop, () -> m_condition.getAsBoolean() && DynamicTrigger.getAsBoolean(), name);
    }

    /**
     * Composes two DynamicTriggers with logical OR.
     *
     * @param DynamicTrigger the condition to compose with
     * @return A DynamicTrigger which is active when either component DynamicTrigger
     *         is active.
     */
    public DynamicTrigger or(BooleanSupplier DynamicTrigger, String name) {
        return new DynamicTrigger(m_loop, () -> m_condition.getAsBoolean() || DynamicTrigger.getAsBoolean(), name);
    }

    /**
     * Creates a new DynamicTrigger that is active when this DynamicTrigger is
     * inactive, i.e. that
     * acts as the
     * negation of this DynamicTrigger.
     *
     * @return the negated DynamicTrigger
     */
    public DynamicTrigger negate(String name) {
        return new DynamicTrigger(m_loop, () -> !m_condition.getAsBoolean(), name);
    }

    /**
     * Creates a new debounced DynamicTrigger from this DynamicTrigger - it will
     * become active
     * when this DynamicTrigger has
     * been active for longer than the specified period.
     *
     * @param seconds The debounce period.
     * @return The debounced DynamicTrigger (rising edges debounced only)
     */
    public DynamicTrigger debounce(double seconds, String name) {
        return debounce(seconds, Debouncer.DebounceType.kRising, name);
    }

    /**
     * Creates a new debounced DynamicTrigger from this DynamicTrigger - it will
     * become active when this DynamicTrigger has
     * been active for longer than the specified period.
     *
     * @param seconds The debounce period.
     * @param type    The debounce type.
     * @return The debounced DynamicTrigger.
     */
    public DynamicTrigger debounce(double seconds, Debouncer.DebounceType type, String name) {
        return new DynamicTrigger(
                m_loop,
                new BooleanSupplier() {
                    final Debouncer m_debouncer = new Debouncer(seconds, type);

                    @Override
                    public boolean getAsBoolean() {
                        return m_debouncer.calculate(m_condition.getAsBoolean());
                    }
                },
                name);
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}