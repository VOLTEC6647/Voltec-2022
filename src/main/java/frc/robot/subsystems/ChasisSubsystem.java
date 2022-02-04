// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ChasisConstants;

public class ChasisSubsystem extends SubsystemBase {
  WPI_TalonFX frontLeft = new WPI_TalonFX(ChasisConstants.frontLeftID);
  WPI_TalonFX frontRight = new WPI_TalonFX(ChasisConstants.frontRightID);
  WPI_TalonFX rearLeft = new WPI_TalonFX(ChasisConstants.rearLeftID);
  WPI_TalonFX rearRight = new WPI_TalonFX(ChasisConstants.rearRightID);
  /** Creates a new Chasis. */
  public ChasisSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void TankDrive(){

  }

  public void ArcadeDrive(){

  }
}
