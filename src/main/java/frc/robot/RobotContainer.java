// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.commands.DeliveryEnable;
import frc.robot.commands.MoveClimber;
import frc.robot.commands.ShooterSpeed;
import frc.robot.commands.intakeIn;
import frc.robot.commands.intakeOut;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClimberSubSystem;
import frc.robot.subsystems.DeliverySubsystem;
import frc.robot.subsystems.IntakeSubsytem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.utils.XboxControllerUpgrade;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DeliverySubsystem delivery = new DeliverySubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final IntakeSubsytem intake = new IntakeSubsytem();
    private final ClimberSubSystem climber = new ClimberSubSystem();

    private final XboxControllerUpgrade joystick1 = new XboxControllerUpgrade(OIConstants.KDriverControllerPort, 0.2);
    private final XboxControllerUpgrade joystick2 = new XboxControllerUpgrade(OIConstants.KDriverControllerPort1, 0.2);

    private final ChassisSubsystem chassis = new ChassisSubsystem();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        chassis.setDefaultCommand(
                new RunCommand(() -> chassis.TankDrive(joystick1.getLeftY(), joystick1.getRightY()), chassis));
        configureButtonBindings();
        /*
         * chassis.setDefaultCommand(
         * new RunCommand(() -> chassis.ArcadeDrive(joystick1.getLeftY(),
         * -joystick1.getRightX()), chassis));
         * configureButtonBindings();
         */

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // final JoystickButton dpadUp = new JoystickButton(joystick1, 5);
        // final JoystickButton dpadDown = new JoystickButton(joystick1, 7);
        // Shooting from Fender

        new JoystickButton(joystick2, Button.kA.value)
                .whileHeld(
                        new SequentialCommandGroup(
                                new ShooterSpeed(ShooterConstants.shooterFender,
                                        shooter),
                                new DeliveryEnable(0.7, delivery)))
                .whenReleased(new ShooterSpeed(0, shooter));

        new JoystickButton(joystick2, Button.kB.value)
                .whileHeld(
                        new SequentialCommandGroup(
                                new ShooterSpeed(ShooterConstants.shooter1MeterFender,
                                        shooter),
                                new DeliveryEnable(0.7, delivery)))
                .whenReleased(new ShooterSpeed(0, shooter));

        new JoystickButton(joystick2, Button.kY.value)
                .whileHeld(
                new ParallelCommandGroup(
                        new ShooterSpeed(1800, 
                        shooter),
                        new DeliveryEnable(0.3, delivery)))
                .whenReleased(new ShooterSpeed(0, shooter));
        joystick2.Dpad.Down.whileHeld(
                new MoveClimber(ClimberConstants.reverseSpeed, climber));

        joystick2.Dpad.Up.whileHeld(
                new MoveClimber(ClimberConstants.forwardSpeed, climber));

        new JoystickButton(joystick1, Button.kY.value)
                .whenPressed(new InstantCommand(() -> chassis.toggleReduccion()));

        new JoystickButton(joystick2, Button.kX.value)
                .whenPressed(new InstantCommand(() -> intake.toggleIntake()));
        
        new JoystickButton(joystick1, Button.kRightBumper.value)
                .whileHeld(new InstantCommand(() -> chassis.toggleBrake(false)))
                .whenReleased(new InstantCommand(() -> chassis.toggleBrake(true)));

        joystick2.rightTriggerButton.whileHeld(
                new StartEndCommand(
                () -> intake.setIntakeMotorSpeed(.5),
                () -> intake.setIntakeMotorSpeed(0),
                intake));

        joystick2.leftTriggerButton.whileHeld(
                new StartEndCommand(
                () -> intake.setIntakeMotorSpeed(-.5),
                () -> intake.setIntakeMotorSpeed(0),
                intake));
        new JoystickButton(joystick2, Button.kRightBumper.value)
                .whileHeld(new DeliveryEnable(-0.3, delivery));
        new JoystickButton(joystick1, Button.kLeftBumper.value)
                .whileHeld(new StartEndCommand(() -> chassis.toggleAim(), () -> chassis.toggleAim(), chassis));
        }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new SequentialCommandGroup(
        //Disparar primera pelota
        new SequentialCommandGroup(
            new ShooterSpeed(3600, shooter),
            new DeliveryEnable(0.7, delivery)
        ).withTimeout(3),
        new ShooterSpeed(0, shooter),

        //Ir por segunda pelota
        // new RunCommand(()->intake.toggleIntake()).withInterrupt(intake.intakeOut::get),
        
        new intakeOut(intake).withTimeout(0.5),
        new ParallelCommandGroup( 
            new RunCommand(()->chassis.TankDrive(-.4, -.4), chassis),
            new StartEndCommand(
                () -> intake.setIntakeMotorSpeed(.5),
                () -> intake.setIntakeMotorSpeed(0)
            )
        ).withTimeout(2),
        new RunCommand(()->chassis.TankDrive(0, 0), chassis).withTimeout(.2),
        new intakeIn(intake).withTimeout(0.5),
        // new RunCommand(()->intake.toggleIntake(), intake).withTimeout(0.11),
        // new RunCommand(()->intake.toggleIntake()).withInterrupt(intake.intakeOut::get),
        new RunCommand(()->chassis.TankDrive(.4, .4), chassis).withTimeout(2),
        new RunCommand(()->chassis.TankDrive(0, 0), chassis).withTimeout(.2),
        new SequentialCommandGroup(
            new ShooterSpeed(3600, shooter),
            new DeliveryEnable(0.7, delivery)
        ).withTimeout(4),
        new ShooterSpeed(0, shooter)
    );
  }

  public double getTrigger() {
    return joystick2.getLeftTriggerAxis();
  }

  public boolean isTrigger() {
    return joystick2.leftTriggerButton.get();
  }
}
