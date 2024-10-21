package org.firstinspires.ftc.teamcode.Robot.RoughDraftCode.Structure;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class Motors {
    //Motor Declarations
    private DcMotor frontLeft; //Motor for front left side
    private DcMotor frontRight; //Motor for front right side
    private DcMotor backLeft; //Motor for the back left side
    private DcMotor backRight; //Motor for the back right side

    private double X_CurrentPower = 0;
    private double Y_CurrentPower = 0;
    private double MAX_Power = 0.05;

    public Motors(HardwareMap hardwareMap) {
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
    }
    //adjust the current power toward the target
    public double adjust(double currentPower, double targetPower) {
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
    // Method to set motor powers based on calculated values
    public void setPower(double targetPowerX, double targetPowerY, double turnPower) {
        // Adjust for X-Axis, Right & Left
        X_CurrentPower = adjust(X_CurrentPower, targetPowerX);
        // Adjust for Y-Axis, Front & Back
        Y_CurrentPower = adjust(Y_CurrentPower, targetPowerY);

        // Calculate motor power
        double frontLeftPower = Y_CurrentPower + X_CurrentPower + turnPower;  // Front left motor power calculation
        double frontRightPower = Y_CurrentPower - X_CurrentPower - turnPower; // Front right motor power calculation
        double backLeftPower = Y_CurrentPower + X_CurrentPower - turnPower;   // Back left motor power calculation
        double backRightPower = Y_CurrentPower - X_CurrentPower + turnPower;  // Back right motor power calculation

        // Set power between -1 and 1
        frontLeft.setPower(Range.clip(frontLeftPower, -1, 1));
        frontRight.setPower(Range.clip(frontRightPower, -1, 1));
        backLeft.setPower(Range.clip(backLeftPower, -1, 1));
        backRight.setPower(Range.clip(backRightPower, -1, 1));
    }

    // Stop all motors
    public void stop() {
        setPower(0, 0, 0); // Stop all motors
    }
}
