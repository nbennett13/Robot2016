package org.usfirst.frc.team1306.robot.vision;

import edu.wpi.first.wpilibj.Timer;

/**
 * A class for getting data from the Jetson.
 * 
 * @author Finn Voichick
 */
public class Vision {

	/** The most recent data retrieved from the Jetson. */
	private VisionData recentData;

	/**
	 * Creates a new Vision object with no data.
	 */
	public Vision() {
		recentData = null;
	}

	/**
	 * Returns the most recent data if it is recent enough, otherwise gets new
	 * data from the Jetson and returns that.
	 * 
	 * @return recent data from the Jetson.
	 */
	public VisionData getData() {
		if (recentData != null && Timer.getFPGATimestamp() - recentData.getTimestamp() < PERIOD) {
			return recentData;
		}

		// TODO James, put your code here.
		double pitch = 0.0;
		double yaw = 0.0;
		double distance = 0.0;

		VisionData newData = new VisionData(pitch, yaw, distance);
		recentData = newData;
		return newData;
	}

	/**
	 * Finds whether a target is detected.
	 * 
	 * @return true if a target is detected, otherwise false
	 */
	public boolean canSeeTarget() {
		return getData().getDistance() > 0.0;
	}

	/** The period between updates, in seconds */
	private final static double PERIOD = 0.2;

}