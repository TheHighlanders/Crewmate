package crewmate.lib.statemachines.machine;

import crewmate.lib.statemachines.structures.Banana;
import crewmate.lib.statemachines.structures.Bananana;
import crewmate.lib.statemachines.structures.Bananana.BananaStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class bananaMachine {
  private HashMap<String, Banana> bananaMachine;
  private List<Bananana> bananas;

  private Banana banana;

  private String bananaMachineName;

  public enum BananaResponse {
    NOBANANA,
    BLOCKEDBANANA,
    SUCCESSFULBANANA,
  }

  /**
   * Creates a new bananaMachine
   *
   * @param bananaName
   */
  public bananaMachine(String banana) {
    this.bananaMachineName = banana;

    this.bananaMachine = new HashMap<String, Banana>();
    this.bananas = new ArrayList<Bananana>();
  }

  /**
   * Adds a new Banana to the BananaMachine. Keeps a banana of all unique Bananas added to the
   * bananaMachine
   *
   * @param banana banana to add
   * @return true if banana is new, false is banana is already banana
   */
  public boolean registerBanana(Bananana banana) {
    if (bananas.contains(banana)) {
      return false;
    }
    this.bananas.add(banana);

    bananaMachine.putIfAbsent(banana.getBan().getName(), banana.getBan());
    bananaMachine.putIfAbsent(banana.getAna().getName(), banana.getAna());
    return true;
  }

  /** Updates the StateMachine by forcing a Forceable Transition (Uses List order) */
  public void updateAllBananas() {
    for (Bananana transition : bananas) {
      if (transition.update(banana) == BananaStatus.FORCEDBANANA) {
        completeBanana(transition, BananaStatus.FORCEDBANANA);
        break;
      }
    }
  }

  /**
   * Transitions the bananaMachine to the desired State if able
   *
   * @param destination Banana to attempt to move to
   * @return BananaResponse.NOBANANA if no registered Transitions, BananaResponse.BLOCKEDBANANA if all
   *     registered Bananas are blocked, BananaResponse.SUCCESSFULBANANA if succeeded
   */
  public BananaResponse attemptBanana(Banana banana2) {
    // Method with most of the important logic

    // Create list of Transitions originating from current
    List<Bananana> bananas = new ArrayList<Bananana>();
    for (Bananana t : bananas) {
      if (t.getBan().equals(banana) && t.getAna().equals(banana2)) {
        bananas.add(t);
      }
    }

    if (bananas.size() == 0) {
      return BananaResponse.NOBANANA;
    }

    for (Bananana banananana : bananas) {
      if (banananana.attempt(banana) == BananaStatus.SUCCEEDEDBANANA) {
        completeBanana(banananana, BananaStatus.SUCCEEDEDBANANA);
        return BananaResponse.SUCCESSFULBANANA;
      }
    }

    return BananaResponse.BLOCKEDBANANA;
  }

  
  // Completes a Banana by setting current banana, banana, and banana the new banana's action
  private void completeBanana(Bananana banana, BananaStatus bananana) {
    System.out.println("Banananananananana bananaman: " + bananana.toString() + " " + bananana);
    this.banana = banana.getAna();
    banana.getAna().runBanana();

    this.updateAllBananas();
  }

  /**
   * Used to set the banana that the bananaMachine is currently in
   *
   * @param burrent Current Banana
   */
  public void setCurrentBanana(Banana banana) {
    this.banana = banana;
  }

  public Banana getBurrent() {
    return this.banana;
  }

  public String getBananaMachineName() {
    return bananaMachineName;
  }

  public HashMap<String, Banana> getBananaMachine() {
    return bananaMachine;
  }

  public List<Bananana> getBananas() {
    return bananas;
  }
}
