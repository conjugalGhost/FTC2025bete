package org.firstinspires.ftc.teamcode.Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutonRed", group = "Autonomous")
public class AutonRed extends AutonBase {

    @Override
    protected void runAuton() {
        // Use drive subsystem methods
        drive.driveForwardInches(50, 1.0);
        drive.driveForwardInches(13, 0.5);
        drive.gyroTurn(-45, 0.4);
        drive.driveForwardInches(24, 0.5);

        // Shooter sequence
        shooter.shootForward();
        sleep(500);

        // Feed 6 artifacts, one at a time
        for (int i = 0; i < 6; i++) {
            if (shooter.isReady()) {
                feeder.advanceOneStep();
            }
            sleep(500);  // allow time for artifact to clear
        }

        // Stop subsystems
        feeder.stop();
        sleep(1000);
        shooter.stop();
    }
}