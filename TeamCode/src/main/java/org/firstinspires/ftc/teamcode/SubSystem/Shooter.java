package org.firstinspires.ftc.teamcode.SubSystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    private DcMotorEx leftShooter;
    private DcMotorEx rightShooter;

    private double targetVelocity = 0;

    public Shooter(HardwareMap hardwareMap) {
        try {
            leftShooter = hardwareMap.get(DcMotorEx.class, "leftShooter");
            if (leftShooter != null) {
                leftShooter.setDirection(DcMotorEx.Direction.REVERSE);
                leftShooter.setVelocityPIDFCoefficients(50.0, 0.0, 0.0, 14.0);
            }
        } catch (Exception e) {
            leftShooter = null;
        }

        try {
            rightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");
            if (rightShooter != null) {
                rightShooter.setDirection(DcMotorEx.Direction.FORWARD);
                rightShooter.setVelocityPIDFCoefficients(50.0, 0.0, 0.0, 14.0);
            }
        } catch (Exception e) {
            rightShooter = null;
        }
    }

    // Spin up shooter forward
    public void shootForward() {
        targetVelocity = 2200; // feeder lockout, can be tuned up to 2800
        if (leftShooter != null) leftShooter.setVelocity(targetVelocity);
        if (rightShooter != null) rightShooter.setVelocity(targetVelocity);
    }

    // ✅ Spin shooter in reverse
    public void shootReverse() {
        targetVelocity = -2200; // same magnitude, negative for reverse
        if (leftShooter != null) leftShooter.setVelocity(targetVelocity);
        if (rightShooter != null) rightShooter.setVelocity(targetVelocity);
    }

    // Stop shooter
    public void stop() {
        targetVelocity = 0;
        if (leftShooter != null) {
            leftShooter.setVelocity(0);
            leftShooter.setPower(0);
        }
        if (rightShooter != null) {
            rightShooter.setVelocity(0);
            rightShooter.setPower(0);
        }
    }

    // Shooter always reports ready (no lock)
    public boolean isReady() {
        return true;
    }

    public double getTargetVelocity() { return targetVelocity; }
    public double getLeftVelocity() { return leftShooter != null ? leftShooter.getVelocity() : 0; }
    public double getRightVelocity() { return rightShooter != null ? rightShooter.getVelocity() : 0; }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("Target Vel", targetVelocity);
        telemetry.addData("Left Vel", getLeftVelocity());
        telemetry.addData("Right Vel", getRightVelocity());
    }
}