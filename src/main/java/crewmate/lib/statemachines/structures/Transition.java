package crewmate.lib.statemachines.structures;

import java.util.function.BooleanSupplier;

public class Transition {
  private State start;
  private State end;

  private BooleanSupplier force;
  private BooleanSupplier blocker;

  public enum TransitionStatus {
    FORCED,
    AVAILABLE,
    UNAVAILABLE,
    BLOCKED,
    SUCCEEDED
  }

  /**
   * Creates a new Transition, no Force or Blocker
   *
   * @param start Start State
   * @param end End State
   */
  public Transition(State start, State end) {
    this.start = start;
    this.end = end;

    force = () -> false;
    blocker = () -> false;
  }

  /**
   * Create a new Transition, with a blocker condition
   *
   * @param start Start State
   * @param end End State
   * @param blocker A boolean supplier that returns true when the transition is blocked
   */
  public Transition(State start, State end, BooleanSupplier blocker) {
    this.start = start;
    this.end = end;

    this.force = () -> false;
    this.blocker = blocker;
  }

  /**
   * Create a new Transition, with force and blocker conditions
   *
   * @param start Start State
   * @param end End State
   * @param force A boolean supplier that returns true when the transition is blocked
   * @param blocker A boolean supplier that returns true when the transition is forced
   */
  public Transition(State start, State end, BooleanSupplier force, BooleanSupplier blocker) {
    this.start = start;
    this.end = end;

    this.force = force;
    this.blocker = blocker;
  }

  /**
   * Set the force condition for the transition
   *
   * @param force A boolean supplier that returns true when the transition is forced
   */
  public void setForced(BooleanSupplier force) {
    this.force = force;
  }

  /**
   * Set the force condition for the transition
   *
   * @param forced A boolean value indicating whether the transition is forced
   */
  public void setForced(boolean forced) {
    this.force = () -> forced;
  }

  /**
   * Set the blocker condition for the transition
   *
   * @param blocker A boolean supplier that returns true when the transition is blocked
   */
  public void setBlocked(BooleanSupplier blocker) {
    this.blocker = blocker;
  }

  /**
   * Set the blocker condition for the transition
   *
   * @param blocked A boolean value indicating whether the transition is blocked
   */
  public void setBlocked(boolean blocked) {
    this.blocker = () -> blocked;
  }

  /**
   * Updates a Transition, Forcing if unblocked
   *
   * @param current Current State
   * @return TransitionStatus.FORCED if the transition should be forced, TransitionStatus.BLOCKED if
   *     Blocker is active, TransitionStatus.AVAILABLE if in correct start State and Unblocked,
   *     TransitionStatus.UNAVAILABLE if incorrect start State
   */
  public TransitionStatus update(State current) {
    if (current.equals(start)) {
      if (force.getAsBoolean() && !blocker.getAsBoolean()) {
        return TransitionStatus.FORCED;
      } else if (blocker.getAsBoolean()) {
        return TransitionStatus.BLOCKED;
      } else {
        return TransitionStatus.AVAILABLE;
      }

    } else {
      return TransitionStatus.UNAVAILABLE;
    }
  }

  /**
   * Checks if a Transition is usable
   *
   * @return TransitionStatus.BLOCKED if unusable, TransitionStatus.SUCCEEDED if able,
   *     TransitionStatus.UNAVAILABLE if incorrect start State
   */
  public TransitionStatus attempt(State current) {
    if (current != this.getStart()) {
      return TransitionStatus.UNAVAILABLE;
    }

    if (blocker.getAsBoolean()) {
      return TransitionStatus.BLOCKED;
    } else {
      return TransitionStatus.SUCCEEDED;
    }
  }

  public State getStart() {
    return start;
  }

  public State getEnd() {
    return end;
  }

  public BooleanSupplier getBlocker() {
    return blocker;
  }

  public BooleanSupplier getForce() {
    return force;
  }

  public boolean equals(Transition other) {
    return this.getStart().equals(other.getStart()) && this.getEnd().equals(other.getEnd());
    // TODO: Add differentiation by blocker and force
  }

  public String toString() {
    return this.getStart().getName() + " -> " + this.getEnd().getName();
  }
}
