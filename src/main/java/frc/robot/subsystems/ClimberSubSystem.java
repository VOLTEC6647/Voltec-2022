package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//FILES
import frc.robot.Constants.ClimberConstants;

//Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ClimberSubSystem extends SubsystemBase 
{
  CANSparkMax climberM = new CANSparkMax(ClimberConstants.climberMotor, MotorType.kBrushless);

  public ClimberSubSystem() 
  {
    climberM.restoreFactoryDefaults();

    climberM.setSoftLimit(SoftLimitDirection.kForward, ClimberConstants.forwardLimit);
    climberM.setSoftLimit(SoftLimitDirection.kReverse, ClimberConstants.reverseLimit);
  }

  @Override
  public void periodic() 
  {
  
  }

  public void setClimberPercentage(double percentage) 
  {
    climberM.set(percentage);    
  }

  public void retractClimnber()
  {

  }
}