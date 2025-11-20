package org.firstinspires.ftc.teamcode.SubSystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Feeder {
    private DcMotorEx feederLeft;
    private DcMotorEx feederRight;

    public Feeder(HardwareMap hardwareMap) {
        try {
            feederLeft = hardwareMap.get(DcMotorEx.class, "feederLeft");
        } catch (Exception e) {
            feederLeft = null;
        }

        try {
            feederRight = hardwareMap.get(DcMotorEx.class, "feederRight");
        } catch (Exception e) {
            feederRight = null;
        }
    }

    // Move both feeders to startup positions
    public void moveToStartupPositions() {
        if (feederLeft != null) {
            feederLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            feederLeft.setTargetPosition(-4200);
            feederLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            feederLeft.setPower(0.5); // adjust speed as needed
        }

        if (feederRight != null) {
            feederRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            feederRight.setTargetPosition(4200);
            feederRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            feederRight.setPower(0.5); // adjust speed as needed
        }
    }

    // Outward from center
    public void feedForward() {
        if (feederLeft != null) feederLeft.setPower(1.0);
        if (feederRight != null) feederRight.setPower(-1.0);
    }

    // Inward toward center
    public void feedReverse() {
        if (feederLeft != null) feederLeft.setPower(-1.0);
        if (feederRight != null) feederRight.setPower(1.0);
    }

    public void stop() {
        if (feederLeft != null) feederLeft.setPower(0.0);
        if (feederRight != null) feederRight.setPower(0.0);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addLine("=== FEEDER ===");
        if (feederLeft != null) {
            telemetry.addData("Left Position", feederLeft.getCurrentPosition());
            telemetry.addData("Left Power", feederLeft.getPower());
        } else {
            telemetry.addData("Left Motor", "Not Found");
        }
        if (feederRight != null) {
            telemetry.addData("Right Position", feederRight.getCurrentPosition());
            telemetry.addData("Right Power", feederRight.getPower());
        } else {
            telemetry.addData("Right Motor", "Not Found");
        }
    }
}