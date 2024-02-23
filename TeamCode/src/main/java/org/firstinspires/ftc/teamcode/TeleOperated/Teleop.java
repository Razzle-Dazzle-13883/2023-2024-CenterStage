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

@TeleOp (name = "Teleop")
public class Teleop extends LinearOpMode {
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;

    DcMotor roller;
    DcMotor leftLS;
    DcMotor rightLS;

    Servo airplane;
    Servo claw;
    Servo armRight;
    Servo armLeft;

    int leftPos;
    int rightPos;

    boolean armHold = false;

    final int TICKS_PER_INCH = 45; // 11.87 in per rev; 537.7 ticks per rev; 537.7/11.87 ticks per inch


    @Override
    public void runOpMode() throws InterruptedException {

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");


        roller = hardwareMap.dcMotor.get("roller");
        leftLS = hardwareMap.dcMotor.get("leftLS");
        rightLS = hardwareMap.dcMotor.get("rightLS");


        airplane = hardwareMap.servo.get("airplane");
        armLeft = hardwareMap.servo.get("armLeft");
        armRight = hardwareMap.servo.get("armRight");
        claw = hardwareMap.servo.get("claw");


        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLS.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {


            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            double frontLeftPower = 0;
            double backLeftPower = 0;
            double frontRightPower = 0;
            double backRightPower = 0;

            frontLeftPower = (y + x + rx);
            backLeftPower = (y - x + rx);
            frontRightPower = (y - x - rx);
            backRightPower = (y + x - rx);

            leftFront.setPower(frontLeftPower);
            leftBack.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightBack.setPower(backRightPower);

            if (gamepad1.left_stick_y == 0) {
                leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            else if (gamepad1.right_stick_x == 0) {
                leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            telemetry.addData("leftFront", leftFront.getCurrentPosition());
            telemetry.addData("leftBack", leftBack.getCurrentPosition());
            telemetry.addData("rightFront", rightFront.getCurrentPosition());
            telemetry.addData("rightBack", rightBack.getCurrentPosition());
            telemetry.update();


            if(gamepad1.a){
                airplane.setPosition(1);
            }

            if (gamepad2.x) {
                roller.setPower(1);
            }

            if (gamepad2.a) {
                roller.setPower(-1);
            }

            if (gamepad2.dpad_right) {
                roller.setPower(0);
            }

            if (gamepad2.dpad_up) {
                up(5 * TICKS_PER_INCH, 5 * TICKS_PER_INCH, 0.5);
            }

            if (gamepad2.right_bumper) {
                claw.setPosition(1);
            }
            telemetry.addData("Claw Pos: ", claw.getPosition());
            if (gamepad2.left_bumper) {
                claw.setPosition(0);
            }

            if (gamepad2.y) {
                armRight.setPosition(0.4);
            }

            if (gamepad2.b) {
                armRight.setPosition(0);
            }


            if (gamepad2.dpad_down) {
                claw.setPosition(0);
                claw.setPosition(1);
                sleep(50);
                armRight.setPosition(0);
                down((20) * TICKS_PER_INCH, (20) * TICKS_PER_INCH, 0.5);
                claw.setPosition(0);
            }


            if (gamepad2.dpad_left) {
                down (5 * TICKS_PER_INCH, 5 * TICKS_PER_INCH, 0.1);
            }

        }
    }

    public void up(int left, int right, double speed) {
        leftPos -= left;
        rightPos -= right;

        leftLS.setTargetPosition(leftPos);
        rightLS.setTargetPosition(rightPos);

        leftLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftLS.setPower(speed);
        rightLS.setPower(speed);

    }

    public void down(int left, int right, double speed) {
        leftPos += left;
        rightPos += right;

        leftLS.setTargetPosition(leftPos);
        rightLS.setTargetPosition(rightPos);

        leftLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLS.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftLS.setPower(speed);
        rightLS.setPower(speed);

    }

}
