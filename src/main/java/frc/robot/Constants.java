// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class ShooterConstants{
        public static final int shooterID = 1;
        public static final int counterSpinID = 3;

        //Shooter PID
        public static final double shooterkP = 6e-5; 
        public static final double shooterkI = 0;
        public static final double shooterkD = 0; 
        public static final double shooterkIz = 0; 
        public static final double shooterkFF = 0.000015; 

        //CounterSpin PID
        public static final double counterSpinkP = 6e-5; 
        public static final double counterSpinkI = 0;
        public static final double counterSpinkD = 0; 
        public static final double counterSpinkIz = 0; 
        public static final double counterSpinkFF = 0.000015; 

        public static final int shooterFender = 2500; 
        public static final int backSpinFender = 2500; 
        public static final int shooter1MeterFender = 3000; 
        public static final int shooter1backSpinFender = 3000; 
        public static final int velocityTolerance = 50; 
    }

    public static final class DeliveryConstants{
        public static final int deliveryID = 0;
        //Delivery PID
        public static final double deliverykP = 6e-5; 
        public static final double deliverykI = 0;
        public static final double deliverykD = 0; 
        public static final double deliverykIz = 0; 
        public static final double deliverykFF = 0.000015;

        public static final int deliveryRot = 1; 
        public static final double errorTolerance = .5; 
        
    }
    public static final class OIConstants{
        public static final int KDriverControllerPort = 0;
    }
}
