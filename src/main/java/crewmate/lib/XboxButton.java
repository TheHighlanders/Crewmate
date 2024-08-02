package crewmate.lib;

public enum XboxButton {
  A("A"),
  B("B"),
  X("X"),
  Y("Y"),
  LEFT_BUMPER("LEFT_BUMPER"),
  RIGHT_BUMPER("RIGHT_BUMPER"),
  BACK("BACK"),
  START("START"),
  LEFT_STICK("LEFT_STICK"),
  RIGHT_STICK("RIGHT_STICK"),
  LEFT_TRIGGER("LEFT_TRIGGER"),
  RIGHT_TRIGGER("RIGHT_TRIGGER");

  private final String buttonName;

  XboxButton(String buttonName) {
    this.buttonName = buttonName;
  }

  public String getButtonName() {
    return buttonName;
  }
}