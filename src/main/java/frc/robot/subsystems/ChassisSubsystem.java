// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ChasisConstants;

public class ChassisSubsystem extends SubsystemBase {
  private WPI_TalonFX frontLeft = new WPI_TalonFX(ChasisConstants.frontLeftID);
  private WPI_TalonFX frontRight = new WPI_TalonFX(ChasisConstants.frontRightID);
  private WPI_TalonFX rearLeft = new WPI_TalonFX(ChasisConstants.rearLeftID);
  private WPI_TalonFX rearRight = new WPI_TalonFX(ChasisConstants.rearRightID);
  private DoubleSolenoid reduction  = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ChasisConstants.HighGearSolenoid, ChasisConstants.LowGearSolenoid);
  private DifferentialDrive chasis; 
  private double leftSpeed, rightSpeed;

  /** Creates a new Chasis. */
  public ChassisSubsystem() {
    rearLeft.follow(frontLeft);
    rearRight.follow(frontRight);
    frontLeft.setInverted(true);
    frontRight.setInverted(false);
    rearLeft.setInverted(InvertType.FollowMaster);
    rearRight.setInverted(InvertType.FollowMaster);
    chasis = new DifferentialDrive(frontLeft, frontRight);

  }
  //obtener error de los encoders a traves de la velocidad
  public void publishData(){
    SmartDashboard.putNumber("LeftSpeed", frontLeft.get());
    SmartDashboard.putNumber("RightSpeed", frontRight.get());
    SmartDashboard.putNumber("LeftSpeedN", leftSpeed);
    SmartDashboard.putNumber("RightSpeedN", rightSpeed);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    publishData();
  }

  public void TankDrive(double leftSpeed, double rightSpeed){
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    chasis.tankDrive(leftSpeed, rightSpeed);

  }

  public void ArcadeDrive(double linearSpeed, double rotSpeed){
    chasis.arcadeDrive(linearSpeed, rotSpeed);
  }
  public void toggleReduccion(){
    reduction.toggle();
  }
}
