package org.firstinspires.ftc.teamcode.Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Auton Blue", group="Auton")
public class AutonBlue extends AutonBase {

    @Override
    protected void runAuton() {
        // Enable detailed telemetry for this run
        detailMode = true;

        // Reset heading before starting
        resetHeading();

        // Example movement before shooting
        driveForwardInches(72, 0.5);   // drive forward in inches
        turnToHeading(45);             // turn to (input) degrees
        driveForwardInches(52,0.5);    // drive forward in inches

        // Spin up shooter
        shooter.shootForward();
        sleep(500);

        // Log velocity before feeding
        logShooterVelocity();

        // Feed 6 artifacts
        for (int i = 0; i < 9; i++) {
            feeder.advanceOneStep();
            sleep(500);
            logShooterVelocity();
        }

        // Stop shooter at the end
        shooter.stop();

        // Optional: back up after shooting
        driveForwardInches(-12, 0.5);
    }
}