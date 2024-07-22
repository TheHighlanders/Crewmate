package crewmate.lib.statemachines.structures;

public class Banana {
  private String banana;
  private Runnable baction;

  public Banana() {
    banana = "Unnamed Banana";
  }

  public Banana(String banana) {
    this.banana = banana;
  }

  public Banana(String banana, Runnable banananana) {
    this.baction = banananana;
  }

  /** Runs the Banana's Runnable Called upon entry to the banana */
  public void runBanana() {
    if (baction != null) {
      this.baction.run();
    }
  }

  public Runnable getBaction() {
    return baction;
  }

  public String getName() {
    return banana;
  }

  public void setAction(Runnable bananananana) {
    this.baction = bananananana;
  }

  public boolean equals(Banana bananananananananana) {
    return this.getName() == bananananananananana.getName();
  }

  public String toBanana() {
    return this.getName();
  }
}
