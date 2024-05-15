package crewmate.lib.statemachines.machine;

import static org.junit.jupiter.api.Assertions.*;

import crewmate.lib.statemachines.machine.StateMachine.MachineResponse;
import crewmate.lib.statemachines.structures.State;
import crewmate.lib.statemachines.structures.Transition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateMachineTest {

  private StateMachine stateMachine;
  private State startState;
  private State endState;
  private Transition transition;

  @BeforeEach
  void setUp() {
    stateMachine = new StateMachine("TestMachine");
    startState = new State("Start");
    endState = new State("End");
    transition = new Transition(startState, endState);
  }

  @Test
  void testRegister_NewStates_ReturnsTrue() {
    boolean result = stateMachine.register(transition);
    assertTrue(result);
  }

  @Test
  void testRegister_ExistingStates_ReturnsFalse() {
    stateMachine.register(transition);
    boolean result = stateMachine.register(transition);
    assertFalse(result);
  }

  @Test
  void testAttemptTransition_NoPath_ReturnsNOPATH() {
    stateMachine.setCurrentState(startState);
    MachineResponse response = stateMachine.attemptTransition(endState);
    assertEquals(MachineResponse.NOPATH, response);
  }

  @Test
  void testAttemptTransition_BlockedTransition_ReturnsBLOCKED() {
    stateMachine.register(transition);
    stateMachine.setCurrentState(startState);
    transition.setBlocked(true);
    MachineResponse response = stateMachine.attemptTransition(endState);
    assertEquals(MachineResponse.BLOCKED, response);
  }

  @Test
  void testAttemptTransition_SuccessfulTransition_ReturnsSUCCESSFUL() {
    stateMachine.register(transition);
    stateMachine.setCurrentState(startState);
    MachineResponse response = stateMachine.attemptTransition(endState);
    assertEquals(MachineResponse.SUCCESSFUL, response);
    assertEquals(endState, stateMachine.getCurrent());
  }

  @Test
  void testUpdateAllTransitions_ForcedTransition_UpdatesCurrentState() {
    stateMachine.register(transition);
    stateMachine.setCurrentState(startState);
    transition.setForced(true);
    stateMachine.updateAllTransitions();
    assertEquals(endState, stateMachine.getCurrent());
  }
}
