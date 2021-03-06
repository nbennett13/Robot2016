package org.usfirst.frc.team1306.robot.commands.autonomous;

import org.usfirst.frc.team1306.robot.Constants;
import org.usfirst.frc.team1306.robot.commands.drivetrain.ShiftDown;
import org.usfirst.frc.team1306.robot.commands.intake.IntakeArmMove;
import org.usfirst.frc.team1306.robot.commands.intake.IntakeArmRest;
import org.usfirst.frc.team1306.robot.commands.intake.IntakePosition;
import org.usfirst.frc.team1306.robot.commands.shooter.Fire;
import org.usfirst.frc.team1306.robot.commands.shooter.SpinUp;
import org.usfirst.frc.team1306.robot.commands.turret.RaiseHood;
import org.usfirst.frc.team1306.robot.commands.turret.ScanForTarget;
import org.usfirst.frc.team1306.robot.commands.turret.Target;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A general command that can be configured for all autonomous programs.
 * 
 * @author Finn Voichick
 */
public class AutonomousCommand extends CommandGroup {

	/**
	 * Constructs an AutonomousCommand with the given settings.
	 * 
	 * @param defenseType
	 *            the type of defense to traverse.
	 * @param fire
	 *            whether or not to fire after moving.
	 */
	public AutonomousCommand(DefenseType defenseType, boolean fire, boolean inRange) {
		
		addSequential(new ShiftDown());

		if (defenseType.equals(DefenseType.LOWBAR)) {
			addSequential(new IntakeArmRest());
		} else {
			addParallel(new IntakeArmMove(IntakePosition.VERTICAL));
		}

		addSequential(defenseType.getDriveCommand());

		if (fire) {
			addParallel(new SpinUp());
			addParallel(new RaiseHood());
			addSequential(new IntakeArmRest());
			addSequential(new ScanForTarget());
			addParallel(new Target());
			if (inRange) {
				addSequential(new DriveInRange());
			}
			addSequential(new Wait(Constants.TARGET_TIME));
			addSequential(new Fire());
		}

	}
}
