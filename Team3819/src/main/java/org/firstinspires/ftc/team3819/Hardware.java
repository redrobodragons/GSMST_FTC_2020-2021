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

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class Hardware {

    private HardwareMap map = null;

    public DcMotorEx  frontLeft = null, frontRight = null, backLeft = null, backRight = null,
            torqueBackRight = null, torqueFrontRight = null, vexIntake = null, torqueFrontLeft = null;   //DC Motors
    public Servo servoBackRight = null, servoBackLeft = null, servoIntake = null, vexRight = null;
    //public CRServo vexRight = null; //vexLeft = null;
    public WebcamName Webcam1 = null;

    public static final double     PI  =  3.14159;
    public static final int        CPR = 560;                                 //encoder counts per revolution 140 or 560
    public static final double    DIAMETER = 3.86;                               //encoded drive wheel diameter (in)
    public static final double    GEARING = 1;
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

        vexIntake = (DcMotorEx)map.get(DcMotor.class, "vexIntake");
        torqueBackRight = (DcMotorEx)map.get(DcMotor.class, "torqueBackRight");
        torqueFrontLeft = (DcMotorEx)map.get(DcMotor.class, "torqueFrontLeft");
        torqueFrontRight = (DcMotorEx)map.get(DcMotor.class, "torqueFrontRight");

        /*servoBackLeft = (Servo)map.get(Servo.class, "servoBackLeft");
        servoBackRight = (Servo)map.get(Servo.class, "servoBackRight");
        servoIntake = (Servo)map.get(Servo.class, "servoIntake");
        vexRight = (Servo)map.get(Servo.class, "vexRight");*/

        //vexLeft = (CRServo)map.get(CRServo.class, "vexLeft");
        //vexRight = (CRServo)map.get(CRServo.class, "vexRight");
        //Webcam1 = map.get(WebcamName.class, "Webcam 1");

        //left.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        torqueFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        vexIntake.setDirection(DcMotorSimple.Direction.REVERSE);

        //left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    /*public void drive(int num) {
        left.setPower(num);
        right.setPower(num);
    }*/

    /*public void winch(float f) {
        liftFront.setPower(f);
        liftBack.setPower(f);
    }*/

    public void drive(Gamepad gp) {
        double turn = 0;
        if(Math.abs(gp.right_stick_x)>=.05 || Math.abs(gp.right_stick_y)>=.05) {
            if(Math.abs(gp.right_stick_x)>=.1) {
                turn = gp.right_stick_x;
            }
            frontLeft.setPower(-1 * gp.right_stick_y + turn);
            backLeft.setPower(-1 * gp.right_stick_y + turn);
            frontRight.setPower(-1 * gp.right_stick_y - turn);
            backRight.setPower(-1 * gp.right_stick_y - turn);
        }
        else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }


    }

    public void driveDos(Gamepad gp)
    {
        double turn = 0;
        /*if(Math.abs(gp.left_stick_x)>=.05 || Math.abs(gp.right_stick_y)>=.05) {
            if(Math.abs(gp.left_stick_x)>=.05) {
                turn = gp.left_stick_x;
            }
            frontLeft.setPower((-1 * gp.right_stick_y + turn)*2);
            backLeft.setPower((-1 * gp.right_stick_y + turn)*2);
            frontRight.setPower((-1 * gp.right_stick_y - turn)*2);
            backRight.setPower((-1 * gp.right_stick_y - turn)*2);
        }*/
        if(Math.abs(gp.left_stick_y)>=.05 || Math.abs(gp.right_stick_x)>=.05) {
            if(Math.abs(gp.right_stick_x)>=.05) {
                turn = gp.right_stick_x;
            }
            frontLeft.setPower((-1 * gp.left_stick_y + turn)*2);
            backLeft.setPower((-1 * gp.left_stick_y + turn)*2);
            frontRight.setPower((-1 * gp.left_stick_y - turn)*2);
            backRight.setPower((-1 * gp.left_stick_y - turn)*2);
        }
        else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        if(gp.dpad_left)
        {
            frontLeft.setPower(-1);
            backLeft.setPower(1);
            frontRight.setPower(1);
            backRight.setPower(-1);
        }
        else
        {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        if(gp.dpad_right)
        {
            frontLeft.setPower(1);
            backLeft.setPower(-1);
            frontRight.setPower(-1);
            backRight.setPower(1);
        }
        else
        {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        /*if(gp.dpad_up)
        {
            //vexRight.setPower(0.5);
            //vexLeft.setPower(1);
            vexRight.setPosition(0.5);
        }

        if(gp.dpad_down)
        {
            //vexRight.setPower(-0.5);
            //vexLeft.setPower(-1);
        }*/

        if(gp.right_bumper)
        {
            vexIntake.setPower(1);
        }
        else
        {
            vexIntake.setPower(0);
        }

        if(gp.left_bumper)
        {
            vexIntake.setPower(-0.5);
        }
        else
        {
            vexIntake.setPower(0);
        }

        if(gp.dpad_up)
        {
            torqueFrontRight.setPower(1);
            torqueFrontLeft.setPower(1);
            torqueBackRight.setPower(1);
            //vexIntake.setPower(1);
        }
        else
        {
            torqueFrontRight.setPower(0);
            torqueFrontLeft.setPower(0);
            torqueBackRight.setPower(0);
            //vexIntake.setPower(0);
        }

        if(gp.dpad_down)
        {
            torqueFrontRight.setPower(-0.6);
            torqueFrontLeft.setPower(-0.6);
            torqueBackRight.setPower(-1);
            //vexIntake.setPower(-0.6);
        }
        else
        {
            torqueFrontRight.setPower(0);
            torqueFrontLeft.setPower(0);
            torqueBackRight.setPower(0);
            //vexIntake.setPower(0);
        }




        /*if(gp.a)
        {
            servoBackRight.setPosition(-1);
            servoBackLeft.setPosition(1);
        }

        if(gp.b)
        {
            servoBackLeft.setPosition(0.5);
            servoBackRight.setPosition(0.5);
        }/*

        if(gp.left_bumper)
        {
            torqueBackRight.setPower(1);
        }
        else
        {
            torqueBackRight.setPower(0);
        }

        if(gp.right_bumper)
        {
            torqueBackRight.setPower(-1);
        }
        else
        {
            torqueBackRight.setPower(0);
        }

        /*if(gp.x)
        {
            servoIntake.setPosition(0.5);
            servoIntake.setPosition(0.5);
        }/*


        /*if(gp.left_bumper)
        {
            frontLeft.setPower(1);
            backLeft.setPower(-1);
            frontRight.setPower(-1);
            backRight.setPower(1);
        }
        else
        {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        if(gp.right_bumper)
        {
            frontLeft.setPower(-1);
            backLeft.setPower(1);
            frontRight.setPower(1);
            backRight.setPower(-1);
        }*/


    }

    /*public void driveInches(double pow, int in) {
        resetEncoders();
        motorControllerEx = (DcMotorControllerEx)left.getController();
        PIDFCoefficients pidfNew = new PIDFCoefficients(128, 40, 192, 57);
        int motorIndexL = ((DcMotorEx)left).getPortNumber();
        int motorIndexR = ((DcMotorEx)left).getPortNumber();

        int target = (int)(in*CPI);

        //motorControllerEx.setPIDFCoefficients(motorIndexL,DcMotor.RunMode.RUN_TO_POSITION,pidfNew);
        //motorControllerEx.setPIDFCoefficients(motorIndexR,DcMotor.RunMode.RUN_TO_POSITION,pidfNew);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setTargetPosition(target);
        right.setTargetPosition(target);

        //left.setDirection(DcMotorSimple.Direction.REVERSE);
        //right.setDirection(DcMotorSimple.Direction.FORWARD);

        //while( (left.getCurrentPosition()>target + 10||left.getCurrentPosition()<target-10) &&
        //      (right.getCurrentPosition()>target + 10||right.getCurrentPosition()<target-10) ) {
        left.setPower(pow);
        right.setPower(pow);

        //left.setDirection(DcMotorSimple.Direction.FORWARD);
        //right.setDirection(DcMotorSimple.Direction.REVERSE);
    }*/


    /*public void turn(double pow, double degrees) {
        resetEncoders();

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setTargetPosition(-1*(int)((360.0/degrees)*CIRCUMFRENCE*CPI));
        right.setTargetPosition((int)((360.0/degrees)*CIRCUMFRENCE*CPI));

        left.setPower(pow);
        right.setPower(pow);
    }*/

    /*public void armUp() {
        armLeft.setPower(1);
        armRight.setPower(1);
    }

    public void armDown() {
        armLeft.setPower(-.05);
        armRight.setPower(-.05);
    }

    public  void armStop() {
        armLeft.setPower(0);
        armRight.setPower(0);
    }

    public void servoUp() {
        servo.setPosition(1);
    }

    public void servoDown() {
        servo.setPosition(0.5);
    }

    public void intake() {intake.setPower(-.75); }

    public void intake(float f) {intake.setPower(f); }

    public void outtake() {intake.setPower(.75); }

    public void outtake(float f) {intake.setPower(-f); }

    public void donttake() {
        intake.setPower(0);
    }

    public void stop() {
        drive(0);
    }

    public boolean driveIsBusy(){
        return left.isBusy() || right.isBusy();
    }

    public boolean isBusy(){
        return left.isBusy() || right.isBusy();
    }*/

    /*public void resetEncoders(){
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }*/

    public void resetEncoders(){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void drive(int num)
    {
        frontRight.setPower(num);
        frontLeft.setPower(num);
        backRight.setPower(num);
        backLeft.setPower(num);
    }

    public void stop()
    {
        drive(0);
    }


}