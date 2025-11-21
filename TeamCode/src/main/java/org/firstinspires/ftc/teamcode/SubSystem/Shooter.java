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
                leftShooter.setDirection(DcMotorEx.Direction.FORWARD);
                leftShooter.setVelocityPIDFCoefficients(50.0, 0.0, 0.0, 14.0);
            }
        } catch (Exception e) {
            leftShooter = null;
        }

        try {
            rightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");
            if (rightShooter != null) {
                // Reverse one motor so both spin inward together
                rightShooter.setDirection(DcMotorEx.Direction.REVERSE);
                rightShooter.setVelocityPIDFCoefficients(50.0, 0.0, 0.0, 14.0);
            }
        } catch (Exception e) {
            rightShooter = null;
        }
    }


    // Spin up shooter to target velocity
    public void shootForward() {
        targetVelocity = 2800; // ticks/sec, matches your PIDF tuning
        if (leftShooter != null) leftShooter.setVelocity(targetVelocity);
        if (rightShooter != null) rightShooter.setVelocity(targetVelocity);
    }

    // Stop shooter
    public void stop() {
        targetVelocity = 0;
        if (leftShooter != null) leftShooter.setPower(0);
        if (rightShooter != null) rightShooter.setPower(0);
    }

    // Check if shooter is at speed
// Check if shooter is at speed (±30% tolerance)
    public boolean isReady() {
        if (targetVelocity == 0) return false;
        double leftVel = getLeftVelocity();
        double rightVel = getRightVelocity();
        return Math.abs(leftVel - targetVelocity) <= targetVelocity * 0.3 &&
                Math.abs(rightVel - targetVelocity) <= targetVelocity * 0.3;
    }

    public double getTargetVelocity() { return targetVelocity; }
    public double getLeftVelocity() { return leftShooter != null ? leftShooter.getVelocity() : 0; }
    public double getRightVelocity() { return rightShooter != null ? rightShooter.getVelocity() : 0; }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("Target Vel", targetVelocity);
        telemetry.addData("Left Vel", getLeftVelocity());
        telemetry.addData("Right Vel", getRightVelocity());
        telemetry.addData("Ready", isReady());
    }
}