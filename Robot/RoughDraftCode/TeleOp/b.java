package org.firstinspires.ftc.teamcode.Robot.RoughDraftCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "b")
public class b extends LinearOpMode {
    // Motor declarations
    private DcMotor frontLeft;  // Motor for the left side of robot
    private DcMotor frontRight; // Motor for the right side of robot
    private DcMotor backLeft; // Motor for the front of robot
    private DcMotor backRight;  // Motor for the back of robot

    // Proportional-Integral-Derivative variables for smoother control
    private double X_TargetPower = 0; // Target power for Right & Left
    private double Y_TargetPower = 0; // Target power for Front & Back
    private double X_CurrentPower = 0; // Current power for X-Axis (Right & Left)
    private double Y_CurrentPower = 0; // Current power for Y-Axis (Front & Back)
    private double SP_Multiplier = 1.0; // Factor to control speed
    private double MAX_Power = 0.05; // Maximum change in power

    @Override
    public void runOpMode() {
        // Initialize the motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // (Uncomment depending on setup aka used for different setups v1, v2, or v3 for WireFire)
        // leftMotor.setDirection(DcMotor.Direction.REVERSE);
        // rightMotor.setDirection(DcMotor.Direction.FORWARD);
        // frontMotor.setDirection(DcMotor.Direction.FORWARD);
        // backMotor.setDirection(DcMotor.Direction.REVERSE);

        //Wait for the game to start(Driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            //Inputs
            double strafeInput = gamepad1.left_stick_x; // left or right
            double forwardInput = gamepad1.left_stick_y; // up or down
            double turnInput = gamepad1.right_stick_x;// Turn left or right

            // Used for speed control
            double leftTrigger = gamepad1.left_trigger; // Range: 0.0 to 1.0
            double rightTrigger = gamepad1.right_trigger; // Range: 0.0 to 1.0

            // Speed multiplier based off inputs
            SP_Multiplier = 0.5 + (leftTrigger + rightTrigger) * 0.5; // Speed ranges from 0.5 to 1.0

            // Target power based off inputs
            X_TargetPower = Range.clip(strafeInput * SP_Multiplier, -1, 1); // Lateral movement
            Y_TargetPower = Range.clip(forwardInput * SP_Multiplier, -1, 1); // Forward/backward movement

            //Calculates the power for turning
            double turnPower = Range.clip(turnInput * SP_Multiplier, -1, 1);//Turning Movement

            X_CurrentPower = adjust(X_CurrentPower, X_TargetPower); // Adjusts the power for strafing aka left and right refer to the class at the bottom
            Y_CurrentPower = adjust(Y_CurrentPower, Y_TargetPower); // Adjusts the power for moving forward and backward refer to the class at the bottom

            //Calculates base motor power
            double frontLeftPower = Y_CurrentPower + X_CurrentPower + turnPower;  // Left motor power calculation
            double frontRightPower = Y_CurrentPower - X_CurrentPower - turnPower; // Right motor power calculation
            double backLeftPower = Y_CurrentPower + X_CurrentPower - turnPower; // Front motor power calculation
            double backRightPower = Y_CurrentPower - X_CurrentPower + turnPower;  // Back motor power calculation

            //Ensures the Motors stay within -1 to 1
            frontLeft.setPower(Range.clip(frontLeftPower, -1, 1));//
            frontRight.setPower(Range.clip(frontRightPower, -1, 1));
            backLeft.setPower(Range.clip(backLeftPower, -1, 1));
            backRight.setPower(Range.clip(backRightPower, -1, 1));

            //Telemetry
            telemetry.addData("Speed Multiplier", SP_Multiplier);
            telemetry.addData("Current Power X", X_CurrentPower);
            telemetry.addData("Current Power Y", Y_CurrentPower);
            telemetry.addData("Turn Power", turnPower);
            telemetry.addData("Left Motor Power", frontLeftPower);
            telemetry.addData("Right Motor Power", frontRightPower);
            telemetry.addData("Front Motor Power", backLeftPower);
            telemetry.addData("Back Motor Power", backRightPower);
            telemetry.update();
        }
    }

    private double adjust(double currentPower, double targetPower) {
        // Difference between current and target
        double powerDifference = targetPower - currentPower;

        // Determine the adjustment based on the maximum allowed change
        if (Math.abs(powerDifference) > MAX_Power) {
            // If the difference is larger than max allowed change, adjust by that amount
            currentPower += (powerDifference > 0) ? MAX_Power : -MAX_Power;
        }
        else {
            // If within the max change limit, set it directly to the target
            currentPower = targetPower;
        }

        //Makes it so the current power stays within -1 to 1
        return Range.clip(currentPower, -1, 1);
    }
}