package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team3819.Hardware;

@TeleOp(name="TestTeleop")
@Disabled
public class TestTeleop extends OpMode {
    Hardware robot;
    double turn;

    @Override
    public void init() {
        robot = new Hardware(hardwareMap);
    }

    private void driverOne() {
        mehDrive(gamepad1);
    }

    @Override
    public void loop() {
        init();
        driverOne();
    }

    public void mehDrive(Gamepad gp)
    {
        if(gp.right_bumper)
        {
            robot.torqueBackLeft.setPower(1);
        }
        else if(gp.a)
        {
            robot.torqueBackRight.setPower(1);
        }
        else if(gp.left_bumper)
        {
            robot.torqueFrontLeft.setPower(1);
        }
        else if(gp.b)
        {
            robot.torqueFrontRight.setPower(1);
        }
        else
        {
            robot.frontRight.setPower(0);
            robot.backRight.setPower(0);
            robot.frontLeft.setPower(0);
            robot.backLeft.setPower(0);
        }
    }
}