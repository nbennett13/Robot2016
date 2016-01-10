package org.usfirst.frc.team1306.robot.subsystems;

import org.usfirst.frc.team1306.robot.RobotMap;
import org.usfirst.frc.team1306.robot.commands.DriveTank;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drivetrain extends Subsystem {

	
	private final CANTalon[] motors;
	private final CANTalon leftMotor1;
	private final CANTalon leftMotor2;
	private final CANTalon rightMotor1;
	private final CANTalon rightMotor2;

	private final RobotDrive drivetrain;

	public Drivetrain() {

		leftMotor1 = new CANTalon(RobotMap.leftTalon1Port);
		leftMotor2 = new CANTalon(RobotMap.leftTalon2Port);
		rightMotor1 = new CANTalon(RobotMap.rightTalon1Port);
		rightMotor2 = new CANTalon(RobotMap.rightTalon1Port);
		
		motors = new CANTalon[]{leftMotor1, leftMotor2, rightMotor1, rightMotor2};
		for (CANTalon motor : motors) {
			setupMotor(motor);
		}

		drivetrain = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
		
		//SmartDashboard.putNumber("maxSpeed", MAX_SPEED);
				
	}

	public void driveTank(double leftVel, double rightVel) {
		double maxSpeed = SmartDashboard.getNumber("maxSpeed");
		leftMotor1.set(leftVel);
		//drivetrain.tankDrive(leftVel * maxSpeed, rightVel * maxSpeed);
		SmartDashboard.putNumber("leftMotor1.get()", leftMotor1.get());
		SmartDashboard.putNumber("leftMotor1.getError()", leftMotor1.getError());
		SmartDashboard.putNumber("Encoder velocity", leftMotor1.getEncVelocity());
	}

	public void driveHybrid(double velocity, double rotation) {
		double maxSpeed = SmartDashboard.getNumber("maxSpeed");
		drivetrain.arcadeDrive(velocity * maxSpeed, rotation * maxSpeed);
	}

	public void stop() {
		drivetrain.stopMotor();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveTank());
	}

	private void setupMotor(CANTalon motor) {
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.changeControlMode(TalonControlMode.Speed);
		motor.set(0.0);
		motor.enable();
	}
	
	/** All of these are placeholder values. */
	/*private static double MAX_SPEED = 850.0;
	private static double P = 1.0;
	private static double I = 0.0;
	private static double D = 0.0;
	private static double F = 0.0;
	private static int IZONE = 0;
	private static double RAMP_RATE = 2.0;*/

}
