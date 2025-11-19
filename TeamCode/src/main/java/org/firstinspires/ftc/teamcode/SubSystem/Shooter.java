package org.firstinspires.ftc.teamcode.SubSystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    private DcMotorEx leftShooter, rightShooter;
    private final double targetVelocity = 2800.0;

    public Shooter(HardwareMap hardwareMap) {
        leftShooter = hardwareMap.get(DcMotorEx.class, "shooterLeft");
        rightShooter = hardwareMap.get(DcMotorEx.class, "shooterRight");

        PIDFCoefficients shooterPIDF = new PIDFCoefficients(50.0, 0.0, 0.0, 14.0);
        leftShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, shooterPIDF);
        rightShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, shooterPIDF);
    }

    public void shootForward() {
        leftShooter.setVelocity(-targetVelocity);
        rightShooter.setVelocity(targetVelocity);
    }

    public void stop() {
        leftShooter.setVelocity(0);
        rightShooter.setVelocity(0);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addLine("Shooter Velocity Dashboard")
                .addData("Target", targetVelocity)
                .addData("Left Actual", leftShooter.getVelocity())
                .addData("Right Actual", rightShooter.getVelocity());
    }
}
