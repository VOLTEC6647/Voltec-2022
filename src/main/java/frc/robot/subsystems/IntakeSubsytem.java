// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
// import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakeSubsytem extends SubsystemBase {
  /** Creates a new IntakeSubsytem. */
  private static CANSparkMax intakeMotor = new CANSparkMax(IntakeConstants.intakeMotorID, MotorType.kBrushless);
  private Solenoid intakeIn = new Solenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.intakeIn);
  private Solenoid intakeOut = new Solenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.intakeOut);
  // private RelativeEncoder indexerEncoder;
  
  public IntakeSubsytem() {
    intakeMotor.restoreFactoryDefaults();
    // indexerEncoder = intakeMotor.getEncoder();
  }

    
  public void toggleIntake(){
    if (intakeIn.get()) {
      intakeIn.set(false);
      intakeOut.set(true);

    }else {
      intakeIn.set(true);
      intakeOut.set(false);    
    }
  }

  public void intakeOutF(){
    intakeOut.set(true);
    intakeIn.set(false);
  }

  public void intakeInF(){
    intakeIn.set(true);
    intakeOut.set(false);
  }
  public void setIntakeMotorSpeed(double speed) {
    intakeMotor.set(speed*0.5);
  }

  // public void publishData(){
  //   SmartDashboard.putNumber("IndexerPosition", indexerEncoder.getPosition());
  //   SmartDashboard.putNumber("IndexerEncoder", indexerEncoder.getVelocity());
  // }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // publishData();
  }

}
