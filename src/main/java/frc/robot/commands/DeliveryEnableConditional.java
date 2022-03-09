// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DeliverySubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class DeliveryEnableConditional extends CommandBase {
  private DeliverySubsystem delivery;
  private double speed;
  private ShooterSubsystem shooter;
  /** Creates a new DeliveryEnable. */
  public DeliveryEnableConditional(double speed, DeliverySubsystem delivery,ShooterSubsystem shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.delivery = delivery;
    this.speed = speed;
    this.shooter = shooter;
    addRequirements(delivery);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(shooter.isInTolerance()&&delivery.getSpeed()!=speed){
      delivery.setSpeed(speed);
    }else{
      delivery.setSpeed(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    delivery.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
