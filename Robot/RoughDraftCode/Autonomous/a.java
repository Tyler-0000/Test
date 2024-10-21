package org.firstinspires.ftc.teamcode.Robot.RoughDraftCode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Park in Observation Zone from Wall")
public class a extends LinearOpMode {
    private DcMotor frontleft;
    private DcMotor frontright;
    private DcMotor backleft;
    private DcMotor backright;

    @Override
    public void runOpMode() {
        // Initialize motors
        frontleft = hardwareMap.get(DcMotor.class, "frontleft");
        frontright = hardwareMap.get(DcMotor.class, "frontright");
        backleft = hardwareMap.get(DcMotor.class, "backleft");
        backright = hardwareMap.get(DcMotor.class, "backright");

        // Wait for the start signal
        waitForStart();

        // Turn right (90 degrees)
        turnRight(0.5, 0.5); // Adjust power and duration as needed

        // Move forward to the submersible
        moveForward(0.5, 24); // Move forward at 50% power for 24 inches

        // Park in the observation zone
        moveForward(0.5, 12); // Move forward another 12 inches to park

        // Stop the motors
        stopMotors();
    }

    // Method to move forward
    private void moveForward(double power, double distance) {
        int targetPosition = (int)(distance * 1120 / (4 * Math.PI)); // Convert inches to encoder ticks

        frontleft.setTargetPosition(frontleft.getCurrentPosition() + targetPosition);
        frontright.setTargetPosition(frontright.getCurrentPosition() + targetPosition);
        backleft.setTargetPosition(backleft.getCurrentPosition() + targetPosition);
        backright.setTargetPosition(backright.getCurrentPosition() + targetPosition);

        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontleft.setPower(power);
        frontright.setPower(power);
        backleft.setPower(power);
        backright.setPower(power);

        // Wait until the motors reach the target position
        while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()) {
            // Add any telemetry here if needed
        }
    }

    // Method to turn right
    private void turnRight(double power, double duration) {
        // Set power to turn right
        frontleft.setPower(power);
        frontright.setPower(-power);
        backleft.setPower(power);
        backright.setPower(-power);

        // Wait for the duration of the turn
        sleep((long)(duration * 1000)); // Convert to milliseconds

        // Stop the motors after turning
        stopMotors();
    }

    // Method to stop all motors
    private void stopMotors() {
        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
    }
}