package frc.robot;

public final class Constants {
    // FlexDIO
    // from 8 to 11 physical ports
    // 18 to 21 in pwm ports
    // highCurrentDIO
    // from 12 to 21 physical ports
    // from 0 to 9 in pwm ports
    

    // default
    public static final int TITAN_ID = 42;

    // drive constants
    public static final class Drive {
        // plugged to titan
        public static final int LEFT_MOTOR_1  = 0; // m0
        public static final int LEFT_MOTOR_2  = 1; // m1
        public static final int RIGHT_MOTOR_1  = 2; // m2
        public static final int RIGHT_MOTOR_2  = 3; // m3
    }

    // intake
    public static final class Intake {
        // check ports!
        public static final int INTAKE_SERVO = 0; // 12
    } 

    // encoder
    public static final class Encoder {
        // radius of drive wheel in mm
        public static final int wheelRadius = 51;

        // encoder pulses per rotation of motor shaft
        public static final int pulsePerRotation = 1464;

        // gear ratio between motor shaft and output shaft
        public static final double gearRatio = 1/1;

        // pulse per rotation combined with gear ratio
        public static final double encoderPulseRatio = pulsePerRotation * gearRatio;

        // distance per tick
        public static final double distancePerTick = (Math.PI * 2 * wheelRadius) / encoderPulseRatio;
    }

    // sharp ir range
    public static final class SharpIR {
        // numbers without explaining by studica
        public static final double firstNumber = -1.2045;
        public static final double secondNumber = 27.726;

        // method for normal code
        public static double voltageToDistance(double voltage) {
            return (Math.pow(voltage, -1.2045)) * 27.726;
        }
    }

    // control panel
    public static final class ControlPanel {
        // bad panel for control
        public static final int runningLedPort = 21;
        public static final int stoppedLedPort = 20;
    }
    
    // gamepad 
    public static final class Gamepad {
        // controller usb port
        public static final int USB_PORT = 0;

        // for ps4 controller
        // main buttons
        public static final int X_BUTTON        = 2; // a
        public static final int CIRCLE_BUTTON   = 3; // b
        public static final int TRIANGLE_BUTTON = 4; // y
        public static final int SQUARE_BUTTON   = 1; // x
        
        // bumpers
        public static final int LEFT_BUMPER  = 5; // l1
        public static final int RIGHT_BUMPER = 6; // r1

        // triggers
        public static final int LEFT_TRIGGER  = 7; // l2
        public static final int RIGHT_TRIGGER = 8; // r2

        // aditional button
        public static final int SHARE_BUTTON   = 9;  // back
        public static final int OPTIONS_BUTTON = 10; // start

        // good button, but didn't work
        public static final int LEFT_ANALOG_BUTTON  = 11; // l3
        public static final int RIGHT_ANALOG_BUTTON = 12; // r3

        // aditional aditional buttons
        public static final int PS4_BUTTON          = 13; // mode
        public static final int TOUCHPAD_BUTTON     = 14; // touchpad

        // joysticks
        public static final int LEFT_ANALOG_X  = 0;
        public static final int LEFT_ANALOG_Y  = 1;
        public static final int RIGHT_ANALOG_X = 2;
        public static final int RIGHT_ANALOG_Y = 5;

        // where is dpad buttons ? :(
    }
}
