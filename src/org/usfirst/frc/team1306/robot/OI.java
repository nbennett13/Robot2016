package org.usfirst.frc.team1306.robot;

import org.usfirst.frc.team1306.robot.commands.drivetrain.ShiftDown;
import org.usfirst.frc.team1306.robot.commands.drivetrain.ShiftUp;
import org.usfirst.frc.team1306.robot.commands.intake.IntakeArmDown;
import org.usfirst.frc.team1306.robot.commands.intake.IntakeArmPickup;
import org.usfirst.frc.team1306.robot.commands.intake.IntakeArmRest;
import org.usfirst.frc.team1306.robot.commands.intake.IntakeArmVertical;
import org.usfirst.frc.team1306.robot.commands.intake.Pass;
import org.usfirst.frc.team1306.robot.commands.intake.RollUntilPickup;
import org.usfirst.frc.team1306.robot.commands.intake.StopRoll;
import org.usfirst.frc.team1306.robot.commands.shooter.Fire;
import org.usfirst.frc.team1306.robot.commands.shooter.SpinUp;
import org.usfirst.frc.team1306.robot.commands.turret.BatterTarget;
import org.usfirst.frc.team1306.robot.commands.turret.HoodSetTargetAuto;
import org.usfirst.frc.team1306.robot.commands.turret.HoodSetTargetLow;
import org.usfirst.frc.team1306.robot.commands.turret.ResetTurret;
import org.usfirst.frc.team1306.robot.commands.turret.Target;
import org.usfirst.frc.team1306.robot.triggers.DPadDown;
import org.usfirst.frc.team1306.robot.triggers.DPadLeft;
import org.usfirst.frc.team1306.robot.triggers.DPadRight;
import org.usfirst.frc.team1306.robot.triggers.DPadUp;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author James Tautges, Finn Voichick
 */
public class OI {

	// Driver controls
	private final XboxController xbox;
	private final XboxController secondary;

	// Buttons and triggers
	private final Button buttonA;
	private final Button buttonB;
	private final Button buttonX;
	private final Button buttonY;
	private final Button buttonBack;
	private final Button buttonStart;
	private final Button bumperL;
	private final Button bumperR;
	private final Trigger dPadUp;
	private final Trigger dPadRight;
	private final Trigger dPadLeft;
	private final Trigger dPadDown;

	private final Button buttonA2;
	private final Button buttonB2;

	// Initialize everything
	public OI() {
		xbox = new XboxController(RobotMap.xboxPort);
		secondary = new XboxController(RobotMap.secondaryPort);

		buttonA = new JoystickButton(xbox, XboxController.A);
		buttonB = new JoystickButton(xbox, XboxController.B);
		buttonX = new JoystickButton(xbox, XboxController.X);
		buttonY = new JoystickButton(xbox, XboxController.Y);
		new JoystickButton(xbox, XboxController.START);
		buttonBack = new JoystickButton(xbox, XboxController.BACK);
		buttonStart = new JoystickButton(xbox, XboxController.START);
		bumperL = new JoystickButton(xbox, XboxController.LB);
		bumperR = new JoystickButton(xbox, XboxController.RB);

		buttonA2 = new JoystickButton(secondary, XboxController.A);
		buttonB2 = new JoystickButton(secondary, XboxController.B);

		dPadUp = new DPadUp(xbox);
		dPadRight = new DPadRight(xbox);
		dPadLeft = new DPadLeft(xbox);
		dPadDown = new DPadDown(xbox);

		// Bind input devices to commands
		buttonA.whenPressed(new Fire());
		buttonB.whenPressed(new StopRoll());
		buttonB.whenPressed(new ResetTurret());
		buttonX.whenPressed(new IntakeArmRest());
		buttonX.whenPressed(new SpinUp());
		buttonX.whenPressed(new Target());
		buttonY.whenPressed(new ResetTurret());
		buttonY.whenPressed(new IntakeArmPickup());
		buttonY.whenPressed(new RollUntilPickup());
		buttonBack.whenPressed(new ResetTurret());
		buttonBack.whenPressed(new Pass());
		buttonStart.whenPressed(new IntakeArmVertical());
		buttonStart.whenPressed(new BatterTarget());
		buttonStart.whenPressed(new SpinUp());
		bumperL.whenPressed(new ShiftDown());
		bumperR.whenPressed(new ShiftUp());
		dPadUp.whenActive(new IntakeArmVertical());
		dPadRight.whenActive(new IntakeArmPickup());
		dPadLeft.whenActive(new IntakeArmDown());
		dPadDown.whenActive(new IntakeArmRest());

		buttonA2.whenPressed(new HoodSetTargetAuto());
		buttonB2.whenPressed(new HoodSetTargetLow());
	}

	/**
	 * Return the value of the Y axis of the main right joystick after applying
	 * a deadband
	 * 
	 * @return Y axis value of main right joystick
	 */
	public double getRightVel() {
		return Math.pow(deadband(xbox.getY(Hand.kRight)), Constants.JOYSTICK_POWER);
	}

	/**
	 * Return the value of the Y axis of the main right joystick after applying
	 * a deadband
	 * 
	 * @return Y axis value of main right joystick
	 */
	public double getLeftVel() {
		return Math.pow(deadband(xbox.getY(Hand.kLeft)), Constants.JOYSTICK_POWER);
	}

	public double getStraightVel() {
		return Math.pow(xbox.getRT() - xbox.getLT(), Constants.JOYSTICK_POWER);
	}

	/**
	 * Return the X axis value of the main left joystick after applying a
	 * deadband
	 * 
	 * @return X axis value of the main left joystick
	 */
	public double getLeftX() {
		return deadband(xbox.getX(Hand.kLeft));
	}

	public int getPOV() {
		return xbox.getPOV();
	}

	public double getTurretVel() {
		return deadband(secondary.getX(Hand.kLeft));
	}

	public double getHoodVel() {
		return deadband(secondary.getY(Hand.kRight));
	}

	public boolean getTurretOverrride() {
		return secondary.getLT() > 0.5;
	}

	public boolean getHoodOverride() {
		return secondary.getRT() > 0.5;
	}

	/**
	 * Apply a deadband to the given value. This means that the value graph is
	 * split around zero a certain amount. This fixes the imprecise zeroing of
	 * xbox joysticks
	 * 
	 * @param value
	 *            Value to deadband
	 * @return Deadbanded value
	 */
	private static double deadband(double value) {
		if (value < -Constants.DEADBAND) {
			return (value + Constants.DEADBAND) / (1.0 - Constants.DEADBAND);
		}
		if (value > Constants.DEADBAND) {
			return (value - Constants.DEADBAND) / (1.0 - Constants.DEADBAND);
		}
		return 0;
	}
}
