package crewmate.lib.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.geometry.Twist2d;

class GeomUtilTest {

  @Test
  void toTransform2d_Translation_ReturnsCorrectTransform() {
    Translation2d translation = new Translation2d(1.0, 2.0);
    Transform2d transform = GeomUtil.toTransform2d(translation);
    assertEquals(new Transform2d(translation, new Rotation2d()), transform);
  }

  @Test
  void toTransform2d_XY_ReturnsCorrectTransform() {
    Transform2d transform = GeomUtil.toTransform2d(1.0, 2.0);
    assertEquals(new Transform2d(1.0, 2.0, new Rotation2d()), transform);
  }

  @Test
  void toTransform2d_Rotation_ReturnsCorrectTransform() {
    Rotation2d rotation = new Rotation2d(Math.PI);
    Transform2d transform = GeomUtil.toTransform2d(rotation);
    assertEquals(new Transform2d(new Translation2d(), rotation), transform);
  }

  @Test
  void toTransform2d_Pose_ReturnsCorrectTransform() {
    Pose2d pose = new Pose2d(1.0, 2.0, new Rotation2d(Math.PI));
    Transform2d transform = GeomUtil.toTransform2d(pose);
    assertEquals(new Transform2d(pose.getTranslation(), pose.getRotation()), transform);
  }

  @Test
  void inverse_Pose_ReturnsInversePose() {
    Pose2d pose = new Pose2d(1.0, 2.0, new Rotation2d(Math.PI));
    Pose2d inversePose = GeomUtil.inverse(pose);
    assertEquals(new Pose2d(1.0, 2.0, new Rotation2d(-Math.PI)), inversePose);
  }

  @Test
  void toPose2d_Transform_ReturnsCorrectPose() {
    Transform2d transform = new Transform2d(1.0, 2.0, new Rotation2d(Math.PI));
    Pose2d pose = GeomUtil.toPose2d(transform);
    assertEquals(new Pose2d(transform.getTranslation(), transform.getRotation()), pose);
  }

  @Test
  void toPose2d_Translation_ReturnsCorrectPose() {
    Translation2d translation = new Translation2d(1.0, 2.0);
    Pose2d pose = GeomUtil.toPose2d(translation);
    assertEquals(new Pose2d(translation, new Rotation2d()), pose);
  }

  @Test
  void toPose2d_Rotation_ReturnsCorrectPose() {
    Rotation2d rotation = new Rotation2d(Math.PI);
    Pose2d pose = GeomUtil.toPose2d(rotation);
    assertEquals(new Pose2d(new Translation2d(), rotation), pose);
  }

  @Test
  void multiply_Twist_ReturnsMultipliedTwist() {
    Twist2d twist = new Twist2d(1.0, 2.0, Math.PI);
    Twist2d multipliedTwist = GeomUtil.multiply(twist, 2.0);
    assertEquals(new Twist2d(2.0, 4.0, 2*Math.PI), multipliedTwist);
  }

  @Test
  void toTwist2d_ChassisSpeeds_ReturnsCorrectTwist() {
    ChassisSpeeds speeds = new ChassisSpeeds(1.0, 2.0, Math.PI);
    Twist2d twist = GeomUtil.toTwist2d(speeds);
    assertEquals(new Twist2d(1.0, 2.0, Math.PI), twist);
  }

  @Test
  void withTranslation_Pose_ReturnsUpdatedPose() {
    Pose2d pose = new Pose2d(1.0, 2.0, new Rotation2d(Math.PI));
    Translation2d newTranslation = new Translation2d(3.0, 4.0);
    Pose2d newPose = GeomUtil.withTranslation(pose, newTranslation);
    assertEquals(new Pose2d(newTranslation, pose.getRotation()), newPose);
  }

  @Test
  void withRotation_Pose_ReturnsUpdatedPose() {
    Pose2d pose = new Pose2d(1.0, 2.0, new Rotation2d(Math.PI));
    Rotation2d newRotation = new Rotation2d(Math.PI/2);
    Pose2d newPose = GeomUtil.withRotation(pose, newRotation);
    assertEquals(new Pose2d(pose.getTranslation(), newRotation), newPose);
  }
}
