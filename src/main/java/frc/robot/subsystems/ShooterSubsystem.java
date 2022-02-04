// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//HOLAAAAAA

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;


public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax delivery = new CANSparkMax(ShooterConstants.deliveryID, MotorType.kBrushless);
  CANSparkMax shooter = new CANSparkMax(ShooterConstants.shooterID, MotorType.kBrushless);
  CANSparkMax counterspin = new CANSparkMax(ShooterConstants.counterspinID, MotorType.kBrushless);
  //holiwis
  /** Creates a new Shooter. */
  public ShooterSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
