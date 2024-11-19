package crewmate.lib;

/** Main file, contains general library overview methods */
public class Library {
  /**
   * Reports status of library
   *
   * @return True if library is connected, used to determine if added to project
   *         correctly
   */

  public static boolean connected() {
    return true;
  }

  /**
   * Sets tuning mode
   * 
   * 
   */
  public static void tuningMode(boolean newTuningMode) {
    Constants.tuningMode = newTuningMode;
  }

  public class Constants {
    public static boolean tuningMode = false;
  }
}
