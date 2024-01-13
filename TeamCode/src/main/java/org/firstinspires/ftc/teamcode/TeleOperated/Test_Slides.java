package org.firstinspires.ftc.teamcode.TeleOperated;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//@TeleOp (name = "TestSlides")
public class Test_Slides extends OpMode {

    DcMotor leftLS;
    DcMotor rightLS;

    int leftLSPos;
    int rightLSPos;


    @Override
    public void init() {

        leftLS = hardwareMap.get(DcMotor.class, "leftLS");
        rightLS = hardwareMap.get(DcMotor.class, "rightLS");

        leftLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLS.setDirection(DcMotorSimple.Direction.REVERSE);

        leftLS.setPower(0);
        rightLS.setPower(0);
    }

    public void loop() {

        if (gamepad2.left_stick_y > 0.1 || gamepad2.left_stick_y < -0.1) {
            leftLS.setPower(gamepad2.left_stick_y / 5);
            rightLS.setPower(gamepad2.left_stick_y / 5);
        }

        if (gamepad1.dpad_up) {
            up(400, 400, 0.1);
        }
        if (gamepad1.dpad_down) {
            up(-400, -400, 0.1);
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



    }


}
