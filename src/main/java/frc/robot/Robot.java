package frc.robot;

import com.studica.frc.Lidar;
import com.studica.frc.Servo;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;
import com.studica.frc.Lidar.Port;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	// motors
	private TitanQuad leftMotor1 = new TitanQuad(Constants.TITAN_ID, Constants.Drive.LEFT_MOTOR_1);
	private TitanQuad leftMotor2 = new TitanQuad(Constants.TITAN_ID, Constants.Drive.LEFT_MOTOR_2);
	private TitanQuad rightMotor1 = new TitanQuad(Constants.TITAN_ID, Constants.Drive.RIGHT_MOTOR_1);
	private TitanQuad rightMotor2 = new TitanQuad(Constants.TITAN_ID, Constants.Drive.RIGHT_MOTOR_2);

	// encoders
	// Radius of drive wheel in mm
	public static final int wheelRadius = 51;
	// Encoder pulses per rotation of motor shaft
	public static final int pulsePerRotation = 1464;
	// Gear ratio between motor shaft and output shaft
	public static final double gearRatio = 1/1;
	// Pulse per rotation combined with gear ratio
	public static final double encoderPulseRatio = pulsePerRotation * gearRatio;
	// Distance per tick
	public static final double distancePerTick = (Math.PI * 2 * wheelRadius) / encoderPulseRatio;
	// create encoders
	private TitanQuadEncoder leftEncoder1 = new TitanQuadEncoder(leftMotor1, Constants.Drive.LEFT_MOTOR_1, distancePerTick);
	private TitanQuadEncoder leftEncoder2 = new TitanQuadEncoder(leftMotor2, Constants.Drive.LEFT_MOTOR_2, distancePerTick);
	private TitanQuadEncoder rightEncoder1 = new TitanQuadEncoder(rightMotor1, Constants.Drive.RIGHT_MOTOR_1, distancePerTick);
	private TitanQuadEncoder rightEncoder2 = new TitanQuadEncoder(rightMotor2, Constants.Drive.RIGHT_MOTOR_2, distancePerTick);

	// motors methods
	private void stopMotors() {
		leftMotor1.set(0.0);
		leftMotor2.set(0.0);
		rightMotor1.set(0.0);
		rightMotor2.set(0.0);

		leftMotor1.stopMotor();;
		leftMotor2.stopMotor();;
		rightMotor1.stopMotor();;
		rightMotor2.stopMotor();;
	}

	private void goForward() {
		leftMotor1.set(0.5);
		leftMotor2.set(-0.5);
		rightMotor1.set(0.45);
		rightMotor2.set(-0.45);
	}
	private void goForward(double left, double right) {
		leftMotor1.set(left);
		leftMotor2.set(-right);
		rightMotor1.set(left);
		rightMotor2.set(-right);
	}
	private void goBackward() {
		leftMotor1.set(-0.5);
		leftMotor2.set(0.5);
		rightMotor1.set(-0.45);
		rightMotor2.set(0.45);
	}
	private void goLeft() {
		leftMotor1.set(-0.4);
		leftMotor2.set(-0.4);
		rightMotor1.set(-0.4);
		rightMotor2.set(-0.4);
	}
	private void goRight() {
		leftMotor1.set(0.4);
		leftMotor2.set(0.4);
		rightMotor1.set(0.4);
		rightMotor2.set(0.4);
	}

	// encoders
	private void resetEncoders() {
		leftEncoder1.reset();
	}

	// claw
	private Servo leftClaw = new Servo(0);
	private Servo rightClaw = new Servo(1);

	private void openClaw() {
		leftClaw.setPosition(0.45);
		rightClaw.setPosition(0.4);
	}

	private void closeClaw() {
		leftClaw.setPosition(0.54);
		rightClaw.setPosition(0.3);
	}
	
	// lidar
	private Lidar lidar = new Lidar(Port.kUSB2);
	private Lidar.ScanData lidarData;

	// 270 left
	public float getLeftDistance() {
		return lidarData.distance[270];
	}
	// 90 right
	public float getRightDistance() {
		return lidarData.distance[90];
	}

	// joystick
	private Joystick joy = new Joystick(0);

	// button
	// private DigitalInput button = new DigitalInput(8);

	// leds
	private DigitalOutput running = new DigitalOutput(21);
	private DigitalOutput stopped = new DigitalOutput(20);
 
	// sensors
	private AnalogInput robotSharp = new AnalogInput(0);
	private AnalogInput clawSharp = new AnalogInput(3);
	// forward left sharp distance
	public double getForwardSharpDistance() {
		return (Math.pow(robotSharp.getAverageVoltage(), -1.2045)) * 27.726;
	}
	// claw sharp
	public double getClawSharpDistance() {
		return (Math.pow(clawSharp.getAverageVoltage(), -1.2045)) * 27.726;
	}

	// robot global
	@Override
	public void robotInit() {
		stopMotors();
		resetEncoders();
		openClaw();

		// lidar
		lidar.start();
		lidar.getData();
	}

	@Override
	public void robotPeriodic() {
		lidarData = lidar.getData();

		// telemetry
		SmartDashboard.putNumber("left encoder 1 distance", leftEncoder1.getEncoderDistance());
		SmartDashboard.putNumber("left power 1", leftMotor1.get());
		SmartDashboard.putNumber("left power 2", leftMotor2.get());
		SmartDashboard.putNumber("right power 1", rightMotor1.get());
		SmartDashboard.putNumber("right power 2", rightMotor2.get());

		SmartDashboard.putNumber("robot sharp", getForwardSharpDistance());
		SmartDashboard.putNumber("claw sharp", getClawSharpDistance());
		// SmartDashboard.putBoolean("button", button.get());

		// claw
		SmartDashboard.putNumber("left claw pos", leftClaw.getPosition());
		SmartDashboard.putNumber("right claw pos", rightClaw.getPosition());

		// lidar
		SmartDashboard.putNumber("lidar 90", lidarData.distance[90]);
		SmartDashboard.putNumber("lidar 270", lidarData.distance[270]);
	}

	// disabled
	@Override
	public void disabledInit() {
		stopMotors();

		// if(!button.get()) {
		// 	autonomousPeriodic();
		// }
	}

	@Override
	public void disabledPeriodic() {
		stopMotors();

		running.set(true);
		stopped.set(false);

		// if(!button.get()) {
		// 	autonomousPeriodic();
		// }
	}

	boolean test = true;

	@Override
	public void autonomousInit() {
		stopMotors();
		resetEncoders();

		openClaw();

		isGoToShkafRoom = false;
		isScanQrOfShkaffRoom = false;

		goToStartForRide = false;
		isTurnToRightForGoToStartForRide = false;

		isGoBasketQrRoom = false;
		isGoInFrontOfDoor = false;
		isGoGrassRoom = false;
		isGoBackToTheDoorInTheEnd = false;
		isGoBackToTheFinish = false;

		isFinished = false;
	}

	private boolean objectInClaw = false;

	@Override
	public void autonomousPeriodic() {
		if (isFinished) {
			stopMotors();
			
			leftMotor1.set(0.0);
			leftMotor2.set(0.0);
			rightMotor1.set(0.0);
			rightMotor1.set(0.0);

			leftMotor1.stopMotor();
			leftMotor2.stopMotor();
			rightMotor1.stopMotor();
			rightMotor1.stopMotor();

			running.set(true);
			stopped.set(false);
		} else {
			running.set(false);
			stopped.set(true);
		}

		if (!isGoToShkafRoom) {

			if (isScanQrOfShkaffRoom) {
				isGoToShkafRoom = true;
				stopMotors();

				Timer.delay(3);
			} else if (getForwardSharpDistance() > 175) {
				stopMotors();
				rightMotor2.set(0.0);
				isScanQrOfShkaffRoom = true;
			} else if (getForwardSharpDistance() < 175) {
				goBackward();
			}
			
		} else if (!goToStartForRide) {
			if (isTurnToRightForGoToStartForRide) {
				goToStartForRide = true;
				stopMotors();
			} else if (getForwardSharpDistance() < 40 && !isTurnToRightForGoToStartForRide) {
				isTurnToRightForGoToStartForRide = true;
				
				turnRight();
				stopMotors();
			} else if (getForwardSharpDistance() > 40 && !isTurnToRightForGoToStartForRide) {
				goForward(); 
			}
			
		} else if (!isGoBasketQrRoom) {
			
			if (nado2 && nado && isTurnRightForBasketQrRoom) {
				isGoBasketQrRoom = true;
				stopMotors();

			} else if (!isTurnRightForBasketQrRoom && nado && nado2) {
				isTurnRightForBasketQrRoom = true;

				turnRight();
				stopMotors();
			} else if (nado2 && !nado && !isTurnRightForBasketQrRoom) {
				goForward();

				Timer.delay(0.75);
				
				stopMotors();

				Timer.delay(3);

				goBackward();

				Timer.delay(0.75);

				stopMotors();
				nado = true;
			} else if (getForwardSharpDistance() < 60 && getRightDistance() > 600 && getLeftDistance() > 250 &&  !nado2 && !nado && !isTurnRightForBasketQrRoom) {
				goForward();

				nado2 = true;
			} else if (getForwardSharpDistance() < 60 && getRightDistance() > 600 && getLeftDistance() < 250 &&  !nado2 && !nado && !isTurnRightForBasketQrRoom) {
				goForward(0.4, 0.2);

				nado2 = true;
			} 

		} else if (!isGoInFrontOfDoor) {
			
			if (getLeftDistance() > 500 && isSomeGoForwardInFrontOfDoor) {
				isGoInFrontOfDoor = true;
				stopMotors();
			} else if (getLeftDistance() > 500 && !isSomeGoForwardInFrontOfDoor) {
				isSomeGoForwardInFrontOfDoor = true;

				goForward(0.4, 0.37);

				Timer.delay(0.3);
				
				turnLeft();

				goForward();

				Timer.delay(1.5);

				goLeft();

				Timer.delay(0.2);

				stopMotors();
			} else if (getLeftDistance() < 500 && !isSomeGoForwardInFrontOfDoor) {
				goForward();
			}

		} else if (!isGoGrassRoom) {
			if (!isGoGrassRoom && getClawSharpDistance() < 15 && objectInClaw) {
				goBackward();
				Timer.delay(0.5);
				goForward();
				Timer.delay(0.5);
				goBackward();
				Timer.delay(0.5);
				goForward();
				Timer.delay(0.5);
				goBackward();
				Timer.delay(0.5);
				goForward();
				Timer.delay(0.5);
				
				stopMotors();
				
				openClaw();
	
				Timer.delay(0.5);
	
				goBackward();
	
				Timer.delay(0.5);

				goBackward();

				Timer.delay(0.3);

				goRight();

				Timer.delay(0.3);
	
				stopMotors();
	
				isGoGrassRoom = true;
			} else if (getClawSharpDistance() > 10 && !objectInClaw) {
				goForward(0.2, 0.15);	 
			} else if (getClawSharpDistance() < 11 && !objectInClaw){
				stopMotors();
				stopMotors();
				closeClaw();
	
				objectInClaw = true;
			}
		} else if (!isGoBackToTheDoorInTheEnd) {
			
			if (isGoBackAndTurnToTheDoorIntTheEnd ) {
				isGoBackToTheDoorInTheEnd = true;
				stopMotors();
			} else if (getLeftDistance() > 500 && !isGoBackAndTurnToTheDoorIntTheEnd ) {
				isGoBackAndTurnToTheDoorIntTheEnd  = true;

				turnRight();

				stopMotors();
			} else if (getLeftDistance() < 300 && !isGoBackAndTurnToTheDoorIntTheEnd ) {
				goBackward();
			}

		} else if (!isGoBackToTheFinish) {
			if (getForwardSharpDistance() < 40 && getLeftDistance() > 400 && getRightDistance() < 400) {
				isGoBackToTheFinish = true;
				stopMotors();
				isFinished = true;
			} else if (getForwardSharpDistance() > 40 && getRightDistance() < 250) {
				goForward(0.2, 0.4);
			} else if (getForwardSharpDistance() > 40 && getRightDistance() > 400) {
				goForward(0.4, 0.2);
			} else {
				goForward();
			}
		} else {
			isFinished = true;
		}
	}

	private boolean isGoToShkafRoom = false;
	private boolean isScanQrOfShkaffRoom = false;

	private boolean goToStartForRide = false;
	private boolean isTurnToRightForGoToStartForRide = false;

	private boolean isGoBasketQrRoom = false;
	private boolean isTurnRightForBasketQrRoom = false;
	private boolean nado = false;
	private boolean nado2 = false;

	private boolean isGoBackToGoToTheDoor = false;
	private boolean isGoBacAndTurnToGoDoor = false;

	private boolean isGoInFrontOfDoor = false;
	private boolean isSomeGoForwardInFrontOfDoor = false;

	private boolean isGoGrassRoom = false;

	private boolean isGoBackToTheDoorInTheEnd = false;
	private boolean isGoBackAndTurnToTheDoorIntTheEnd = false;

	private boolean isGoBackToTheFinish = false;

	private boolean isFinished = false;

	private void turnLeft() {
		resetEncoders();
		Timer.delay(0.3);

		boolean abs = true;
		while(abs) {
			if (Math.abs(leftEncoder1.getEncoderDistance()) <= 275) {
				goLeft();
			} else {
				stopMotors();
				abs = false;
			}
		}

		stopMotors();
		resetEncoders();
		Timer.delay(0.5);
	}

	private void turnRight() {
		resetEncoders();
		Timer.delay(0.3);

		boolean abs = true;
		while(abs) {
			if (Math.abs(leftEncoder1.getEncoderDistance()) <= 455) {
				goRight();
			} else {
				stopMotors();
				abs = false;
			}
		}

		stopMotors();
		resetEncoders();
		Timer.delay(0.5);
	}

	// teleop
	@Override
	public void teleopInit() {
		stopMotors();
		resetEncoders();

		openClaw();
	}

	@Override
	public void teleopPeriodic() {
		double speed = -joy.getRawAxis(Constants.Gamepad.LEFT_ANALOG_Y);
		double turn = joy.getRawAxis(Constants.Gamepad.RIGHT_ANALOG_X);

		double left = speed + turn;
		double right = speed - turn;

		leftMotor1.set(left);
		leftMotor2.set(-left);
		rightMotor1.set(right);
		rightMotor2.set(-right);
	}

	// test
	@Override
	public void testInit() {
		stopMotors();
		resetEncoders();
	}

	@Override
	public void testPeriodic() {
	}
}
