package org.firstinspires.ftc.teamcode.Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutonBlue", group = "Autonomous")
public class AutonBlue extends AutonBase {

    @Override
    protected void runAuton() {
        // Use drive subsystem methods
        drive.driveForwardInches(40, 1.0);
        drive.gyroTurn(45, 0.4);
        drive.driveForwardInches(30, 0.5);

        // Shooter sequence
        shooter.shootForward();
        sleep(500);

        // Feed 6 artifacts, one at a time
        for (int i = 0; i < 6; i++) {
            if (shooter.isReady()) {
                feeder.advanceOneStep();
            }
            sleep(500);  // allow time for artifact to clear before next feed
        }

        // Stop subsystems
        feeder.stop();
        sleep(1000);
        shooter.stop();
    }
}