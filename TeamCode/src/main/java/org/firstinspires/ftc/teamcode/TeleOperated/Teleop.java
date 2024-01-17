package org.firstinspires.ftc.teamcode.TeleOperated;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class Teleop extends LinearOpMode {
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor roller;


    DcMotor leftLS;
    DcMotor rightLS;

    Servo wrist;
    Servo claw;

    int leftLSPos;
    int rightLSPos;
    final int TICKS_PER_INCH = 45; // 11.87 in per rev; 537.7 ticks per rev; 537.7/11.87 ticks per inch


    @Override
    public void runOpMode() throws InterruptedException {

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        leftLS = hardwareMap.dcMotor.get("leftLS");
        rightLS = hardwareMap.dcMotor.get("rightLS");
        roller = hardwareMap.dcMotor.get("spinner");

        wrist = hardwareMap.servo.get("wrist");
        claw = hardwareMap.servo.get("claw");

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLS.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                                                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {


            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double frontLeftPower = 0;
            double backLeftPower = 0;
            double frontRightPower = 0;
            double backRightPower = 0;

            if (gamepad1.options) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double rotX = x * Math.cos(-botHeading) - y  * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            if (gamepad1.left_stick_button) {
                frontLeftPower = (rotY + rotX + rx);
                backLeftPower = (rotY - rotX + rx);
                frontRightPower = (rotY - rotX - rx);
                backRightPower = (rotY + rotX - rx);

                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);

            }

            if (gamepad1.right_stick_button) {
                frontLeftPower = (y + x + rx);
                backLeftPower = (y - x - rx);
                frontRightPower = (y - x - rx);
                backRightPower = (y + x - rx);

                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);


            }


            if (gamepad1.a) {
                wrist.setPosition(0);
            }

            if (gamepad1.y) {
                wrist.setPosition(0.1);
            }

            if (gamepad1.right_bumper) {
                claw.setPosition(0.7);
            }

            if (gamepad1.left_bumper) {
                claw.setPosition(0.5);
            }

            if (gamepad1.x) {
                roller.setPower(0.6);
                if (gamepad1.x) {
                    roller.setPower(0);
                }
            }

            if (gamepad1.b) {
                roller.setPower(-0.6);
                if (gamepad1.b) {
                    roller.setPower(0);
                }
            }

            if (gamepad1.dpad_up) {
                up (10 * TICKS_PER_INCH, 10 * TICKS_PER_INCH, 0.5);
            }

            if (gamepad1.dpad_down) {
                up (-10 * TICKS_PER_INCH, -10 * TICKS_PER_INCH, 0.5);
            }


        }
    }
    public void up(int left, int right, double speed) {

        leftLSPos += left;
        rightLSPos += right;

        leftLS.setTargetPosition(leftLSPos);
        rightLS.setTargetPosition(rightLSPos);

        leftLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftLS.setPower(speed);
        rightLS.setPower(speed);

        while (opModeIsActive() && leftLS.isBusy() && rightLS.isBusy()) {
            idle();
        }

    }

}
