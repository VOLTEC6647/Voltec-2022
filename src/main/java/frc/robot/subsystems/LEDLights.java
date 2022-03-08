// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LedConstants;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class LEDLights extends SubsystemBase {
  private static Spark m_blinkin = null;
  
  /** Creates a new LEDLights. */
  public LEDLights() {
    m_blinkin = new Spark(LedConstants.PWMPort); //Change
    heartbeat_blue();
  }

  public void set (double value)
  {
    if((value <= 1) && (value >= -1))
    {
      m_blinkin.set(value);
    }
  }

  private void heartbeat_blue() {
    set(-0.23);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
