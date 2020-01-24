package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends OpMode {
    Hardware robot;

    @Override
    public void init() {
        robot = new Hardware(hardwareMap);
    }
    private void driveRobot(){
        robot.driveMechanum(gamepad1);
        telemetry.addLine("Y stick 1 " + gamepad1.left_stick_y);
        telemetry.addLine("X stick 2 " + gamepad1.right_stick_x);
        telemetry.addLine("Left Trigger " + gamepad1.left_trigger);
        telemetry.update();
    }
    @Override
    public void loop(){
        driveRobot();
    }
}