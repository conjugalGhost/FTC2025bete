package org.firstinspires.ftc.teamcode.Auton;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SubSystem.Drive;
import org.firstinspires.ftc.teamcode.SubSystem.Shooter;
import org.firstinspires.ftc.teamcode.SubSystem.Feeder;

public abstract class AutonBase extends LinearOpMode {

    protected Drive drive;
    protected Shooter shooter;
    protected Feeder feeder;
    protected BNO055IMU imu;

    @Override
    public void runOpMode() {
        // Initialize subsystems
        drive = new Drive(hardwareMap);
        shooter = new Shooter(hardwareMap);
        feeder = new Feeder(hardwareMap);

        // Initialize IMU
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(params);

        // Wait for start and run autonomous routine
        waitForStart();
        if (opModeIsActive()) {
            runAuton();
        }

        // Stop all subsystems safely
        drive.setDrivePower(0.0);
        shooter.stop();
        feeder.stop();
    }

    // This is implemented in AutonRed and AutonBlue
    protected abstract void runAuton();
}