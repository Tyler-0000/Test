package org.firstinspires.ftc.teamcode.Main.Structure;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class Servos {
    // Servo declarations
    private Servo gripper;
    private CRServo continuousServo1; // Continuous rotation servo 1
    private CRServo continuousServo2; // Continuous rotation servo 2

    public Servos(HardwareMap hardwareMap) {
        // Initialize standard servo
        gripper = hardwareMap.get(Servo.class, "gripper");

        // Initialize continuous rotation servos
        continuousServo1 = hardwareMap.get(CRServo.class, "continuousServo1");
        continuousServo2 = hardwareMap.get(CRServo.class, "continuousServo2");

        // Set the continuous servos to use their own direction
        continuousServo1.setDirection(CRServo.Direction.FORWARD);
        continuousServo2.setDirection(CRServo.Direction.REVERSE);
    }

    // Set gripper position
    public void setGripperPosition(double position) {
        gripper.setPosition(Range.clip(position, 0, 1)); // Assuming position is between 0 and 1
    }

    // Set power for continuous rotation servo 1
    public void setContinuousServo1Power(double power) {
        continuousServo1.setPower(Range.clip(power, -1, 1)); // Power ranges from -1 to 1
    }

    // Set power for continuous rotation servo 2
    public void setContinuousServo2Power(double power) {
        continuousServo2.setPower(Range.clip(power, -1, 1)); // Power ranges from -1 to 1
    }

    // Stop continuous rotation servos
    public void stopContinuousServos() {
        setContinuousServo1Power(0);
        setContinuousServo2Power(0);
    }
}