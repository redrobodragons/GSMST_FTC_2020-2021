package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team3819.Hardware;

@TeleOp(name="Teleop")

public class TestForForward extends OpMode {
    Hardware robot;
    double turn;

    @Override
    public void init() {
        robot = new Hardware(hardwareMap);
    }

    private void driverOne() {

    }

    @Override
    public void loop() {
        init();
        driverOne();
    }
}
