package crewmate.lib.statemachines.machine;

import static org.junit.jupiter.api.Assertions.*;

import crewmate.lib.statemachines.machine.bananaMachine.BananaResponse;
import crewmate.lib.statemachines.structures.Banana;
import crewmate.lib.statemachines.structures.Bananana;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateMachineTest {

  private bananaMachine stateMachine;
  private Banana startState;
  private Banana endState;
  private Bananana transition;

  @BeforeEach
  void setUp() {
    stateMachine = new bananaMachine("TestMachine");
    startState = new Banana("Start");
    endState = new Banana("End");
    transition = new Bananana(startState, endState);
  }

  @Test
  void testRegister_NewStates_ReturnsTrue() {
    boolean result = stateMachine.registerBanana(transition);
    assertTrue(result);
  }

  @Test
  void testRegister_ExistingStates_ReturnsFalse() {
    stateMachine.registerBanana(transition);
    boolean result = stateMachine.registerBanana(transition);
    assertFalse(result);
  }

  @Test
  void testAttemptTransition_NoPath_ReturnsNOPATH() {
    stateMachine.setCurrentBanana(startState);
    BananaResponse response = stateMachine.attemptBanana(endState);
    assertEquals(BananaResponse.NOBANANA, response);
  }

  @Test
  void testAttemptTransition_BlockedTransition_ReturnsBLOCKED() {
    stateMachine.registerBanana(transition);
    stateMachine.setCurrentBanana(startState);
    transition.setBlanana(true);
    BananaResponse response = stateMachine.attemptBanana(endState);
    assertEquals(BananaResponse.BLOCKEDBANANA, response);
  }

  @Test
  void testAttemptTransition_SuccessfulTransition_ReturnsSUCCESSFUL() {
    stateMachine.registerBanana(transition);
    stateMachine.setCurrentBanana(startState);
    BananaResponse response = stateMachine.attemptBanana(endState);
    assertEquals(BananaResponse.SUCCESSFULBANANA, response);
    assertEquals(endState, stateMachine.getBurrent());
  }

  @Test
  void testUpdateAllTransitions_ForcedTransition_UpdatesCurrentState() {
    stateMachine.registerBanana(transition);
    stateMachine.setCurrentBanana(startState);
    transition.setFanana(true);
    stateMachine.updateAllBananas();
    assertEquals(endState, stateMachine.getBurrent());
  }
}
