// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HoodSubsystem;

public class MoveHood extends CommandBase {

  private HoodSubsystem hood;

  private double speed;
  /** Creates a new MoveHood. */
  public MoveHood(double speed, HoodSubsystem hood) {
    addRequirements(hood);

    this.speed = speed;
    this.hood = hood;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hood.setHoodPercentage(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hood.setHoodPercentage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
