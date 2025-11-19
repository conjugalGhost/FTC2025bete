package org.firstinspires.ftc.teamcode.SubSystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Drive {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private BNO055IMU imu;
    private Orientation angles;
    private double velocityScale = 2000.0;
    private double targetFL, targetFR, targetBL, targetBR;

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

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.mode = BNO055IMU.SensorMode.IMU;
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(params);
    }

    public void driveWithGamepad(com.qualcomm.robotcore.hardware.Gamepad gamepad) {
        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x;
        double rx = gamepad.right_stick_x;

        angles = imu.getAngularOrientation();
        double heading = angles.firstAngle;
        double correction = heading * 0.05;

        double fl = y + x + rx + correction;
        double bl = y - x + rx + correction;
        double fr = y - x - rx + correction;
        double br = y + x - rx + correction;

        double max = Math.max(Math.abs(fl), Math.max(Math.abs(bl), Math.max(Math.abs(fr), Math.abs(br))));
        if (max > 1.0) { fl/=max; bl/=max; fr/=max; br/=max; }

        targetFL = fl * velocityScale;
        targetFR = fr * velocityScale;
        targetBL = bl * velocityScale;
        targetBR = br * velocityScale;

        frontLeft.setVelocity(targetFL);
        frontRight.setVelocity(targetFR);
        backLeft.setVelocity(targetBL);
        backRight.setVelocity(targetBR);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addLine("Drive Velocity Dashboard")
                .addData("T FL", targetFL).addData("A FL", frontLeft.getVelocity())
                .addData("T FR", targetFR).addData("A FR", frontRight.getVelocity())
                .addData("T BL", targetBL).addData("A BL", backLeft.getVelocity())
                .addData("T BR", targetBR).addData("A BR", backRight.getVelocity());
    }
}
