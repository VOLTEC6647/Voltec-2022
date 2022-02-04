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
  CANSparkMax motor = new CANSparkMax(ClimberConstants.climberMotor, MotorType.kBrushless);

  public ClimberSubSystem() 
  {
    motor.setSoftLimit(SoftLimitDirection.kForward, ClimberConstants.forwardLimit);
    motor.setSoftLimit(SoftLimitDirection.kReverse, ClimberConstants.reverseLimit);
  }

  @Override
  public void periodic() 
  {
  
  }

  public void extendClimber()
  {

  }

  public void retractClimnber()
  {

  }
}