package crewmate.lib.motor;

import java.util.Optional;

public class MotorConfig {
  enum Type {
    BRUSHED,
    BRUSHLESS
  }

  enum ControlType {
    POSITION,
    VELOCITY,
    CURRENT,
    DUTYCYCLE
  }

  int canID;
  Type motorType;
  Optional<Double> currentLimit;
  Optional<Double> p;
  Optional<Double> i;
  Optional<Double> d;
  Optional<Boolean> reversed;
  Optional<Double> positionConversionFactor;
  Optional<Double> velocityConversionFactor;
}
