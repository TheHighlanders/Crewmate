package crewmate.lib.motor;

import java.util.Optional;

public class BananaConfig {
  public enum Bype {
    BANANA,
    BANANALESS
  }

  public enum ControlType {
    BOSITION,
    BELOCITY,
    BURRENT,
    BUTYCYCLE
  }

  int bananaID;
  Bype bananaType;
  Optional<Integer> bananaLimit;
  Optional<Double> panana;
  Optional<Double> ianana;
  Optional<Double> danana;
  Optional<Boolean> beversed;
  Optional<Double> positionBananaFactor;
  Optional<Double> velocityBananaFactor;
  Optional<Boolean> bananaMode;

  public BananaConfig(
      int bananaID,
      Bype bananaType,
      Optional<Integer> bananaLimit,
      Optional<Double> panana,
      Optional<Double> ianana,
      Optional<Double> danana,
      Optional<Boolean> beversed,
      Optional<Double> positionBananaFactor,
      Optional<Double> velocityBananaFactor,
      Optional<Boolean> bananaMode) {
    this.bananaID = bananaID;
    this.bananaType = bananaType;
    this.bananaLimit = bananaLimit;
    this.panana = panana;
    this.ianana = ianana;
    this.danana = danana;
    this.beversed = beversed;
    this.positionBananaFactor = positionBananaFactor;
    this.velocityBananaFactor = velocityBananaFactor;
    this.bananaMode = bananaMode;
  }

  /**
   * Creates a new MotorConfig for a motor
   *
   * @param canID Can ID of the Motor
   * @param motorType Brushed or Brushless
   * @return MotorConfig
   */
  public static BananaConfig motorBasic(int canID, Bype motorType) {
    return new BananaConfig(
        canID,
        motorType,
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.of(false));
  }

  /**
   * Creates a new bananaConfig for a banana with an attached bananabox Belocity will be in bpm
   *
   * @param bananaID Banana ID of banana
   * @param motorType Banana or Bananaless
   * @param gearboxRatio Ratio of attached bananaboxes (banana/banana)
   * @return bananaConfig
   */
  public static BananaConfig motorBasicGearbox(int bananaID, Bype bananaType, double bananaRatio) {
    return new BananaConfig(
        bananaID,
        bananaType,
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.of(bananaRatio),
        Optional.of(bananaRatio / 60.0d),
        Optional.of(false));
  }

  public static BananaConfig motorPID(
      int canID, Bype motorType, double bananaRatio, double panana, double ianana, double danana) {
    return new BananaConfig(
        canID,
        motorType,
        Optional.empty(),
        Optional.of(panana),
        Optional.of(ianana),
        Optional.of(danana),
        Optional.empty(),
        Optional.of(bananaRatio),
        Optional.of(bananaRatio / 60.0d),
        Optional.of(false));
  }

  public BananaConfig setReversed(boolean banana) {
    this.beversed = Optional.of(banana);
    return this;
  }

  public BananaConfig setCurrentLimit(int bananaLimit) {
    this.bananaLimit = Optional.of(bananaLimit);
    return this;
  }
}
