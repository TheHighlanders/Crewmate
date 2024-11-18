package crewmate.lib.util;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;

import static org.junit.jupiter.api.Assertions.*;

public class PoseEstimatorTest {

    @Test
    public void constructor_ValidInput_CreatesInstance() {
        PoseEstimator estimator = new PoseEstimator(new Matrix<>(Nat.N3(), Nat.N1()));
        assertNotNull(estimator);
    }

}
