package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team3819.Hardware;

@TeleOp(name="TestTeleop")
public class TestTeleop extends OpMode {
    Hardware robot;
    double turn;

    @Override
    public void init()
    {
        robot = new Hardware(hardwareMap);
    }

    private void driverOne()
    {
        mehDrive(gamepad1);
    }

    @Override
    public void loop() {
        init();
        driverOne();
    }

    public void mehDrive(Gamepad gp)
    {
        
    }
}