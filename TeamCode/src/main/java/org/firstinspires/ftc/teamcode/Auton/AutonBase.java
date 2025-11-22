package org.firstinspires.ftc.teamcode.Auton;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.SubSystem.Drive;
import org.firstinspires.ftc.teamcode.SubSystem.IMU;
import org.firstinspires.ftc.teamcode.SubSystem.Shooter;
import org.firstinspires.ftc.teamcode.SubSystem.Feeder;

public abstract class AutonBase extends LinearOpMode {
    protected Drive drive;
    protected IMU imu;
    protected Shooter shooter;
    protected Feeder feeder;
    protected boolean detailMode = false;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize subsystems
        drive   = new Drive(hardwareMap);
        imu     = new IMU(hardwareMap);
        shooter = new Shooter(hardwareMap);
        feeder  = new Feeder(hardwareMap);

        telemetry.addLine("Autonomous Initialized");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            runAuton();
        }
    }

    /** Override this in your auton routines */
    protected abstract void runAuton();

    /** Helper: drive forward a measured distance in inches */
    protected void driveForwardInches(double inches, double power) {
        drive.driveForwardInches(inches, power);
        drive.stop();
    }

    /** Helper: turn to a specific heading (±2° tolerance) */
    protected void turnToHeading(double targetHeading) {
        while (opModeIsActive()) {
            double currentHeading = imu.getHeading();
            double error = targetHeading - currentHeading;

            if (Math.abs(error) < 2) break;

            double turnPower = error > 0 ? 0.3 : -0.3;
            drive.turn(turnPower);

            telemetry.addData("Target", targetHeading);
            telemetry.addData("Current", currentHeading);
            telemetry.addData("Error", error);
            telemetry.update();
        }
        drive.stop();
    }

    /** Optional: reset IMU yaw to zero */
    protected void resetHeading() {
        imu.resetYaw();
    }

    /** Optional helper to log shooter velocity */
    protected void logShooterVelocity() {
        telemetry.addData("Shooter Left Vel", shooter.getLeftVelocity());
        telemetry.addData("Shooter Right Vel", shooter.getRightVelocity());
        telemetry.update();
    }
}