package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "SlidesTest")
public class SlidesTest extends LinearOpMode {

    DcMotor leftLS;
    DcMotor rightLS;
//    DcMotor roller;

    int leftLSPos;
    int rightLSPos;

    final int TICKS_PER_INCH = 45; // 11.87 in per rev; 537.7 ticks per rev; 537.7/11.87 ticks per inch


    @Override
    public void runOpMode() throws InterruptedException {

        leftLS = hardwareMap.get(DcMotor.class, "leftLS");
        rightLS = hardwareMap.get(DcMotor.class, "rightLS");
//        roller = hardwareMap.get(DcMotor.class, "roller");

        leftLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLS.setDirection(DcMotorSimple.Direction.REVERSE);

        leftLS.setPower(0);
        rightLS.setPower(0);

        waitForStart();
//        roller.setPower(0.7);

        up(27 * TICKS_PER_INCH, 27 * TICKS_PER_INCH, 0.5);

        sleep(1000);

        up(-27 * TICKS_PER_INCH, -27 * TICKS_PER_INCH, 0.5);

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
