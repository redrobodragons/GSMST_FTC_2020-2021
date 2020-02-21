package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class Hardware {

    private HardwareMap map = null;

    public DcMotorEx frontLeft = null, frontRight = null, backLeft = null, backRight = null; //DC Motors


    public double stickX = 0;
    public double stickY = 0;
    public double rotate = 0;


    public Hardware(HardwareMap map) {
        this.map = map;

        frontLeft = (DcMotorEx) map.get(DcMotor.class, "frontLeft");
        backLeft = (DcMotorEx) map.get(DcMotor.class, "backLeft");
        frontRight = (DcMotorEx) map.get(DcMotor.class, "frontRight");
        backRight = (DcMotorEx) map.get(DcMotor.class, "backRight");

        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void MechDrive(Gamepad gp)
    {
        if(Math.abs(gp.left_stick_x) < 0.05){ stickX = 0; }
        else{ stickX = gp.left_stick_x; }
        if(Math.abs(gp.left_stick_y) < 0.05){ stickY = 0; }
        else{ stickY = gp.left_stick_y; }
        if(Math.abs(gp.right_stick_x) < 0.05){ rotate = 0; }
        else{ rotate = gp.right_stick_x; }

        double magnitude = Math.hypot(stickX, -stickY); //deadzones are incorporated into these values
        double phi = Math.atan2(-stickY, stickX) - Math.PI / 4;
        double turnRight = rotate/1.25;
        final double fL = magnitude * Math.cos(phi) + turnRight;
        final double fR = magnitude * Math.sin(phi) - turnRight;
        final double bL = magnitude * Math.sin(phi) + turnRight;
        final double bR = magnitude * Math.cos(phi) - turnRight;

        frontLeft.setPower(fL);
        frontRight.setPower(fR);
        backLeft.setPower(bL);
        backRight.setPower(bR);

    }

}