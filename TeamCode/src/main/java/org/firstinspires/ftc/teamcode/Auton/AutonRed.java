package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutonRed", group = "Autonomous")
public class AutonRed extends AutonBase {

    @Override
    protected void runAuton() {
        driveForwardInches(50, 1.0);
        driveForwardInches(13, 0.5);
        gyroTurn(-45, 0.4);
        driveForwardInches(24, 0.5);

        shooter.shootForward();
        sleep(500);

        for (int i = 0; i < 6; i++) {
            feeder.feedForward();
            sleep(500);
        }

        feeder.stop();
        sleep(1000);
        shooter.stop();
    }
}