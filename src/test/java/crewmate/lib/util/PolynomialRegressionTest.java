package crewmate.lib.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PolynomialRegressionTest {

  @Test
  void testConstructor() {
    double[] x = {1, 2, 3, 4, 5};
    double[] y = {2, 4, 6, 8, 10};
    PolynomialRegression regression = new PolynomialRegression(x, y, 1);
    assertNotNull(regression);
  }

  @Test
  void testPredictWithDegree1() {
    double[] x = {1, 2, 3, 4, 5};
    double[] y = {2, 4, 6, 8, 10};
    PolynomialRegression regression = new PolynomialRegression(x, y, 1);
    assertEquals(12, regression.predict(6), 0.001);
  }

  @Test
  void testPredictWithDegree2() {
    double[] x = {1, 2, 3, 4, 5};
    double[] y = {1, 4, 9, 16, 25};
    PolynomialRegression regression = new PolynomialRegression(x, y, 2);
    assertEquals(36, regression.predict(6), 0.001);
  }

  @Test
  void testPredictWithEmptyData() {
    double[] x = {};
    double[] y = {};
    assertThrows(IllegalArgumentException.class, () -> new PolynomialRegression(x, y, 1));
  }

  @Test
  void testPredictWithInvalidDegree() {
    double[] x = {1, 2, 3, 4, 5};
    double[] y = {2, 4, 6, 8, 10};
    assertThrows(IllegalArgumentException.class, () -> new PolynomialRegression(x, y, 0));
    assertThrows(IllegalArgumentException.class, () -> new PolynomialRegression(x, y, -1));
  }
}
