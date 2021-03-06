// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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

  private DifferentialDrive chasis;
  private double leftSpeed, rightSpeed;
  private Solenoid forwardSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ChasisConstants.HighGearSolenoid);
  private Solenoid backwardSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ChasisConstants.LowGearSolenoid);

  // Visión:
  private final LimelightCamera limelight;

  private final Object lock = new Object(); // Solamente para "pedir permiso" para correr el bloque de código
  private final Notifier notifier;
  private boolean aiming = false, firstRun = true;

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
      // Hace que esté limitado a un solo thread
      synchronized (lock) {
        if (firstRun) {
          Thread.currentThread().setName("limelight");
          Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
          firstRun = false;
        }

        if (!aiming)
          return;

        synchronized (ChassisSubsystem.this) {
          if (!limelight.isTargetFound())
            return;
          //Saca los datos de la visión en X y Y
          double tx = limelight.getData(Data.HORIZONTAL_OFFSET),
              ty = limelight.getData(Data.VERTICAL_OFFSET);

          //Inicializa los valores de vision
          double kpAim = VisionConstants.kpAim, kpDistance = VisionConstants.kpDistance,
              min_aim_command = VisionConstants.min_aim_command;
          
          //Calcula el ajuste de la distancia y angulo
          var steeringAdjust = tx > 1 ? (kpAim * -tx - min_aim_command)
              : (tx < -1 ? (kpAim * -tx + min_aim_command) : 0.0);
          var distanceAdjust = kpDistance * ty;

          //Aplica los ajustes
          TankDrive(-steeringAdjust + distanceAdjust, steeringAdjust + distanceAdjust);
        }
      }
    });

    notifier.startPeriodic(0.01); // Iniciar subrutina a 10 ms por ciclo.
    
    // Set CoastMode
    rearLeft.setNeutralMode(NeutralMode.Coast);
    rearRight.setNeutralMode(NeutralMode.Coast);
    frontLeft.setNeutralMode(NeutralMode.Coast);
    frontRight.setNeutralMode(NeutralMode.Coast);

  }

  // obtener error de los encoders a traves de la velocidad
  public void publishData() {
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
    publishData();
  }

  public void TankDrive(double leftSpeed, double rightSpeed) {
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    chasis.tankDrive(leftSpeed, rightSpeed);
  }

  public void ArcadeDrive(double linearSpeed, double rotSpeed) {
    chasis.arcadeDrive(linearSpeed, rotSpeed);
  }

  public void toggleReduccion() {
    var current = forwardSolenoid.get();
    forwardSolenoid.set(!current);
    backwardSolenoid.set(current);
  }

  public void toggleAim() {
    aiming = !aiming;
  }

  //No se usa pero se deja por si se necesita
  public boolean isAiming() {
    return aiming;
  }

  public void toggleBrake(boolean brake)
  {
    if(brake)
    {
      rearLeft.setNeutralMode(NeutralMode.Coast);
      rearRight.setNeutralMode(NeutralMode.Coast);
      frontLeft.setNeutralMode(NeutralMode.Coast);
      frontRight.setNeutralMode(NeutralMode.Coast);
    }
    else if(!brake)
    {
      rearLeft.setNeutralMode(NeutralMode.Brake);
      rearRight.setNeutralMode(NeutralMode.Brake);
      frontLeft.setNeutralMode(NeutralMode.Brake);
      frontRight.setNeutralMode(NeutralMode.Brake);
    }
  }
}
