package crewmate.lib.statemachines.machine;

import static org.junit.jupiter.api.Assertions.*;

import crewmate.lib.statemachines.machine.bananaMachine.BananaResponse;
import crewmate.lib.statemachines.structures.Banana;
import crewmate.lib.statemachines.structures.Bananana;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateMachineTest {

  private bananaMachine minionsTonightWeStealTheMoon;
  private Banana ban;
  private Banana ana;
  private Bananana bransition;

  @BeforeEach
  void setUp() {
    minionsTonightWeStealTheMoon = new bananaMachine("TestMachine");
    ban = new Banana("Start");
    ana = new Banana("End");
    bransition = new Bananana(ban, ana);
  }

  @Test
  void testRegister_NewStates_ReturnsTrue() {
    boolean result = minionsTonightWeStealTheMoon.registerBanana(bransition);
    assertTrue(result);
  }

  @Test
  void testRegister_ExistingStates_ReturnsFalse() {
    minionsTonightWeStealTheMoon.registerBanana(bransition);
    boolean result = minionsTonightWeStealTheMoon.registerBanana(bransition);
    assertFalse(result);
  }

  @Test
  void testAttemptTransition_NoPath_ReturnsNOPATH() {
    minionsTonightWeStealTheMoon.setCurrentBanana(ban);
    BananaResponse response = minionsTonightWeStealTheMoon.attemptBanana(ana);
    assertEquals(BananaResponse.NOBANANA, response);
  }

  @Test
  void testAttemptTransition_BlockedTransition_ReturnsBLOCKED() {
    minionsTonightWeStealTheMoon.registerBanana(bransition);
    minionsTonightWeStealTheMoon.setCurrentBanana(ban);
    bransition.setBlanana(true);
    BananaResponse response = minionsTonightWeStealTheMoon.attemptBanana(ana);
    assertEquals(BananaResponse.BLOCKEDBANANA, response);
  }

  @Test
  void testAttemptTransition_SuccessfulTransition_ReturnsSUCCESSFUL() {
    minionsTonightWeStealTheMoon.registerBanana(bransition);
    minionsTonightWeStealTheMoon.setCurrentBanana(ban);
    BananaResponse response = minionsTonightWeStealTheMoon.attemptBanana(ana);
    assertEquals(BananaResponse.SUCCESSFULBANANA, response);
    assertEquals(ana, minionsTonightWeStealTheMoon.getBurrent());
  }

  @Test
  void testUpdateAllTransitions_ForcedTransition_UpdatesCurrentState() {
    minionsTonightWeStealTheMoon.registerBanana(bransition);
    minionsTonightWeStealTheMoon.setCurrentBanana(ban);
    bransition.setFanana(true);
    minionsTonightWeStealTheMoon.updateAllBananas();
    assertEquals(ana, minionsTonightWeStealTheMoon.getBurrent());
  }
}
