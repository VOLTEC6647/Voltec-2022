// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HoodConstants;

public class HoodSubsystem extends SubsystemBase {

  private CANSparkMax hoodMotor = new CANSparkMax(HoodConstants.hoodID, MotorType.kBrushless);
  private RelativeEncoder hoodEncoder;


  /** Creates a new Hood. */
  public HoodSubsystem() {
    hoodMotor.restoreFactoryDefaults();

    hoodMotor.setSoftLimit(SoftLimitDirection.kForward, HoodConstants.forwardLimit);
    hoodMotor.setSoftLimit(SoftLimitDirection.kReverse, HoodConstants.reverseLimit);
    hoodMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    hoodMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);

    hoodEncoder = hoodMotor.getEncoder();
  }

  public void publishData(){
    SmartDashboard.putNumber("HoodPosition", hoodEncoder.getPosition());
  }
  
  @Override
  public void periodic() {
    publishData();
  }

  public void setHoodPercentage(double percentage) 
  {
    hoodMotor.set(percentage);
  }
}
