// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.Vision;

public class MoveTowardsGoal extends CommandBase {
  private final Vision vision;
  private final ChassisSubsystem chasis;

  double kpAim;
  double min_aim_command;
  double steeringAdjust;
  double kpDistance;

  /** Creates a new MoveTowardGoal. */
  public MoveTowardsGoal(Vision vision, ChassisSubsystem chasis, double kp, double min_command, double steeringAdjust, double kpDistance) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.vision = vision;
    this.chasis = chasis;
    this.kpAim = kp;
    this.min_aim_command = min_command;
    this.steeringAdjust = steeringAdjust;
    this.kpDistance = kpDistance;
    addRequirements(vision, chasis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    vision.aimingNrange(chasis, kpAim, kpDistance, min_aim_command, steeringAdjust);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    steeringAdjust = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}