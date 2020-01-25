package org.firstinspires.ftc.team2993;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class Hardware {

    private HardwareMap map = null;

    public DcMotorEx  frontLeft = null, frontRight = null, backLeft = null, backRight = null, lift = null, claw = null;    //DC Motors
//    public WebcamName Webcam1 = null;
    public DistanceSensor leftDist = null, rightDist = null, backDist =null;
    public static final double     PI  =  3.14159;
    public static final int        CPR = 560;                                 //encoder counts per revolution
    private static final double    DIAMETER = 4;                               //encoded drive wheel diameter (in)
    private static final double    GEARING = 1;
    public static final double     CPI = (CPR * GEARING) / (DIAMETER * PI);
    public static final double     CPF = CPI * 12;
    public static final double     TURNING_RADIUS = 7;
    public static final double     CIRCUMFRENCE = TURNING_RADIUS * 2 * PI;     //distnace 1 wheel travels in a full 360


    public Hardware(HardwareMap map){
        this.map = map;

        frontLeft = (DcMotorEx)map.get(DcMotor.class, "frontLeft");
        backLeft = (DcMotorEx)map.get(DcMotor.class, "backLeft");
        frontRight = (DcMotorEx)map.get(DcMotor.class, "frontRight");
        backRight = (DcMotorEx)map.get(DcMotor.class, "backRight");
        lift = (DcMotorEx)map.get(DcMotor.class, "lift");
        claw = (DcMotorEx)map.get(DcMotor.class, "claw");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDist = (DistanceSensor)map.get(DistanceSensor.class, "leftDist");
        rightDist = (DistanceSensor)map.get(DistanceSensor.class, "rightDist");
        backDist = (DistanceSensor)map.get(DistanceSensor.class, "backDist");

    }


    /*public void drive(int num) {
        left.setPower(num);
        right.setPower(num);
    }*/

    /*public void winch(float f) {
        liftFront.setPower(f);
        liftBack.setPower(f);
    }*/
    double deadZone = 0.05;
    double rightX = 0;
    double leftY = 0;
    double leftX = 0;
    public void driveMechanum(Gamepad gp){
        rightX = Math.abs(gp.right_stick_x)>=deadZone?gp.right_stick_x:0;
        leftY = Math.abs(gp.left_stick_y)>=(deadZone)?gp.left_stick_y:0;

        if(gp.dpad_left){
            frontLeft.setPower(-1);
            backLeft.setPower(1);
            frontRight.setPower(1);
            backRight.setPower(-1);

        }else if(gp.dpad_right){
            frontLeft.setPower(1);
            backLeft.setPower(-1);
            frontRight.setPower(-1);
            backRight.setPower(1);

        }else{
            frontLeft.setPower(-1*leftY+ rightX);
            backLeft.setPower(-1*leftY + rightX);
            frontRight.setPower(-1*leftY - rightX);
            backRight.setPower(-1*leftY - rightX);
        }
        if(gp.left_bumper){
            claw.setPower(.5);
        }else if(gp.left_trigger > .3){
            claw.setPower(-.3);
        }else{
            claw.setPower(0);
        }
        if(gp.right_bumper){
            lift.setPower(.60);

        }else if(gp.right_trigger > .1){
            lift.setPower(-gp.right_trigger/1.5);
            claw.setPower(.5);
        }else{
            lift.setPower(0);
        }


    }


    public void driveInches(double pow, int in)
    {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int target = (int)(in*CPI);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition(target);
        frontRight.setTargetPosition(target);
        backLeft.setTargetPosition(target);
        backRight.setTargetPosition(target);

        frontLeft.setPower(pow);
        frontRight.setPower(pow);
        backLeft.setPower(pow);
        backRight.setPower(pow);

        while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy())
        {
            //waiting until done running
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void turn(double pow, double degrees)
    {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition(-1*(int)((360.0/degrees)*CIRCUMFRENCE*CPI));
        frontRight.setTargetPosition((int)((360.0/degrees)*CIRCUMFRENCE*CPI));
        backLeft.setTargetPosition(-1*(int)((360.0/degrees)*CIRCUMFRENCE*CPI));
        backRight.setTargetPosition((int)((360.0/degrees)*CIRCUMFRENCE*CPI));
    }

    public void moveDrive(double forward, double yaw, double lateral){
        frontLeft.setPower(forward+ yaw + lateral);
        backLeft.setPower(forward + yaw - lateral);
        frontRight.setPower(forward - yaw - lateral);
        backRight.setPower(forward - yaw + lateral);
    }

    public void setAllDrive(double power){
        frontLeft.setPower(power);
        backLeft.setPower(power);
        frontRight.setPower(power);
        backRight.setPower(power);
    }

    public void setLift(double power){
        lift.setPower(power);
    }

    public void turnDrive(double power){
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(power);
        backRight.setPower(power);
    }
}