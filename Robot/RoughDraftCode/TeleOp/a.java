package org.firstinspires.ftc.teamcode.Robot.RoughDraftCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "a")
public class a extends LinearOpMode {
    private Motors motors; // Instance of Motors class
    private Servos servos; // Instance of Servos class
    private double SP_Multiplier = 1.0; // Factor to control speed

    @Override
    public void runOpMode() {
        // Initialize motors and servos using the HardwareMap
        motors = new Motors(hardwareMap); // Initialize motors
        servos = new Servos(hardwareMap); // Initialize servos

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            // Inputs
            double strafeInput = gamepad1.left_stick_x; // left or right
            double forwardInput = gamepad1.left_stick_y; // up or down
            double turnInput = gamepad1.right_stick_x;// Turn left or right

            // Used for speed control
            double leftTrigger = gamepad1.left_trigger; // Range: 0.0 to 1.0
            double rightTrigger = gamepad1.right_trigger; // Range: 0.0 to 1.0

            // Speed multiplier based off inputs
            SP_Multiplier = 0.5 + (leftTrigger + rightTrigger) * 0.5; // Speed ranges from 0.5 to 1.0

            // Target power based off inputs
            double targetPowerX = Range.clip(strafeInput * SP_Multiplier, -1, 1); // Lateral movement
            double targetPowerY = Range.clip(forwardInput * SP_Multiplier, -1, 1); // Forward/backward movement

            // Set the motor powers
            motors.setPower(targetPowerX, targetPowerY, turnInput);

            // Control the gripper and continuous servos based ...
            if (gamepad1.a) {
                servos.setGripperPosition(1.0); // Open gripper
            } else {
                servos.setGripperPosition(0.0); // Close gripper
            }

            // Control continuous servos with ...
            double continuousPower = gamepad1.right_stick_y; // Use ... for continuous servo power
            servos.setContinuousServo1Power(continuousPower);
            servos.setContinuousServo2Power(-continuousPower); // Reverse power for the second servo

            //Telemetry
            telemetry.addData("Speed Multiplier", SP_Multiplier);
            telemetry.update();
        }
    }
}