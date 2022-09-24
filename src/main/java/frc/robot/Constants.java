// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
    public static final class ChasisConstants{
        public static final int frontLeftID = 8; 
        public static final int frontRightID = 9;
        public static final int rearLeftID = 6;
        public static final int rearRightID = 1;
        public static final int HighGearSolenoid = 0;
        public static final int LowGearSolenoid = 1;
    }
    
    public static final class ClimberConstants {
        public static final int climberMotor = 4;

        //valores en 0 de forward limit y reverse limit
        public static final int forwardLimit = 165;
        public static final int reverseLimit = 10;

        public static final double forwardSpeed = .8;
        public static final double reverseSpeed = -.8;
    }

    public static final class ShooterConstants {
        public static final int shooterID = 5;

        // Shooter PID
        public static final double shooterkP = 5e-5;
        public static final double shooterkI = 1e-7;
        public static final double shooterkD = 1e-3;
        public static final double shooterkIz = 0;
        public static final double shooterkFF = 1.5e-4;

        public static int shooterFender = 3500;
        public static int backSpinFender = 1700;
        public static final int shooter1MeterFender = 4000;
        public static final int shooter1backSpinFender = 3500;
        public static int velocityTolerance = 25;
    }

    public static final class HoodConstants {
        public static int hoodID = 10;

        public static final int forwardLimit = 0;
        public static final int reverseLimit = 10;

        public static final double speed = .01;
    }

    public static final class DeliveryConstants {
        public static final int deliveryID = 3;
        // Delivery PID
        public static final double deliverykP = 0.7;
        public static final double deliverykI = 0;
        public static final double deliverykD = 0.5;
        public static final double deliverykIz = 0;
        public static final double deliverykFF = 0.0000;

        public static double deliveryRot = 4;
        public static double errorTolerance = .2;
    }

    public static final class OIConstants {
        public static final int KDriverControllerPort = 0;
        public static final int KDriverControllerPort1 = 1;
    }
    public static final class IntakeConstants{
        public static final int intakeMotorID = 1;
        public static final double intakeSpeed = 0.6;
        public static final int intakeIn = 2;
        public static final int intakeOut = 3;
    }
    
    public static final class VisionConstants
    {
        public static final double kpAim = -0.03;
        public static final double kpDistance = -0.06; 
        public static final double min_aim_command = 0.02;
        public static final double steeringAdjust = 0.0;
    }
}
