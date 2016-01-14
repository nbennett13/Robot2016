package org.usfirst.frc.team1306.robot.commands.motionprofile;

import java.util.LinkedList;
import java.util.Queue;

import org.usfirst.frc.team1306.robot.Constants;
import org.usfirst.frc.team1306.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StraightProfile extends CommandBase {

	private final double target = 1000;

	public StraightProfile() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		double stopRampUp;
		double startRampDown;
		double maxSpeed;
		// if the target distance is too short for us to get up to full speed
		if (target < Math.pow(Constants.MAX_SPEED, 2) / (2 * Constants.MAX_ACCELERATION)) {
			maxSpeed = Math.sqrt(target * Constants.MAX_ACCELERATION);
		} else { // if we can reach our max speed
			maxSpeed = Constants.MAX_SPEED;
		}
		
		Queue<Double> velocities = new LinkedList<Double>();
		Queue<Double> positions = new LinkedList<Double>();
		
		int time = 0;
		double velocity = 0.0;
		double position = 0.0;
		int steps = 0;
		while (velocity > 0 || position == 0.0) {
			position += velocity * Constants.PROFILE_STEP;
			
			if (target - position < 0.5 * Math.pow(velocity, 2) / Constants.MAX_ACCELERATION) {
				velocity = Math.max(Math.min(time*Constants.MAX_ACCELERATION, Constants.MAX_SPEED), 0.0);
			} else {
				velocity -= Constants.MAX_ACCELERATION * Constants.PROFILE_STEP;
			}
			
			velocities.add(velocity);
			positions.add(position);
			steps++;
			time = steps * Constants.PROFILE_STEP;
		}
		
		drivetrain.setTrajectory(0, velocities, positions, steps);
		drivetrain.setTrajectory(1, velocities, positions, steps);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drivetrain.runProfile();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
