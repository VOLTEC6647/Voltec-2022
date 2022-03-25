// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ChasisConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.utils.vision.LimelightCamera;
import frc.robot.utils.vision.LimelightData.Data;

public class ChassisSubsystem extends SubsystemBase {
  private WPI_TalonFX frontLeft = new WPI_TalonFX(ChasisConstants.frontLeftID);
  private WPI_TalonFX frontRight = new WPI_TalonFX(ChasisConstants.frontRightID);
  private WPI_TalonFX rearLeft = new WPI_TalonFX(ChasisConstants.rearLeftID);
  private WPI_TalonFX rearRight = new WPI_TalonFX(ChasisConstants.rearRightID);
  // private DoubleSolenoid reduction  = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ChasisConstants.HighGearSolenoid, ChasisConstants.LowGearSolenoid);
  private DifferentialDrive chasis; 
  private double leftSpeed, rightSpeed;
  private Solenoid forwardSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ChasisConstants.HighGearSolenoid);
  private Solenoid backwardSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ChasisConstants.LowGearSolenoid);
  
  // Visión:
  private final LimelightCamera limelight;

  private final Object lock = new Object();
  private final Notifier notifier;
  private boolean aiming = false, firstRun = true;
  // ...

  /** Creates a new Chasis. */
  public ChassisSubsystem() {
    rearLeft.follow(frontLeft);
    rearRight.follow(frontRight);
    frontLeft.setInverted(true);
    frontRight.setInverted(false);
    rearLeft.setInverted(InvertType.FollowMaster);
    rearRight.setInverted(InvertType.FollowMaster);
	chasis = new DifferentialDrive(frontLeft, frontRight);
	
	// Inicialización de la Limelight
	limelight = new LimelightCamera("limelight");

	// Subrutina para visión
	notifier = new Notifier(() -> {
		synchronized(lock) {
			if (firstRun) {
				Thread.currentThread().setName("limelight");
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
				firstRun = false;
			}

			if (!aiming)
				return;

			synchronized(ChassisSubsystem.this) {
				if(!limelight.isTargetFound())
					return;

				double tx = limelight.getData(Data.HORIZONTAL_OFFSET),
					ty = limelight.getData(Data.VERTICAL_OFFSET);

				double kpAim = VisionConstants.kpAim, kpDistance = VisionConstants.kpDistance,
					min_aim_command = VisionConstants.min_aim_command;

				var steeringAdjust = tx > 1 ? (kpAim * -tx - min_aim_command) :
					(tx < -1 ? (kpAim * -tx + min_aim_command) : 0.0);
				var distanceAdjust = kpDistance * ty;
				
				TankDrive(-steeringAdjust + distanceAdjust, steeringAdjust + distanceAdjust);
			}
		}
	});

	notifier.startPeriodic(0.01); // Iniciar subrutina a 10 ms por ciclo.
  }

  //obtener error de los encoders a traves de la velocidad
  public void publishData(){
    SmartDashboard.putNumber("LeftSpeed", frontLeft.get());
    SmartDashboard.putNumber("RightSpeed", frontRight.get());
    SmartDashboard.putNumber("LeftSpeedN", leftSpeed);
    SmartDashboard.putNumber("RightSpeedN", rightSpeed);
    SmartDashboard.putNumber("RightEncoderFront", frontRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("LeftEncoderFront", frontLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("RightEncoderBack", rearRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("LeftEncoderBack", rearLeft.getSelectedSensorPosition());
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
  
  public void toggleReduccion() {
    forwardSolenoid.set(!forwardSolenoid.get());
	backwardSolenoid.set(forwardSolenoid.get());
  }

  public void toggleAim() {
	aiming = !aiming;
  }

  public boolean isAiming() {
	return aiming;
  }
}
