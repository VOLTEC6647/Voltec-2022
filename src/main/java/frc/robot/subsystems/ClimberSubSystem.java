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
  /*
  El boolean completely servirá para saber si se quiere extender/retraer por completo
  Por ejemplo, presionando un boton. Esto para hacer más eficiente y rapido 
  La escalada. Si el bool es falso, se usará el input directo del botón, es
  Decir, si se presiona el boton de subir/bajar, este subira lo indicado por
  El tiempo presionado, y viceversa. 
  */
  public void extendClimber(boolean completely) 
  {
    if(completely) //Extender por completo
    {
      
    }
    else //Mientras que el boton esté presionado, el robot se extenderá (siempre y cuando no llegue al limite)
    {

    }
  }

  public void retractClimnber(boolean completely)
  {
    if(completely) //Retraer por completo
    {
      
    }
    else //Mientras que el boton esté presionado, el robot se retraerá (siempre y cuando no llegue al limite)
    {

    }
  }
}