// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ChasisConstants;

public class ChassisSubsystem extends SubsystemBase {
  WPI_TalonFX frontLeft = new WPI_TalonFX(ChasisConstants.frontLeftID);
  WPI_TalonFX frontRight = new WPI_TalonFX(ChasisConstants.frontRightID);
  WPI_TalonFX rearLeft = new WPI_TalonFX(ChasisConstants.rearLeftID);
  WPI_TalonFX rearRight = new WPI_TalonFX(ChasisConstants.rearRightID);
  DoubleSolenoid reduction  = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ChasisConstants.HighGearSolenoid, ChasisConstants.LowGearSolenoid);
  DifferentialDrive chasis; 

  /** Creates a new Chasis. */
  public ChassisSubsystem() {
    rearLeft.follow(frontLeft);
    rearRight.follow(frontRight);
    frontLeft.setInverted(false);
    frontRight.setInverted(true);
    rearLeft.setInverted(InvertType.FollowMaster);
    rearLeft.setInverted(InvertType.FollowMaster);
    chasis = new DifferentialDrive(frontLeft, frontRight);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void TankDrive(double leftSpeed, double rightSpeed){
    chasis.tankDrive(leftSpeed, rightSpeed);

  }

  public void ArcadeDrive(double linearSpeed, double rotSpeed){
    chasis.arcadeDrive(linearSpeed, rotSpeed);
  }
  public void toggleReduccion(){
    reduction.toggle();
  }
}
