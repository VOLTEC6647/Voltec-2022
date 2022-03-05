// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.DeliveryConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.DeliveryRotate;
import frc.robot.commands.IntakeMotorSpeed;
import frc.robot.commands.MoveClimber;
import frc.robot.commands.ShooterSpeed;
import frc.robot.oi.JController;
import frc.robot.oi.JHand;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClimberSubSystem;
import frc.robot.subsystems.DeliverySubsystem;
import frc.robot.subsystems.IntakeSubsytem;
import frc.robot.subsystems.ShooterSubsystem;

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
	private final ChassisSubsystem chassis = new ChassisSubsystem();

	private final JController joystick1;

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		joystick1 = new JController(0);

		// Run any extra JController initialization code.
		Logger.getGlobal().info(() -> String.format("Found: '%s'!%n", joystick1.getName()));

		if (joystick1.getName().equals("Wireless Controller")) {
			joystick1.setXY(JHand.LEFT, 0, 1);
			joystick1.setXY(JHand.RIGHT, 2, 5);
		} else if (joystick1.getName().toLowerCase().contains("xbox")
				|| joystick1.getName().equals("Controller (Gamepad F310)")) {
			joystick1.setXY(JHand.LEFT, 0, 1);
			joystick1.setXY(JHand.RIGHT, 4, 5);
		}
		// ...

		// Configure the button bindings
		chassis.setDefaultCommand(new RunCommand(
				() -> chassis.TankDrive(joystick1.getY(JHand.LEFT), joystick1.getY(JHand.RIGHT)), chassis));
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

		joystick1.get("A").whileHeld(new SequentialCommandGroup(
				new ShooterSpeed(ShooterConstants.shooterFender, ShooterConstants.backSpinFender,
						shooter),
				new DeliveryRotate(DeliveryConstants.deliveryRot, delivery),
				new WaitUntilCommand(shooter::isInTolerance),
				new DeliveryRotate(DeliveryConstants.deliveryRot, delivery)))
				.whenReleased(new ShooterSpeed(0, 0, shooter));

		joystick1.get("B").whenHeld(new SequentialCommandGroup(
				new ShooterSpeed(ShooterConstants.shooter1MeterFender,
						ShooterConstants.shooter1backSpinFender, shooter),
				new DeliveryRotate(DeliveryConstants.deliveryRot, delivery),
				new WaitUntilCommand(shooter::isInTolerance),
				new DeliveryRotate(DeliveryConstants.deliveryRot, delivery)))
				.whenReleased(new ShooterSpeed(0, 0, shooter));

		joystick1.get("Y").whenPressed(() -> chassis.toggleReduccion());
		joystick1.get("X").whenPressed(() -> intake.toggleIntake());

		joystick1.get("dPadDown").whileHeld(new MoveClimber(ClimberConstants.reverseSpeed, climber));
		joystick1.get("dPadUp").whileHeld(new MoveClimber(ClimberConstants.forwardSpeed, climber));

		joystick1.get("LTrigger").whenPressed(new IntakeMotorSpeed(joystick1.getRawAxis(2), intake));
		joystick1.get("RTrigger").whenPressed(new IntakeMotorSpeed(-joystick1.getRawAxis(3), intake));
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
