// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.DeliveryConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.DeliveryRotate;
import frc.robot.commands.IntakeMotorSpeed;
import frc.robot.commands.MoveClimber;
import frc.robot.commands.ShooterSpeed;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClimberSubSystem;
import frc.robot.subsystems.DeliverySubsystem;
import frc.robot.subsystems.IntakeSubsytem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.utils.XboxControllerUpgrade;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
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

  private final ChassisSubsystem chassis = new ChassisSubsystem();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    chassis.setDefaultCommand( new RunCommand(() ->
    chassis.TankDrive(joystick1.getLeftY(), joystick1.getRightY()), chassis));
    configureButtonBindings();
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

    new JoystickButton(joystick1, Button.kA.value)
        .whenHeld(
            new SequentialCommandGroup(
                new ShooterSpeed(ShooterConstants.shooterFender, ShooterConstants.backSpinFender, shooter),
                new DeliveryRotate(DeliveryConstants.deliveryRot, delivery),
                new WaitUntilCommand(shooter::isInTolerance),
                new DeliveryRotate(DeliveryConstants.deliveryRot, delivery)))
        .whenReleased(new ShooterSpeed(0, 0, shooter));

      new JoystickButton(joystick1, Button.kB.value)
        .whenHeld(
            new SequentialCommandGroup(
                new ShooterSpeed(ShooterConstants.shooter1MeterFender, ShooterConstants.shooter1backSpinFender, shooter),
                new DeliveryRotate(DeliveryConstants.deliveryRot, delivery),
                new WaitUntilCommand(shooter::isInTolerance),
                new DeliveryRotate(DeliveryConstants.deliveryRot, delivery)))
      .whenReleased(new ShooterSpeed(0, 0, shooter));
      
    joystick1.Dpad.Down.whenPressed(
        new MoveClimber(ClimberConstants.reverseSpeed, climber));

    joystick1.Dpad.Up.whenPressed(
        new MoveClimber(ClimberConstants.forwardSpeed, climber));

    new JoystickButton(joystick1, Button.kY.value)
      .whenPressed(() -> chassis.toggleReduccion());

      new JoystickButton(joystick1, Button.kX.value)
      .whenPressed(() -> intake.toggleIntake());

    joystick1.rightTriggerButton.whenPressed(new IntakeMotorSpeed(joystick1.getRightTriggerAxis(), intake));
    joystick1.leftTriggerButton.whenPressed(new IntakeMotorSpeed(-joystick1.getRightTriggerAxis(), intake));
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new DeliveryRotate(0, delivery);
  }
}
