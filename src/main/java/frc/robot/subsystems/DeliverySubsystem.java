// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//HOLAAAAAA

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeliveryConstants;



public class DeliverySubsystem extends SubsystemBase {
  private CANSparkMax delivery = new CANSparkMax(DeliveryConstants.deliveryID, MotorType.kBrushless);
  private SparkMaxPIDController deliveryPID;
  private RelativeEncoder deliveryEncoder;
  private double setPoint;

  /** Creates a new Shooter. */
  public DeliverySubsystem() {

    delivery.restoreFactoryDefaults();


    deliveryPID = delivery.getPIDController();
    deliveryEncoder = delivery.getEncoder();

    delivery.setIdleMode(IdleMode.kBrake);
    deliveryPID.setP(DeliveryConstants.deliverykP);
    deliveryPID.setI(DeliveryConstants.deliverykI);
    deliveryPID.setD(DeliveryConstants.deliverykD);
    deliveryPID.setFF(DeliveryConstants.deliverykFF);
    deliveryPID.setIZone(DeliveryConstants.deliverykIz);
  }


  /**
   * Sets the rotation for the delivery spin. 
   *
   * @param velSetpoint NEO position.
   */
  public void setDeliveryRotation(double rotSetpoint){
    this.setPoint = rotSetpoint;
    deliveryPID.setReference(rotSetpoint, ControlType.kPosition);
  }

  public void stopRotation(){
    delivery.set(0);
    setPoint=0;
    resetEncoder();
  }

  public void resetEncoder(){
    deliveryEncoder.setPosition(0);
  }

  public double deliveryError(){
    return Math.abs(setPoint-deliveryEncoder.getPosition());
  }

  public void publishData(){
    SmartDashboard.putNumber("Indexer position", deliveryEncoder.getPosition());
  }

  public void setSpeed(double speed){
    delivery.set(speed);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    publishData(); 
  }

}
