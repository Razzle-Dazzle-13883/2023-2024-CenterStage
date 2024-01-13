package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "RollerStamp")
public class RollerTest extends LinearOpMode {

    DcMotor roller;

    @Override
    public void runOpMode() throws InterruptedException {
        roller = hardwareMap.get(DcMotor.class, "spinner");

//        roller.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        roller.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        waitForStart();

        while(opModeIsActive()) {
            //roller.setTargetPosition(400);

            //roller.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            roller.setPower(0.75);
        }
    }

}
