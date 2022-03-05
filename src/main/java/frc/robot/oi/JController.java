package frc.robot.oi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.json.JSONInitException;
import frc.robot.json.JSONReader;
import net.jafama.FastMath;

/**
 * Wrapper for the {@link GenericHID} class, for easy {@link Button}
 * initialization and increased controller compatibility.
 */
public class JController extends GenericHID {
	/** HashMap storing the {@link JController}'s {@link Button Buttons}. */
	private final HashMap<String, Button> buttons = new HashMap<>();
	/** Tolerance for this {@link JController}'s axes. */
	private double axisTolerance = 0.15;
	/** This {@link JController}'s axes' values. */
	private int leftX = 0, leftY = 1, rightX = 4, rightY = 5;

	/**
	 * {@link JsonNode} holding the {@link JController}'s profile (friendly
	 * {@link Button} names).
	 */
	private JsonNode profile;

	/**
	 * Constructor for a {@link JController}.
	 * 
	 * <p>
	 * Initializes each and every {@link Button} from the {@link Joystick} found at
	 * the given port, and initializes {@link Button Buttons} for each of the
	 * {@link JController}'s axes and POVs with the default {@link #axisTolerance
	 * tolerance} of 0.15.
	 * 
	 * <p>
	 * Also initializes its {@link #profile} if possible.
	 * 
	 * @param port The port to be read
	 */
	public JController(int port) {
		this(port, 0.15);
	}

	/**
	 * Constructor for a {@link JController}.
	 * 
	 * <p>
	 * Initializes each and every {@link Button} from the {@link Joystick} found at
	 * the given port, and initializes {@link Button Buttons} for each of the
	 * {@link JController}'s axes and POVs with the given {@link #axisTolerance
	 * tolerance} value.
	 * 
	 * <p>
	 * Also initializes its {@link #profile} if possible.
	 * 
	 * @param port          The port used by this {@link JController}
	 * @param axisTolerance The amount of {@link #axisTolerance tolerance} to apply
	 *                      to this {@link JController}'s axes
	 */
	public JController(int port, double axisTolerance) {
		super(port);

		// Set tolerance of JController to specified amount.
		this.axisTolerance = axisTolerance;

		try {
			profile = JSONReader.getInstance().getNode("Profiles", getName());
		} catch (JSONInitException e) {
			var error = String.format(
					"[!] COULD NOT INITIALIZE CONTROLLER PROFILE FOR CONTROLLER '%1$s', USER-FRIENDLY NAMES WON'T WORK!%n\t%2$s",
					getName().toUpperCase(), e.getLocalizedMessage());

			Logger.getGlobal().severe(() -> error);
			DriverStation.reportError(error, false);
		}

		// Button initialization. Starting at 1.
		for (int i = 1; i <= this.getButtonCount(); i++)
			buttons.put("Button" + i, new JoystickButton(this, i));

		// dPadButton initialization. Starting at 0.
		for (int i = 0; i < this.getPOVCount(); i++) {
			final int pov = i; // Required for BooleanSupplier.

			for (int j = 0; j <= 315; j += 45) {
				final int angle = j; // Required for BooleanSupplier.

				// Create Button for each 45° angle on the dPad (e.g. 0 = down, 45 = left +
				// down, 90 = left, 135 = left + up, etc.).
				buttons.put("dPad" + i + "_" + j, new Button(() -> getPOV(pov) == angle));
			}

			// Create Button for ANY direction pressed on the dPad (single button).
			buttons.put("dPad" + i, new Button(() -> getPOV(pov) != -1));
		}

		// axisButton (Stick) initialization. Starting at 0.
		for (int i = 0; i < this.getAxisCount(); i++) {
			final int axis = i; // Required for BooleanSupplier.

			// Create Button for ANY direction of the axis, as well both positive and
			// negative directions.
			buttons.put("Stick" + i, new Button(() -> Math.abs(getRawAxis(axis)) > axisTolerance));
			buttons.put("Stick" + i + "_1", new Button(() -> getRawAxis(axis) > axisTolerance));
			buttons.put("Stick" + i + "_-1", new Button(() -> getRawAxis(axis) < -axisTolerance));
		}
	}

	@Override
	public String getName() {
		return DriverStation.getJoystickName(getPort()).trim();
	}

	@Override
	public double getRawAxis(int axis) {
		return getRawAxis(axis, false);
	}

	/**
	 * Sets this {@link JController}'s {@link #axisTolerance}.
	 * 
	 * @param axisTolerance The sensitivity at which to set the {@link JController}
	 *                      to
	 */
	public void setAxisTolerance(double axisTolerance) {
		this.axisTolerance = axisTolerance;
	}

	/**
	 * Get the value of the axis, with or without {@link #axisTolerance tolerance}.
	 *
	 * @param axis         The axis to read, starting at 0
	 * @param useTolerance Whether to apply {@link #axisTolerance tolerance} or not
	 * @return The value of the axis
	 */
	public double getRawAxis(int axis, boolean useTolerance) {
		return (!useTolerance || (Math.abs(DriverStation.getStickAxis(getPort(), axis)) > axisTolerance))
				? DriverStation.getStickAxis(getPort(), axis)
				: 0.0;
	}

	/**
	 * Method for getting a {@link Button} with a friendly name (declared in the
	 * JSON configuration) from this {@link JController}.
	 * 
	 * @param buttonNames The {@link Button}'s possible 'friendly' names
	 * @return A {@link Button} that matches the first 'friendly' name found in the
	 *         {@link JController}'s profile
	 * @throws NullPointerException When no provided 'friendly' {@link Button} names
	 *                              are found in the {@link #profile}
	 */
	public Button get(String... buttonNames) throws NullPointerException {
		for (String buttonName : buttonNames)
			if (profile.hasNonNull(buttonName))
				return buttons.get(profile.get(buttonName).asText());

		throw new NullPointerException("[!] JSON 'FRIENDLY' NAME NOT FOUND FOR JCONTROLLER: " + getName()
				+ "!\nPOSSIBLE 'FRIENDLY' NAMES PROVIDED WERE:\n\t" + Arrays.toString(buttonNames));
	}

	/**
	 * Method for getting a {@link Button} from the {@link JController}.
	 * 
	 * @param button The {@link Button}'s specific number (starting at 1)
	 * @return A {@link Button} from the {@link JController}'s {@link #buttons}
	 *         HashMap
	 */
	public Button get(int button) {
		return buttons.get("Button" + button);
	}

	/**
	 * Method for getting a {@link Button} from either an axis or POV from the
	 * {@link JController}.
	 * 
	 * @param type The {@link JAxisType type of axis} to get
	 * @param axis The value of the axis to be read
	 * @return A {@link Button} from the {@link JController}'s {@link #buttons}
	 *         HashMap, for the specified axis or POV, for any direction or angle
	 */
	public Button get(JAxisType type, int axis) {
		return buttons.get(type.getName() + axis);
	}

	/**
	 * Method for getting a {@link Button} from either an axis or POV from the
	 * {@link JController}, at a specific angle or value.
	 * 
	 * @param type  The {@link JAxisType type of axis} to get
	 * @param axis  The value of the axis to be read
	 * @param angle The angle or direction needed for this {@link Button} to be
	 *              triggered
	 * @return A {@link Button} from the {@link JController}'s {@link #buttons}
	 *         HashMap, for the specified axis or POV, for the specified direction
	 *         or angle
	 */
	public Button get(JAxisType type, int axis, int angle) {
		return buttons.get(type.getName() + axis + "_" + angle);
	}

	/**
	 * Sets both X and Y axes for a specific {@link JHand hand}.
	 * 
	 * @param hand  The hand to be used
	 * @param axisX The value to be read when calling {@link #getX(JHand)}, starting
	 *              at 0
	 * @param axisY The value to be read when calling {@link #getY(JHand)}, starting
	 *              at 0
	 */
	public void setXY(JHand hand, int axisX, int axisY) {
		setX(hand, axisX);
		setY(hand, axisY);
	}

	/**
	 * Set X axis for a specific {@link JHand}.
	 * 
	 * @param hand The hand to be used
	 * @param axis The value to be read when calling {@link #getX(JHand)}, starting
	 *             at 0
	 */
	public void setX(JHand hand, int axis) {
		switch (hand) {
			case LEFT:
				leftX = axis;
				break;
			case RIGHT:
				rightX = axis;
				break;
			default:
		}
	}

	/**
	 * Get X raw axis, for a specific hand, with {@link #axisTolerance tolerance} by
	 * default.
	 * 
	 * @param hand The hand to be used
	 * @return The x position of the given {@link JHand hand}'s axes, with
	 *         {@link #axisTolerance tolerance}
	 */
	public double getX(JHand hand) {
		return getX(hand, true);
	}

	/**
	 * Get X raw axis, for a specific hand, with or without {@link #axisTolerance
	 * tolerance}.
	 * 
	 * @param hand         The hand to be used
	 * @param useTolerance Whether to apply {@link #axisTolerance tolerance} or not
	 * @return The x position of the given {@link JHand hand}'s axes, with or
	 *         without {@link #axisTolerance tolerance}
	 */
	public double getX(JHand hand, boolean useTolerance) {
		switch (hand) {
			case LEFT:
				return getRawAxis(leftX, useTolerance);
			case RIGHT:
				return getRawAxis(rightX, useTolerance);
			default:
				return Double.NaN;
		}
	}

	/**
	 * Set Y axis for a specific {@link JHand}.
	 * 
	 * @param hand The hand to be used
	 * @param axis The value to be read when calling {@link #getY(JHand)}, starting
	 *             at 0
	 */
	public void setY(JHand hand, int axis) {
		switch (hand) {
			case LEFT:
				leftY = axis;
				break;
			case RIGHT:
				rightY = axis;
				break;
			default:
		}
	}

	/**
	 * Get Y raw axis, for a specific hand, with {@link #axisTolerance tolerance} by
	 * default.
	 * 
	 * @param hand The hand to be used
	 * @return The y position of the given {@link JHand hand}'s axes, with
	 *         {@link #axisTolerance tolerance}
	 */
	public double getY(JHand hand) {
		return getY(hand, true);
	}

	/**
	 * Get Y raw axis, for a specific hand, with or without {@link #axisTolerance
	 * tolerance}.
	 * 
	 * @param hand         The hand to be used
	 * @param useTolerance Whether to apply {@link #axisTolerance tolerance} or not
	 * @return The y position of the given {@link JHand hand}'s axes, with or
	 *         without {@link #axisTolerance tolerance}
	 */
	public double getY(JHand hand, boolean useTolerance) {
		switch (hand) {
			case LEFT:
				return getRawAxis(leftY, useTolerance);
			case RIGHT:
				return getRawAxis(rightY, useTolerance);
			default:
				return Double.NaN;
		}
	}

	/**
	 * Get the direction of the vector formed by the joystick and its origin in
	 * radians.
	 *
	 * @param hand The {@link JHand hand} to be used
	 * @return The direction of the vector in radians
	 */
	public double getAngleRadians(JHand hand) {
		return getAngleRadians(hand, false);
	}

	/**
	 * Get the direction of the vector formed by the joystick and its origin in
	 * radians.
	 *
	 * @param hand         The {@link JHand hand} to be used
	 * @param useTolerance Whether to apply {@link #axisTolerance tolerance} or not
	 * @return The direction of the vector in radians
	 */
	public double getAngleRadians(JHand hand, boolean useTolerance) {
		return FastMath.atan2(getX(hand, useTolerance), -getY(hand, useTolerance));
	}

	/**
	 * Get the direction of the vector formed by the joystick and its origin in
	 * degrees.
	 *
	 * @param hand The {@link JHand hand} to be used
	 * @return The direction of the vector in degrees
	 */
	public double getAngleDegrees(JHand hand) {
		return getAngleDegrees(hand, false);
	}

	/**
	 * Get the direction of the vector formed by the joystick and its origin in
	 * degrees.
	 *
	 * @param hand         The {@link JHand hand} to be used
	 * @param useTolerance Whether to apply {@link #axisTolerance tolerance} or not
	 * @return The direction of the vector in degrees
	 */
	public double getAngleDegrees(JHand hand, boolean useTolerance) {
		return FastMath.toDegrees(getAngleRadians(hand, useTolerance));
	}
}