//package org.usfirst.frc.team1306.robot.commands.intake;
//
//import org.usfirst.frc.team1306.robot.commands.CommandBase;
//
///**
// *
// */
//public class StopRoll extends CommandBase {
//
//    public StopRoll() {
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//    	requires(intake);
//    	requires(indexer);
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    	intake.stopRollers();
//    	indexer.stop();
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return true;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
