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


  public Vision() {
    m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

  }

  @Override
  public void periodic() {
    /*
     * SmartDashboard.putNumber("LimelightX", getTX());
     * SmartDashboard.putNumber("LimelightY", getTX());
     * SmartDashboard.putNumber("LimelightArea", getTA());
     * SmartDashboard.putNumber("Distance", getDistance());
     */
  }

  public void moveTowardsgoal(double kp, double min_command, double steeringAdjust) {

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