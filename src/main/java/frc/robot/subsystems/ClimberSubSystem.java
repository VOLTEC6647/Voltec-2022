package frc.robot.subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//FILES
import frc.robot.Constants.ClimberConstants;

//Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ClimberSubSystem extends SubsystemBase 
{
  private CANSparkMax climberM = new CANSparkMax(ClimberConstants.climberMotor, MotorType.kBrushless);
  private RelativeEncoder climberEncoder;

  public ClimberSubSystem() 
  {
    climberM.restoreFactoryDefaults();

    // Pone softLimits
    climberM.setSoftLimit(SoftLimitDirection.kForward, ClimberConstants.forwardLimit);
    climberM.setSoftLimit(SoftLimitDirection.kReverse, ClimberConstants.reverseLimit);
    climberM.enableSoftLimit(SoftLimitDirection.kReverse, true);
    climberM.enableSoftLimit(SoftLimitDirection.kForward, true);

    climberEncoder = climberM.getEncoder();
  }
  
  public void publishData(){
    SmartDashboard.putNumber("ClimberPosition", climberEncoder.getPosition());
  }

  @Override
  public void periodic() 
  {
    publishData();
  }

  public void setClimberPercentage(double percentage) 
  {
    climberM.set(percentage);
  }
}