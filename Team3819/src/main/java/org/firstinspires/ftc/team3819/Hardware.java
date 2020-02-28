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

    public DcMotorEx frontLeft = null, frontRight = null, backLeft = null, backRight = null,
            torqueTop = null, torqueBottom = null, vexRight = null, vexLeft = null; //DC Motors
    public Servo foundRight = null, foundLeft = null, claw = null;
    public Servo liftTop = null, liftBottom = null;
    public WebcamName Webcam1 = null;

    public static final double PI = 3.14159;
    public static final int CPR = 537;                                 //encoder counts per revolution 140 or 560
    public static final double DIAMETER = 3.86;                               //encoded drive wheel diameter (in)
    public static final double GEARING = 1;
    public static final double CPI = (CPR * GEARING) / (DIAMETER * PI);
    public static final double CPF = CPI * 12;
    public static final double TURNING_RADIUS = 7;
    public static final double CIRCUMFRENCE = TURNING_RADIUS * 2 * PI;     //distnace 1 wheel travels in a full 360


    private ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public static boolean foundation = true;
    public static boolean clawD = true;
    public static boolean swivel = false;
    public static boolean fHit = false;

    public static boolean hasRun = false;

    public static boolean driveNeg = false;
    public int toNeg = 1;

    long setTime = System.currentTimeMillis();

    public double stickX = 0;
    public double stickY = 0;
    public double rotate = 0;


    public Hardware(HardwareMap map) {
        this.map = map;

        frontLeft = (DcMotorEx) map.get(DcMotor.class, "frontLeft");
        backLeft = (DcMotorEx) map.get(DcMotor.class, "backLeft");
        frontRight = (DcMotorEx) map.get(DcMotor.class, "frontRight");
        backRight = (DcMotorEx) map.get(DcMotor.class, "backRight");

        torqueTop = (DcMotorEx) map.get(DcMotor.class, "torqueTop");
        torqueBottom = (DcMotorEx) map.get(DcMotor.class, "torqueBottom");
        vexRight = (DcMotorEx) map.get(DcMotor.class, "vexRight");
        vexLeft = (DcMotorEx) map.get(DcMotor.class, "vexLeft");

        foundRight = (Servo)map.get(Servo.class, "foundRight");
        foundLeft = (Servo)map.get(Servo.class, "foundLeft");
        liftTop = (Servo)map.get(Servo.class, "liftTop");
        liftBottom = (Servo)map.get(Servo.class, "liftBottom");
        claw = (Servo)map.get(Servo.class, "claw");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);


        //vexLeft = (CRServo)map.get(CRServo.class, "vexLeft");
        //Webcam1 = map.get(WebcamName.class, "Webcam 1");
        /*backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        vexIntake.setDirection(DcMotorSimple.Direction.REVERSE);*/
        //left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    public void driveOnly(Gamepad gp) {
        double turn2 = 0;
        if (Math.abs(gp.left_stick_y) >= .05 || Math.abs(gp.right_stick_x) >= .05) {
            if (Math.abs(gp.right_stick_x) >= .05) {
                turn2 = gp.right_stick_x;
            }
            frontLeft.setPower((-1 * gp.left_stick_y + turn2));
            backLeft.setPower((-1 * gp.left_stick_y + turn2));
            frontRight.setPower((-1 * gp.left_stick_y - turn2));
            backRight.setPower((-1 * gp.left_stick_y - turn2));
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
    }

    public void driveOriginal(Gamepad gp) {
        double turn = 0;
        if (Math.abs(gp.right_stick_x) >= .05 || Math.abs(gp.right_stick_y) >= .05) {
            if (Math.abs(gp.right_stick_x) >= .1) {
                turn = gp.right_stick_x;
            }
            frontLeft.setPower(-1 * gp.right_stick_y + turn);
            backLeft.setPower(-1 * gp.right_stick_y + turn);
            frontRight.setPower(-1 * gp.right_stick_y - turn);
            backRight.setPower(-1 * gp.right_stick_y - turn);
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }


    }

    public void driveDos(Gamepad gp) {
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
        if (Math.abs(gp.left_stick_y) >= .05 || Math.abs(gp.right_stick_x) >= .05) {
            if (Math.abs(gp.right_stick_x) >= .05) {
                turn = gp.right_stick_x;
            }
            frontLeft.setPower((-1 * gp.left_stick_y + turn));
            backLeft.setPower((-1 * gp.left_stick_y + turn));
            frontRight.setPower((-1 * gp.left_stick_y - turn));
            backRight.setPower((-1 * gp.left_stick_y - turn));
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        if (gp.dpad_left) {
            frontLeft.setPower(-1);
            backLeft.setPower(1);
            frontRight.setPower(1);
            backRight.setPower(-1);
        }
        else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        if (gp.dpad_right) {
            frontLeft.setPower(1);
            backLeft.setPower(-1);
            frontRight.setPower(-1);
            backRight.setPower(1);
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }


        if (gp.right_bumper) {
            vexRight.setPower(-1);
            vexLeft.setPower(-1);
        } else {
            vexRight.setPower(0);
            vexLeft.setPower(0);
        }

        if (gp.right_trigger>=0.1) {
            vexRight.setPower(1);
            vexLeft.setPower(1);
        } else {
            vexRight.setPower(0);
            vexLeft.setPower(0);
        }

        if (gp.dpad_up) {
            torqueTop.setPower(1);
            torqueBottom.setPower(1);
        } else {
            torqueTop.setPower(0);
            torqueBottom.setPower(0);
        }

        if (gp.dpad_down) {
            torqueTop.setPower(-1);
            torqueBottom.setPower(-1);
        } else {
            torqueTop.setPower(0);
            torqueBottom.setPower(0);
        }


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

        frontLeft.setPower(fL*toNeg);
        frontRight.setPower(fR*toNeg);
        backLeft.setPower(bL*toNeg);
        backRight.setPower(bR*toNeg);


        if (gp.right_bumper) { //intake
            vexRight.setPower(1);
            vexLeft.setPower(-1);
        } else {
            vexRight.setPower(0);
            vexLeft.setPower(0);
        }

        if (gp.right_trigger>=0.1) { //outtake
            vexRight.setPower(-1);
            vexLeft.setPower(1);
        } else {
            vexRight.setPower(0);
            vexLeft.setPower(0);
        }

        if (gp.left_bumper) { //lift going up
            torqueTop.setPower(-0.6);
            torqueBottom.setPower(-0.6);
        } else {
            torqueTop.setPower(0);
            torqueBottom.setPower(0);
        }

        if (gp.left_trigger>=0.1) { //lift going down
            torqueTop.setPower(0.4);
            torqueBottom.setPower(0.4);
        } else {
            torqueTop.setPower(0);
            torqueBottom.setPower(0);
        }

//        if (gp.b) //foundation not clasped
//        {
//            if(fHit == false) {
//                fHit = true;
//
//                if (foundation == true) {
//                    foundLeft.setPosition(0);
//                    foundRight.setPosition(1);
//                } else if (foundation == false) {
//                    foundLeft.setPosition(1);
//                    foundRight.setPosition(0);
//                }
//                fHit = false;
//                foundation = !foundation;
//            }
//
//        }

        if (gp.b)
        {
            while(gp.b){

            }
            if (foundation == true) {
                foundLeft.setPosition(0);
                foundRight.setPosition(1);
            } else if (foundation == false) {
                foundLeft.setPosition(1);
                foundRight.setPosition(0);
            }
            foundation = !foundation;
        }

        if (gp.a)
        {
            while(gp.a){

            }
            if (clawD == true) {
                claw.setPosition(0);
            } else if (clawD == false) {
                claw.setPosition(1);
            }
            clawD = !clawD;
        }

        if(gp.y)
        {
            while(gp.y)
            {

            }

            toNeg = -1*toNeg;
        }

//        if (gp.a) //foundation not clasped
//        {
//            if(fHit == false) {
//                fHit = true;
//
//                if (clawD == true) {
//                    claw.setPosition(0);
//                } else if (clawD == false) {
//                    claw.setPosition(1);
//                }
//                fHit = false;
//                clawD = !clawD;
//            }
//
//        }
//
//        if (gp.x) //foundation not clasped
//        {
//            if(fHit == false) {
//                fHit = true;
//
//                if (swivel == true) {
//                    liftTop.setPosition(0);
//                    liftBottom.setPosition(1);
//                } else if (swivel == false) {
//                    liftTop.setPosition(1);
//                    liftBottom.setPosition(0);
//                }
//                fHit = false;
//                swivel = !swivel;
//            }
//
//        }

        if (gp.x)
        {
            while(gp.x){

            }
            if (swivel == true) {
                torqueTop.setPower(-0.6);
                torqueBottom.setPower(-0.6);
                if(System.currentTimeMillis() - setTime > 100)
                {
                    hasRun = true;
                    torqueTop.setPower(0);
                    torqueBottom.setPower(0);
                }
                liftTop.setPosition(0);
                liftBottom.setPosition(1);
            } else if (swivel == false) {
                liftTop.setPosition(1);
                liftBottom.setPosition(0);
                torqueTop.setPower(0.4);
                torqueBottom.setPower(0.4);
                if(System.currentTimeMillis() - setTime > 100)
                {
                    hasRun = true;
                    torqueTop.setPower(0);
                    torqueBottom.setPower(0);
                }
            }
            swivel = !swivel;
        }


    }



        /*if(gp.b)
        {
            servoBackLeft.setPosition(0.5);
            servoBackRight.setPosition(0.5);
        }/*
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

        public void resetEncoders()
        {

            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        public void loopTime(int ms)
        {
            if(System.currentTimeMillis() - setTime > ms && !hasRun)
            {
                hasRun = true;
            }
        }




}