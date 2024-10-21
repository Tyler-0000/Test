package org.firstinspires.ftc.teamcode.Robot.RoughDraftCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "c")
public class c extends LinearOpMode {
    private DcMotor frontleft;
    private DcMotor frontright;
    private DcMotor backleft;
    private DcMotor backright;

    private double speedMultiplier = 1.0; // How fast the robot can go
    private double maxPowerChange = 0.1; // How much power can change each loop
    private double frontleftPower = 0; // Current power for front left motor
    private double frontrightPower = 0; // Current power for front right motor
    private double backleftPower = 0; // Current power for back left motor
    private double backrightPower = 0; // Current power for back right motor

    @Override
    public void runOpMode() {
        // Get the motors from the robot's hardware
        frontleft = hardwareMap.get(DcMotor.class, "frontleft");
        frontright = hardwareMap.get(DcMotor.class, "frontright");
        backleft = hardwareMap.get(DcMotor.class, "backleft");
        backright = hardwareMap.get(DcMotor.class, "backright");

        // Wait for the game to start
        waitForStart();

        while (opModeIsActive()) {
            // Get input from the gamepad
            double strafe = gamepad1.left_stick_x; // Move left or right
            double forward = -gamepad1.left_stick_y; // Move forward or backward (inverted)
            double turn = gamepad1.right_stick_x; // Turn left or right

            // Set speed based on triggers
            speedMultiplier = 0.5 + (gamepad1.left_trigger + gamepad1.right_trigger) * 0.5;

            // Calculate target power for each motor
            double targetFrontleft = Range.clip(forward + strafe + turn, -1, 1) * speedMultiplier;
            double targetFrontright = Range.clip(forward - strafe - turn, -1, 1) * speedMultiplier;
            double targetBackleft = Range.clip(forward - strafe - turn, -1, 1) * speedMultiplier;
            double targetBackright = Range.clip(forward + strafe + turn, -1, 1) * speedMultiplier;

            // Smoothly change motor power
            frontleftPower = adjust(frontleftPower, targetFrontleft);
            frontrightPower = adjust(frontrightPower, targetFrontright);
            backleftPower = adjust(backleftPower, targetBackleft);
            backrightPower = adjust(backrightPower, targetBackright);

            // Set the motor power
            frontleft.setPower(frontleftPower);
            frontright.setPower(frontrightPower);
            backleft.setPower(backleftPower);
            backright.setPower(backrightPower);

            // Show data on the screen for testing
            telemetry.addData("Speed Multiplier", speedMultiplier);
            telemetry.addData("Front Left Power", frontleftPower);
            telemetry.addData("Front Right Power", frontrightPower);
            telemetry.addData("Back Left Power", backleftPower);
            telemetry.addData("Back Right Power", backrightPower);
            telemetry.update();
        }
    }

    private double adjust(double currentPower, double targetPower) {
        // Change power slowly to avoid sudden movements
        if (Math.abs(targetPower - currentPower) > maxPowerChange) {
            currentPower += (targetPower > currentPower) ? maxPowerChange : -maxPowerChange;
        } else {
            currentPower = targetPower; // Go directly to target if close enough
        }
        return Range.clip(currentPower, -1, 1); // Make sure power is between -1 and 1
    }
}