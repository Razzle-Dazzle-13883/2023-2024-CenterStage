package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Autonomous
public class pidTest extends LinearOpMode {


    DcMotorEx leftLS;
    DcMotorEx rightLS;

    double integralSum = 0;
    double Kp = 0;
    double Ki = 0;
    double Kd = 0;

    double output = 0;
    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        leftLS = hardwareMap.get(DcMotorEx.class, "leftLS");
        rightLS = hardwareMap.get(DcMotorEx.class, "rightLS");

        leftLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLS.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();
        while (opModeIsActive()) {

            double leftPower = PIDControl(100, leftLS.getCurrentPosition());
            double rightPower = PIDControl(100, rightLS.getCurrentPosition());
            leftLS.setPower(leftPower);
            rightLS.setPower(rightPower);

        }

    }

    public double PIDControl (double reference, double state) {

        double error = reference - state;
        integralSum += error * timer.seconds();
        double derivative = (error - lastError ) / timer.seconds();
        lastError = error;

        timer.reset();

        output = (error * Kp) + (derivative * Kd) + (integralSum * Ki);

        return output;
    }

}
