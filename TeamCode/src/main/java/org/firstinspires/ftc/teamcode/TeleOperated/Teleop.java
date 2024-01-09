package org.firstinspires.ftc.teamcode.TeleOperated;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="TeleOp")
public class Teleop extends OpMode {

    //motors
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;
    Servo leftClaw;
    Servo rightClaw;
    DcMotor roller;

    int leftLSPos = 0;
    int rightLSPos = 0;





    @Override
    public void init() {

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "rightFront");
        rightFront = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        roller = hardwareMap.get(DcMotor.class, "spinner");


        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        roller.setPower(0);


        leftClaw.setPosition(0.3);
        rightClaw.setPosition(0.75);



        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {


        //strafing stuff + Turbomode stuff
        double strafe = -gamepad1.left_stick_y;
        double forward = gamepad1.left_stick_x * 1.1;
        double r = -gamepad1.right_stick_x;




        leftFront.setPower((forward + strafe + r) / 1.5);
        leftBack.setPower((forward - strafe + r) / 1.5);
        rightFront.setPower((forward - strafe - r) / 1.5);
        rightBack.setPower((forward + strafe - r) / 1.5);


    }

/*    public void drive(int left, int right, double speed) {

        leftLSPos -= left;
        rightLSPos -= right;

        leftLS.setTargetPosition(leftLSPos);
        right.setTargetPosition(rightLSPos);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        leftBack.setPower(speed);



    }
*/
}