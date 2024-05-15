package crewmate.lib.statemachines.structures;

public class State {
  private String name;
  private Runnable action;

  public State() {
    name = "Unnamed State";
  }

  public State(String name) {
    this.name = name;
  }

  public State(String name, Runnable action) {
    this.action = action;
  }

  /** Runs the State's Runnable Called upon entry to the state */
  public void runState() {
    if (action != null) {
      this.action.run();
    }
  }

  public Runnable getAction() {
    return action;
  }

  public String getName() {
    return name;
  }

  public void setAction(Runnable action) {
    this.action = action;
  }

  public boolean equals(State other) {
    return this.getName() == other.getName();
  }

  public String toString() {
    return this.getName();
  }
}
