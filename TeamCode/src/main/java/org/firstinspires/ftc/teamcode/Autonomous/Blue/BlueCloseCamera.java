package org.firstinspires.ftc.teamcode.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Autonomous.FirstVisionProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

import org.firstinspires.ftc.teamcode.Autonomous.Blue.Blue;

@Autonomous()
public class BlueCloseCamera extends LinearOpMode {
    private FirstVisionProcessor visionProcessor;
    private VisionPortal visionPortal;

    Blue blue = new Blue();

    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;

    DcMotor roller;
    DcMotor leftLS;
    DcMotor rightLS;

    Servo arm;
    Servo claw;

    int leftFrontPos = 0;
    int leftBackPos = 0;
    int rightFrontPos = 0;
    int rightBackPos = 0;

    int leftLSPos;
    int rightLSPos;

    final int TICKS_PER_INCH = 45; // 11.87 in per rev; 537.7 ticks per rev; 537.7/11.87 ticks per inch



    @Override
    public void runOpMode() {

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        roller = hardwareMap.get(DcMotor.class, "roller");
        leftLS = hardwareMap.get(DcMotor.class, "leftLS");
        rightLS = hardwareMap.get(DcMotor.class, "rightLS");

        arm = hardwareMap.get(Servo.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

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
        leftLS.setDirection(DcMotorSimple.Direction.REVERSE);


        visionProcessor = new FirstVisionProcessor();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), visionProcessor);

        telemetry.addData("Identified", visionProcessor.getSelection());



        waitForStart();


         if (visionProcessor.getSelection() == FirstVisionProcessor.Selected.LEFT) {

            blue.blueLeftClose();

         }
         else if (visionProcessor.getSelection() == FirstVisionProcessor.Selected.MIDDLE) {

            blue.blueMiddleClose();

         }
         else if (visionProcessor.getSelection() == FirstVisionProcessor.Selected.RIGHT) {

            blue.blueRightClose();

         }

    }

    public void drive(int lF, int lB, int rF, int rB, double speed) {

        leftFrontPos -= lF;
        leftBackPos -= lB;
        rightFrontPos -= rF;
        rightBackPos -= rB;

        leftFront.setTargetPosition(leftFrontPos);
        leftBack.setTargetPosition(leftBackPos);
        rightFront.setTargetPosition(rightFrontPos);
        rightBack.setTargetPosition(rightBackPos);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double driftFactor = 1.042;
        leftFront.setPower(speed);
        leftBack.setPower(speed);
        rightFront.setPower(speed * driftFactor);
        rightBack.setPower(speed * driftFactor);




        while (opModeIsActive() && leftFront.isBusy() &&  leftBack.isBusy() && rightFront.isBusy() &&  rightBack.isBusy()) {
            idle();
            telemetry.addData("LeftFront: ", leftFront.getCurrentPosition());
            telemetry.addData("LeftBack: ", leftBack.getCurrentPosition());
            telemetry.addData("RightFront", rightFront.getCurrentPosition());
            telemetry.addData("RightBack:", rightBack.getCurrentPosition());

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
    public void down(int left, int right, double speed) {

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
