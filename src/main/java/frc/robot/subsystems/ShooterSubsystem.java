// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//HOLAAAAAA

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  private CANSparkMax shooter = new CANSparkMax(ShooterConstants.shooterID, MotorType.kBrushless);
  private CANSparkMax cSpin = new CANSparkMax(ShooterConstants.counterSpinID, MotorType.kBrushless);
  private SparkMaxPIDController shooterPID, cSpinPID;
  private RelativeEncoder shooterEncoder, cSpinEncoder;
  private int shooterSetpoint, counterSpinSetpoint;


  /** Creates a new Shooter. */
  public ShooterSubsystem() {

    shooter.restoreFactoryDefaults();
    cSpin.restoreFactoryDefaults();

    shooterPID = shooter.getPIDController();
    cSpinPID =  cSpin.getPIDController();

    shooterEncoder = shooter.getEncoder();
    cSpinEncoder = cSpin.getEncoder();

    shooter.setIdleMode(IdleMode.kCoast);
    cSpin.setIdleMode(IdleMode.kCoast);
    shooterPID.setP(ShooterConstants.shooterkP);
    shooterPID.setI(ShooterConstants.shooterkI);
    shooterPID.setD(ShooterConstants.shooterkD);
    shooterPID.setFF(ShooterConstants.shooterkFF);
    shooterPID.setIZone(ShooterConstants.shooterkIz);

    cSpinPID.setP(ShooterConstants.counterSpinkP);
    cSpinPID.setI(ShooterConstants.counterSpinkI);
    cSpinPID.setD(ShooterConstants.counterSpinkD);
    cSpinPID.setFF(ShooterConstants.counterSpinkFF);
    cSpinPID.setIZone(ShooterConstants.counterSpinkIz);
  }

  /**
   * Sets the velocity for the Shooter. 
   *
   * @param velSetpoint Shooter NEO RPM.
   */
  public void setShooterVelocity(int velSetpoint){
    shooterSetpoint = velSetpoint;
    shooterPID.setReference(velSetpoint, ControlType.kVelocity);
  }

  /**
   * Sets the velocity for the counter backSpin. 
   *
   * @param velSetpoint Shooter NEO RPM.
   */
  public void setCSpinVelocity(int velSetpoint){
    counterSpinSetpoint = velSetpoint;
    cSpinPID.setReference(velSetpoint, ControlType.kVelocity);
  }

  public boolean isInTolerance(){
    return Math.abs(shooterEncoder.getVelocity()-shooterSetpoint)<ShooterConstants.velocityTolerance && Math.abs(cSpinEncoder.getVelocity()-counterSpinSetpoint)<ShooterConstants.velocityTolerance;
  }

 //detener motor 
  public void stopMotors(){
    shooter.set(0);
    shooter.set(0);
  }

  //obtener error de los encoders a traves de la velocidad
  public void publishData(){
    SmartDashboard.putNumber("ShooterVelocity", shooterEncoder.getVelocity());
    SmartDashboard.putNumber("CounterSpinVelocity", cSpinEncoder.getVelocity());
  }

  @Override
  public void periodic() {
    publishData();
    // This method will be called once per scheduler run
  }

}