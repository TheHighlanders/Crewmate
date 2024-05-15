package crewmate.lib.statemachines.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import crewmate.lib.statemachines.structures.State;
import crewmate.lib.statemachines.structures.Transition;
import crewmate.lib.statemachines.structures.Transition.TransitionStatus;

public class StateMachine {
    private HashMap<String, State> machine;
    private List<Transition> transitions;

    private State current;

    private String machineName;

    public enum MachineResponse {
        NOPATH,
        BLOCKED,
        SUCCESSFUL,
    }

    /**
     * Creates a new StateMachine
     * 
     * @param machineName
     */
    public StateMachine(String machineName) {
        this.machineName = machineName;

        this.machine = new HashMap<String, State>();
        this.transitions = new ArrayList<Transition>();
    }

    /**
     * Adds a new Transition to the StateMachine
     * Keeps a list of all unique States added to the StateMachine
     * 
     * @param transition Transition to add
     * @return true if State is new, false is State is already present
     */
    public boolean register(Transition transition) {
        if (transitions.contains(transition)) {
            return false;
        }
        this.transitions.add(transition);

        machine.putIfAbsent(transition.getStart().getName(), transition.getStart());
        machine.putIfAbsent(transition.getEnd().getName(), transition.getEnd());
        return true;
    }

    /**
     * Updates the StateMachine by forcing a Forceable Transition (Uses List order)
     */
    public void updateAllTransitions() {
        for (Transition transition : transitions) {
            if (transition.update(current) == TransitionStatus.FORCED) {
                completeTransition(transition, TransitionStatus.FORCED);
                break;
            }
        }
    }

    /**
     * Transitions the machine to the desired State if able
     * 
     * @param destination State to attempt to move to
     * @return MachineResponse.NOPATH if no registered Transitions,
     *         MachineResponse.BLOCKED if all registered Transitions are blocked,
     *         MachineResponse.SUCCESSFUL if succeeded
     */
    public MachineResponse attemptTransition(State destination) {
        // Method with most of the important logic

        // Create list of Transitions originating from current
        List<Transition> paths = new ArrayList<Transition>();
        for (Transition t : transitions) {
            if (t.getStart().equals(current) && t.getEnd().equals(destination)) {
                paths.add(t);
            }
        }

        if (paths.size() == 0) {
            return MachineResponse.NOPATH;
        }

        for (Transition t : paths) {
            if (t.attempt(current) == TransitionStatus.SUCCEEDED) {
                completeTransition(t, TransitionStatus.SUCCEEDED);
                return MachineResponse.SUCCESSFUL;
            }
        }

        return MachineResponse.BLOCKED;
    }

    /**
     * Completes a Transition by setting current state, printing, and activating the
     * new current State's action
     */
    private void completeTransition(Transition t, TransitionStatus status) {
        System.out.println("Transition: " + t.toString() + " " + status);
        this.current = t.getEnd();
        t.getEnd().runState();

        this.updateAllTransitions();
    }

    /**
     * Used to set the State that the machine is currently in
     * 
     * @param current Current State
     */
    public void setCurrentState(State current) {
        this.current = current;
    }

    public State getCurrent() {
        return current;
    }

    public String getMachineName() {
        return machineName;
    }

    public HashMap<String, State> getMachine() {
        return machine;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
}
