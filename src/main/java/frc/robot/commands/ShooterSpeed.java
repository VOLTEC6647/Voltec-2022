// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShooterSpeed extends CommandBase {
  private final ShooterSubsystem shooter;
  private final int shooterSpeed, cSpinSpeed;
  
  public ShooterSpeed(int shooterSpeed, int cSpinSpeed, ShooterSubsystem shooter) {
    this.shooter = shooter;
    this.shooterSpeed = shooterSpeed;
    this.cSpinSpeed = cSpinSpeed;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setShooterVelocity(shooterSpeed);
    shooter.setCSpinVelocity(cSpinSpeed);
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shooter.isInTolerance();
  }
}
