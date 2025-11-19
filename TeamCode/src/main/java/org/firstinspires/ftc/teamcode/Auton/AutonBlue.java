package org.firstinspires.ftc.teamcode.Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutonBlue", group = "Autonomous")
public class AutonBlue extends AutonBase {

    @Override
    protected void runAuton() {
        driveForwardInches(40, 1.0);
        gyroTurn(45, 0.4);
        driveForwardInches(30, 0.5);

        shooter.shootForward();
        sleep(500);

        for (int i = 6; i > 0; i--) {
            feeder.feedForward();
            sleep(500);
        }

        feeder.stop();
        sleep(1000);
        shooter.stop();
    }
}
