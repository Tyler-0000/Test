package org.firstinspires.ftc.teamcode.Robot.RoughDraftCode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.RoughDraftCode.Structure.Motors;
import org.firstinspires.ftc.teamcode.Robot.RoughDraftCode.Structure.Servos;

@Autonomous(name = "Basic Autonomous")
public class b extends LinearOpMode {
    private Motors motors;// Instance of Motors class
    private Servos servos;// Instance of Servos class

    @Override
    public void runOpMode() {
        // Initialize hardware
        motors = new Motors(hardwareMap); // Initialize motors
        servos = new Servos(hardwareMap); // Initialize servos

        // Wait for the start signal
        waitForStart();

        // Start autonomous actions
        driveForward(1.0, 1000);// Drive forward at full speed for 1000 ms
        turn(0.5, 500);// Turn for 500 ms
        sleep(1000);// Wait for 1 second
        motors.stop();// Stop all motors
    }

    //Drive Forward
    private void driveForward(double power, long duration) {
        motors.setPower(0, power, 0); // Move forward
        sleep(duration);// Wait for specified duration
        motors.stop();// Stop motors
    }

    // Turn
    private void turn(double power, long duration) {
        motors.setPower(0, 0, power); // Turn
        sleep(duration);// Wait for specified duration
        motors.stop();// Stop motors
    }
}
