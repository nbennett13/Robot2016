package org.usfirst.frc.team1306.robot.commands.turret;

import org.usfirst.frc.team1306.robot.Constants;
import org.usfirst.frc.team1306.robot.commands.CommandBase;
import org.usfirst.frc.team1306.robot.vision.Vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A command that automatically aims the turret at the tower goal. It repeatedly
 * (5 times per second) gets data about where the target is, then uses the
 * CANTalon's internal PID loop to point there, always updating its intended
 * target.
 * 
 * @author Finn Voichick
 */
public class Target extends CommandBase {

	/**
	 * Creates a new Target command. The turret is required because this command
	 * can't run at the same time as ResetTurret.
	 */
	public Target() {
		requires(turret);
		requires(hood);
		SmartDashboard.putNumber("set hood height", SmartDashboard.getNumber("set hood height", 45.0));
	}

	/**
	 * Called just before this Command runs the first time. Currently does
	 * nothing.
	 */
	protected void initialize() {
		// turret.disable();
		if (hood.isAimingLow()) {
			hood.toggleTarget();
		}
		// turret.enable();
	}

	/**
	 * Called repeatedly when this Command is scheduled to run. If the target is
	 * seen, it sets the target there.
	 */
	protected void execute() {
		boolean canSeeTarget = Vision.getData().getDistance() > 0.0;
		if (canSeeTarget && !oi.getTurretOverrride()) {
			turret.enable();
			turret.setSetpoint(0.0);
		} else {
			turret.disable();
			turret.setVel(oi.getTurretVel());
		}
		SmartDashboard.putBoolean("aiming low", hood.isAimingLow());
		SmartDashboard.putBoolean("hood override", oi.getHoodOverride());
//		if (hood.isAimingLow()) {
//			hood.setHeight(Constants.HOOD_LOW_GOAL_POSITION);
//		} 
//		if (!oi.getHoodOverride()) {
//			hood.setHeight(45.0);
//		} else {
//			hood.setVel(oi.getHoodVel());
//		}
		if (canSeeTarget && !oi.getHoodOverride()) {
//			hood.setDistance(Vision.getData().getDistance());
			hood.setHeight(SmartDashboard.getNumber("set hood height"));
		} else {
			hood.setVel(oi.getHoodVel());
		}
	}

	/**
	 * Make this return true when this Command no longer needs to run execute().
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
		turret.setVel(0.0);
	}

	/**
	 * Called when another command which requires one or more of the same
	 * subsystems is scheduled to run. Nothing happens because it simply
	 * transfers control, so no new target needs to be set.
	 */
	protected void interrupted() {
		turret.disable();
		end();
	}
}
