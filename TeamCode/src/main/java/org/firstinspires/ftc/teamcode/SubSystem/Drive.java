package org.firstinspires.ftc.teamcode.SubSystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Drive {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private double velocityScale = 2000.0;
    private double targetFL, targetFR, targetBL, targetBR;

    private static final double TICKS_PER_INCH = 45.0; // placeholder value

    public Drive(HardwareMap hardwareMap) {
        frontLeft  = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft   = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight  = hardwareMap.get(DcMotorEx.class, "backRight");

        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

        PIDFCoefficients drivePIDF = new PIDFCoefficients(10.0, 0.0, 0.0, 2.0);
        frontLeft.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, drivePIDF);
        frontRight.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, drivePIDF);
        backLeft.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, drivePIDF);
        backRight.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, drivePIDF);
    }

    /** TeleOp drive with gamepad sticks (robot-centric) */
    public void driveWithGamepad(com.qualcomm.robotcore.hardware.Gamepad gamepad) {
        double y = -gamepad.left_stick_y; // forward/back
        double x = gamepad.left_stick_x;  // strafe
        double rx = gamepad.right_stick_x; // rotation

        double fl = y + x + rx;
        double bl = y - x + rx;
        double fr = y - x - rx;
        double br = y + x - rx;

        double max = Math.max(Math.abs(fl), Math.max(Math.abs(bl),
                Math.max(Math.abs(fr), Math.abs(br))));
        if (max > 1.0) {
            fl /= max; bl /= max; fr /= max; br /= max;
        }

        targetFL = fl * velocityScale;
        targetFR = fr * velocityScale;
        targetBL = bl * velocityScale;
        targetBR = br * velocityScale;

        frontLeft.setVelocity(targetFL);
        frontRight.setVelocity(targetFR);
        backLeft.setVelocity(targetBL);
        backRight.setVelocity(targetBR);
    }

    /** Simple power set for all motors */
    public void setDrivePower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    /** Encoder-based forward drive */
    public void driveForwardInches(double inches, double power) {
        int ticks = (int)(inches * TICKS_PER_INCH);

        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setTargetPosition(ticks);
        frontRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        while (frontLeft.isBusy() && frontRight.isBusy() &&
                backLeft.isBusy() && backRight.isBusy()) {
            // Optionally add telemetry updates here
        }

        setDrivePower(0.0);
    }

    /** New: field-centric drive using heading from AutonBase */
    public void driveWithHeading(double forward, double strafe, double headingDeg) {
        double h = Math.toRadians(headingDeg);

        double rotatedX = strafe * Math.cos(-h) - forward * Math.sin(-h);
        double rotatedY = strafe * Math.sin(-h) + forward * Math.cos(-h);

        double fl = rotatedY + rotatedX;
        double bl = rotatedY - rotatedX;
        double fr = rotatedY - rotatedX;
        double br = rotatedY + rotatedX;

        double max = Math.max(1.0, Math.max(Math.abs(fl),
                Math.max(Math.abs(bl),
                        Math.max(Math.abs(fr), Math.abs(br)))));

        frontLeft.setPower(fl / max);
        backLeft.setPower(bl / max);
        frontRight.setPower(fr / max);
        backRight.setPower(br / max);
    }

    /** New: simple in-place turn */
    public void turn(double power) {
        double p = Math.max(-1.0, Math.min(1.0, power));
        frontLeft.setPower(p);
        backLeft.setPower(p);
        frontRight.setPower(-p);
        backRight.setPower(-p);
    }

    /** New: stop all motors */
    public void stop() {
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addLine("=== DRIVE ===")
                .addData("Target FL", targetFL).addData("Actual FL", frontLeft.getVelocity())
                .addData("Target FR", targetFR).addData("Actual FR", frontRight.getVelocity())
                .addData("Target BL", targetBL).addData("Actual BL", backLeft.getVelocity())
                .addData("Target BR", targetBR).addData("Actual BR", backRight.getVelocity());
    }
}
