package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@Autonomous
public class pidTestFTCLib extends OpMode {
    private PIDController controller;

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    private final double TICKS_IN_DEGREE = 537.7 / 360.0;

    private DcMotorEx leftLS;

    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        leftLS = hardwareMap.get(DcMotorEx.class, "leftLS");
    }
    @Override
    public void loop() {

        controller.setPID(p, i, d);
        int leftLSPos = leftLS.getCurrentPosition();
        double pid = controller.calculate(leftLSPos, target);
        double ff = Math.cos(Math.toRadians(target / TICKS_IN_DEGREE)) * f;

        double power = pid + ff;

        leftLS.setPower(power);

        telemetry.addData("leftLSPos", leftLSPos);
        telemetry.addData("target", target);

    }

}
