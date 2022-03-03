// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;

public class ChassisTankDriveControl extends CommandBase {
  private final ChassisSubsystem chassis;
  private final double left;
  private final double right;

  /** Creates a new ChassisControl. */
  public ChassisTankDriveControl(ChassisSubsystem chassis, double left, double right) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.chassis = chassis;
    this.left = left;
    this.right = right;
    addRequirements(chassis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    chassis.TankDrive(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
