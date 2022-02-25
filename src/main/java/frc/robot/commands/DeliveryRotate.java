// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DeliveryConstants;
import frc.robot.subsystems.DeliverySubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DeliveryRotate extends CommandBase {
  private final DeliverySubsystem delivery;
  private double rotation;

  public DeliveryRotate(double rot, DeliverySubsystem delivery) {
    this.delivery = delivery;
    rotation = rot;
    addRequirements(delivery);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    delivery.resetEncoder();
    delivery.setDeliveryRotation(rotation);
  }
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      delivery.stopRotation();
      delivery.resetEncoder();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return delivery.deliveryError() < DeliveryConstants.errorTolerance;
    }
}
