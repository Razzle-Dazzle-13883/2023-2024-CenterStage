package org.firstinspires.ftc.teamcode.TeleOperated;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Test Mechs")
public class Test_Mechs extends OpMode {

    //motors
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    DcMotor roller;
//    Servo claw;
//    Servo wrist;
//    DcMotor leftSlide;
//    DcMotor rightSlide;



    @Override
    public void init() {

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
//        leftSlide = hardwareMap.get(DcMotor.class, "leftSlide");
//        rightSlide = hardwareMap.get(DcMotor.class, "rightSlide");
        roller = hardwareMap.get(DcMotor.class, "spinner");
//        claw = hardwareMap.get(Servo.class, "leftClaw");
//        wrist = hardwareMap.get(Servo.class, "rightClaw");


        leftFront.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        roller.setPower(0);


        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
//        rightSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        if (gamepad1.a) {
            leftFront.setPower(0.5);
            if (gamepad1.a) {
                leftFront.setPower(0);
            }
        }

        if (gamepad1.b) {
            leftBack.setPower(0.5);
            if (gamepad1.b) {
                leftBack.setPower(0);
            }
        }

        if (gamepad1.x) {
            rightFront.setPower(0.5);
            if (gamepad1.x) {
                rightFront.setPower(0);
            }
        }

        if (gamepad1.y) {
            rightBack.setPower(0.5);
            if (gamepad1.y) {
                rightBack.setPower(0);
            }
        }

        if (gamepad1.right_bumper) {
            roller.setPower(0.5);
        }

        if (gamepad1.left_bumper) {
            roller.setPower(-0.5);
        }

        if (gamepad1.left_bumper && gamepad1.right_bumper) {
            roller.setPower(0);
        }

/*        if (gamepad1.dpad_up) {
            leftSlide.setPower(0.5);
            rightSlide.setPower(0.5);
        }

        if (gamepad1.dpad_up) {
            leftSlide.setPower(-0.5);
            rightSlide.setPower(-0.5);
        }

        if (gamepad1.dpad_left) {
            leftSlide.setPower(0);
            rightSlide.setPower(0);
        }
*/



    }
}