package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Feeder;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="TeleOp")
public class TeleOp extends LinearOpMode {

    private Drive drive;
    private Shooter shooter;
    private Feeder feeder;

    @Override
    public void runOpMode() {
        drive = new Drive(hardwareMap);
        shooter = new Shooter(hardwareMap);
        feeder = new Feeder(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            // Drive control
            drive.driveWithGamepad(gamepad1);

            // Shooter control
            if (gamepad2.right_trigger > 0.05) {
                shooter.shootForward();
            } else {
                shooter.stop();
            }

            // Feeder control
            if (gamepad2.a) {
                feeder.feedForward();
            } else if (gamepad2.x) {
                feeder.feedReverse();
            } else {
                feeder.stop();
            }

            // Telemetry dashboards
            drive.updateTelemetry(telemetry);
            shooter.updateTelemetry(telemetry);
            feeder.updateTelemetry(telemetry);
            telemetry.update();
        }
    }
}