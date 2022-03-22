// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  public final NetworkTable m_limelightTable;
  private double tx, ty, ta;

  private ChassisSubsystem chasis;

  public Vision() {
    m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

  }

  @Override
  public void periodic() {
  }

  //MUST CHECK
  public void aimingNrange(double kpAim, double kpDistance, double min_aim_command, double steeringAdjust) {
    double headingError = -getTX();
    double distanceError = -getTY();

    if (tx > 1) {
      steeringAdjust = kpAim * headingError - min_aim_command;
    } else if (tx < -1) {
      steeringAdjust = kpAim * headingError + min_aim_command;
    }

    double distance_Adjust = kpDistance * distanceError;

    chasis.TankDrive(-(steeringAdjust + distance_Adjust), steeringAdjust + distance_Adjust);
  }

  public double getTX() {
    tx = m_limelightTable.getEntry("tx").getDouble(0);
    return tx;
  }

  public double getTY() {
    ty = m_limelightTable.getEntry("ty").getDouble(0);
    return ty;
  }

  public double getTA() {
    ta = m_limelightTable.getEntry("ta").getDouble(0);
    return ta;
  }
}