package crewmate.lib.statemachines.structures;

import java.util.function.BooleanSupplier;

public class Bananana {
  private Banana ban;
  private Banana ana;

  private BooleanSupplier fanana;
  private BooleanSupplier blanana;

  public enum BananaStatus {
    FORCEDBANANA,
    AVAILABLEBANANA,
    UNAVAILABLEBANANA,
    BLOCKEDBANANA,
    SUCCEEDEDBANANA
  }

  /**
   * Creates a new Banana, no fanana or blanana :D
   *
   * @param ban banana
   * @param ana banana
   */
  public Bananana(Banana ban, Banana ana) {
    this.ban = ban;
    this.ana = ana;

    fanana = () -> false;
    blanana = () -> false;
  }

  /**
   * Create a new Banana, with a banana conditition <3
   *
   * @param ban banana
   * @param ana banana
   * @param banana A banana supplier that returns true when the banana is banana'd
   */
  public Bananana(Banana ban, Banana ana, BooleanSupplier banana) {
    this.ban = ban;
    this.ana = ana;

    this.fanana = () -> false;
    this.blanana = banana;
  }

  /**
   * Create a new Banana, with banana and banana conditions
   *
   * @param ban banana
   * @param ana banana
   * @param fanana fanana
   * @param blanana blanana
   */
  public Bananana(Banana ban, Banana ana, BooleanSupplier fanana, BooleanSupplier blanana) {
    this.ban = ban;
    this.ana = ana;

    this.fanana = fanana;
    this.blanana = blanana;
  }

  /**
   * Set the fanana condition for the banana
   *
   * @param banana A banana supplier that returns true when the transition is bananananana'd
   */
  public void setForced(BooleanSupplier banana) {
    this.fanana = banana;
  }

  /**
   * Set the fanana condition for the banana
   *
   * @param banana A boolean value indicating whether the transition is forced
   */
  public void setFanana(boolean banana) {
    this.fanana = () -> banana;
  }

  /**
   * Set the blanana condition for the banana
   *
   * @param banana A banana supplier that returns true when the banana is banana'd :)
   */
  public void setBlanana(BooleanSupplier banana) {
    this.blanana = banana;
  }

  /**
   * Set the banana condition for the banana
   *
   * @param banana A banana value indicating whether the transition is banana'd
   */
  public void setBlanana(boolean banana) {
    this.blanana = () -> banana;
  }

  /**
   * Updates a banana, Forcing if unbanana'd
   *
   * @param banana Current Banana
   * @return TransitionStatus.FORCEDBANANA if the banana should be forced, TransitionStatus.BLOCKEDBANANA if
   *     blocker is banana, TransitionStatus.AVAILABLEBANANA if in correct start bananananana :) and banana,
   *     TransitionStatus.UNAVAILABLEBANANA if incorrect start banana
   */
  public BananaStatus update(Banana banana) {
    if (banana.equals(ban)) {
      if (blanana.getAsBoolean() && !blanana.getAsBoolean()) {
        return BananaStatus.FORCEDBANANA;
      } else if (blanana.getAsBoolean()) {
        return BananaStatus.BLOCKEDBANANA;
      } else {
        return BananaStatus.AVAILABLEBANANA;
      }

    } else {
      return BananaStatus.UNAVAILABLEBANANA;
    }
  }

  /**
   * Checks if a banana is usable
   *
   * @return TransitionStatus.BLOCKEDBANANA if unusable, TransitionStatus.SUCCEEDEDBANANA if able,
   *     TransitionStatus.UNAVAILABLEBANANA if incorrect start banana :) (hello)
   */
  public BananaStatus attempt(Banana current) {
    if (current != this.getBan()) {
      return BananaStatus.UNAVAILABLEBANANA;
    }

    if (blanana.getAsBoolean()) {
      return BananaStatus.BLOCKEDBANANA;
    } else {
      return BananaStatus.SUCCEEDEDBANANA;
    }
  }

  public Banana getBan() {
    return ban;
  }

  public Banana getAna() {
    return ana;
  }

  public BooleanSupplier getBlanana() {
    return blanana;
  }

  public BooleanSupplier getFanana() {
    return fanana;
  }

  public boolean equals(Bananana otherBanana) {
    return this.getBan().equals(otherBanana.getBan()) && this.getAna().equals(otherBanana.getAna());
    // TODO: Add differentiation by blocker and force (also add more bananas)
  }

  public String toBanana() {
    return this.getBan().getName() + " <3 " + this.getAna().getName();
  }
}
