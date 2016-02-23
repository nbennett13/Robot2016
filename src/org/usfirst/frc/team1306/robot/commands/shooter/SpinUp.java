package org.usfirst.frc.team1306.robot.commands.shooter;

import org.usfirst.frc.team1306.robot.commands.CommandBase;

/**
 * A command that spins the shooter up to full speed. This command is meant to
 * be used in conjunction with the Target command so that the shooter is spun up
 * by the time the Fire command is run.
 */
public class SpinUp extends CommandBase {

	/**
	 * Constructs the SpinUp command. For obvious reasons, it requires the
	 * shooter.
	 */
	public SpinUp() {
		requires(shooter);
	}

	/**
	 * Called just before this Command runs the first time. Nothing needs to be
	 * done before the command starts.
	 */
	protected void initialize() {
	}

	/**
	 * Called repeatedly when this Command is scheduled to run. For whatever
	 * reason, the spinUp() method must be called repeatedly.
	 */
	protected void execute() {
		shooter.spinUp();
	}

	/**
	 * Returns true when this Command no longer needs to run execute().
	 * This command only ends when it's interrupted.
	 * 
	 * @return false
	 */
	protected boolean isFinished() {
		return false;
	}

	/**
	 * Called once after isFinished returns true. This command never does end.
	 */
	protected void end() {
	}

	/**
	 * Called when another command which requires one or more of the same
	 * subsystems is scheduled to run. Nothing happens because it simply
	 * transfers control, so no new velocity needs to be set. 
	 */
	protected void interrupted() {
	}
}
