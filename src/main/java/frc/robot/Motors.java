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


	// motors methods
	private void stopMotors() {
		leftMotor1.set(0.0);

		leftMotor1.stopMotor();
	}
    private void setLeftMotor1Power(double power) {
        leftMotor1.set(power);
    }

	// encoders
	private void resetEncoders() {
		leftEncoder1.reset();
	}

	// robot global
	@Override
	public void robotInit() {
	}

	@Override
	public void robotPeriodic() {
		// telemetry
		SmartDashboard.putNumber("left encoder 1 distance", leftEncoder1.getEncoderDistance());
		SmartDashboard.putNumber("left power 1", leftMotor1.get());
	}

	// disabled
	@Override
	public void disabledInit() {
        stopMotors();
	}

	@Override
	public void disabledPeriodic() {
        stopMotors();
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	// teleop
	@Override
	public void teleopInit() {
        resetEncoders();
        stopMotors();
	}

	@Override
	public void teleopPeriodic() {
		double speed = -joy.getRawAxis(Constants.Gamepad.LEFT_ANALOG_Y);
		double turn = joy.getRawAxis(Constants.Gamepad.RIGHT_ANALOG_X);

        setLeftMotor1Power(speed);
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
