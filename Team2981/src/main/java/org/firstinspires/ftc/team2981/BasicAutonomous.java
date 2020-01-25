package org.firstinspires.ftc.team2981;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Blue Side", group = "Blue Side")
public class BasicAutonomous extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor leftLift = null;
    private DcMotor rightLift = null;
    private Servo leftFoundation = null;
    private Servo rightFoundation = null;
    private DcMotor rightIntake = null;

    //private Servo leftDropper = null;
    //corrector ratio 60/49
    // private Servo rightDropper = null;


    public void forward(double Lspeed, double Rspeed,  double Inches, double timeoutS, double rampup, int wait) throws InterruptedException {
        double     COUNTS_PER_MOTOR_REV    = 383.6 ;    //Set for NevRest 20 drive. For 40's change to 1120. For 60's 1680
        double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is the ratio between the motor axle and the wheel
        double     WHEEL_DIAMETER_INCHES   = 3.93700787 ; // 100mm to Inches
        double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

        //initialise some variables for the subroutine

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Determine new target position, and pass to motor controller we only do this in case the encoders are not totally zero'd

        newLeftTarget = ((this.frontLeft.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH));
        newRightTarget = (this.frontRight.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH);

        // reset the timeout time and start motion.

        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target

        while ( (runtime.seconds() < timeoutS) &&
                this.frontLeft.getCurrentPosition() < newLeftTarget && opModeIsActive() ) {

            double rem = (Math.abs(this.frontLeft.getCurrentPosition() + this.backLeft.getCurrentPosition())+Math.abs(this.frontRight.getCurrentPosition() + this.backRight.getCurrentPosition()))/4;

            double NLspeed;
            double NRspeed;

            //To Avoid spinning the wheels, this will "Slowly" ramp the motors up over

            //the amount of time you set for this SubRun

            double R = runtime.seconds();

            if (R < rampup) {

                double ramp = R / rampup;
                NLspeed = Lspeed * ramp;
                NRspeed = Rspeed * ramp;

            }

//Keep running until you are about two rotations out

            else if(rem > (1000) )
            {
                NLspeed = Lspeed;
                NRspeed = Rspeed;
            }

            //start slowing down as you get close to the target

            else if(rem > (200) && (Lspeed*.2) > .1 && (Rspeed*.2) > .1) {
                NLspeed = Lspeed * (rem / 1000);
                NRspeed = Rspeed * (rem / 1000);

            }

            //minimum speed

            else {
                NLspeed = Lspeed * .2;
                NRspeed = Rspeed * .2;


            }

            //Pass the seed values to the motors
            //telemetry.addData("backLeftPosition", backLeft.getCurrentPosition());
            telemetry.addData("frontLeftPosition", frontLeft.getCurrentPosition());
            //telemetry.addData("backRightPosition", backRight.getCurrentPosition());
            telemetry.addData("frontRightPosition", frontRight.getCurrentPosition());
            telemetry.update();

            frontLeft.setPower(NLspeed);
            frontRight.setPower(NRspeed);
            backLeft.setPower(NLspeed);
            backRight.setPower(NRspeed);

        }

        // Stop all motion;

        //Note: This is outside our while statement, this will only activate once the time, or distance has been met

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        // show the driver how close they got to the last target

        //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
        //telemetry.addData("Path2",  "Running at %7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());

        //telemetry.update();

        //setting resetC as a way to check the current encoder values easily

        double resetC = ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));

        //Get the motor encoder resets in motion

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //keep waiting while the reset is running

        /*while (Math.abs(resetC) > 0){
            resetC =  ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));
            idle();
        } */

        // switch the motors back to RUN_USING_ENCODER mode

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(wait);   // optional pause after each move

    }

    public void backward(double Lspeed, double Rspeed,  double Inches, double timeoutS, double rampup, int wait) throws InterruptedException {
        double     COUNTS_PER_MOTOR_REV    = 383.6 ;    //Set for NevRest 20 drive. For 40's change to 1120. For 60's 1680
        double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is the ratio between the motor axle and the wheel
        double     WHEEL_DIAMETER_INCHES   = 3.93700787 ; // 100mm to Inches
        double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

        //initialise some variables for the subroutine

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Determine new target position, and pass to motor controller we only do this in case the encoders are not totally zero'd

        newLeftTarget = -((this.frontLeft.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH));
        newRightTarget = (this.frontRight.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH);

        // reset the timeout time and start motion.

        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target

        while ( (runtime.seconds() < timeoutS) &&
                this.frontLeft.getCurrentPosition() > newLeftTarget && opModeIsActive() ) {

            double rem = (Math.abs(this.frontLeft.getCurrentPosition() + this.backLeft.getCurrentPosition())+Math.abs(this.frontRight.getCurrentPosition() + this.backRight.getCurrentPosition()))/4;

            double NLspeed;
            double NRspeed;

            //To Avoid spinning the wheels, this will "Slowly" ramp the motors up over

            //the amount of time you set for this SubRun

            double R = runtime.seconds();

            if (R < rampup) {

                double ramp = R / rampup;
                NLspeed = Lspeed * ramp;
                NRspeed = Rspeed * ramp;

            }

//Keep running until you are about two rotations out

            else if(rem > (1000) )
            {
                NLspeed = Lspeed;
                NRspeed = Rspeed;
            }

            //start slowing down as you get close to the target

            else if(rem > (200) && (Lspeed*.2) > .1 && (Rspeed*.2) > .1) {
                NLspeed = Lspeed * (rem / 1000);
                NRspeed = Rspeed * (rem / 1000);

            }

            //minimum speed

            else {
                NLspeed = Lspeed * .2;
                NRspeed = Rspeed * .2;


            }

            //Pass the seed values to the motors
            //telemetry.addData("backLeftPosition", backLeft.getCurrentPosition());
            telemetry.addData("frontLeftPosition", frontLeft.getCurrentPosition());
            //telemetry.addData("backRightPosition", backRight.getCurrentPosition());
            telemetry.addData("frontRightPosition", frontRight.getCurrentPosition());
            telemetry.update();

            frontLeft.setPower(NLspeed);
            frontRight.setPower(NRspeed);
            backLeft.setPower(NLspeed);
            backRight.setPower(NRspeed);

        }

        // Stop all motion;

        //Note: This is outside our while statement, this will only activate once the time, or distance has been met

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        // show the driver how close they got to the last target

        //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
        //telemetry.addData("Path2",  "Running at %7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());

        //telemetry.update();

        //setting resetC as a way to check the current encoder values easily

        double resetC = ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));

        //Get the motor encoder resets in motion

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //keep waiting while the reset is running

        /*while (Math.abs(resetC) > 0){
            resetC =  ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));
            idle();
        } */

        // switch the motors back to RUN_USING_ENCODER mode

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(wait);   // optional pause after each move

    }

    public void strafeLeft(double Lspeed, double Rspeed,  double Inches, double timeoutS, double rampup, int wait) throws InterruptedException {
        double     COUNTS_PER_MOTOR_REV    = 383.6 ;    //Set for NevRest 20 drive. For 40's change to 1120. For 60's 1680
        double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is the ratio between the motor axle and the wheel
        double     WHEEL_DIAMETER_INCHES   = 3.93700787 ; // 100mm to Inches
        double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

        //initialise some variables for the subroutine

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Determine new target position, and pass to motor controller we only do this in case the encoders are not totally zero'd

        newLeftTarget = -((this.frontLeft.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH));
        newRightTarget = (this.frontRight.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH);

        // reset the timeout time and start motion.

        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target

        while ( (runtime.seconds() < timeoutS) &&
                this.frontLeft.getCurrentPosition() > newLeftTarget && opModeIsActive() ) {

            double rem = (Math.abs(this.frontLeft.getCurrentPosition() + this.backLeft.getCurrentPosition())+Math.abs(this.frontRight.getCurrentPosition() + this.backRight.getCurrentPosition()))/4;

            double NLspeed;
            double NRspeed;

            //To Avoid spinning the wheels, this will "Slowly" ramp the motors up over

            //the amount of time you set for this SubRun

            double R = runtime.seconds();

            if (R < rampup) {

                double ramp = R / rampup;
                NLspeed = Lspeed * ramp;
                NRspeed = Rspeed * ramp;

            }

//Keep running until you are about two rotations out

            else if(rem > (1000) )
            {
                NLspeed = Lspeed;
                NRspeed = Rspeed;
            }

            //start slowing down as you get close to the target

            else if(rem > (200) && (Lspeed*.2) > .1 && (Rspeed*.2) > .1) {
                NLspeed = Lspeed * (rem / 1000);
                NRspeed = Rspeed * (rem / 1000);

            }

            //minimum speed

            else {
                NLspeed = Lspeed * .2;
                NRspeed = Rspeed * .2;


            }

            //Pass the seed values to the motors
            //telemetry.addData("backLeftPosition", backLeft.getCurrentPosition());
            telemetry.addData("frontLeftPosition", frontLeft.getCurrentPosition());
            //telemetry.addData("backRightPosition", backRight.getCurrentPosition());
            telemetry.addData("frontRightPosition", frontRight.getCurrentPosition());
            telemetry.update();

            frontLeft.setPower(-NLspeed);
            frontRight.setPower(NLspeed);
            backLeft.setPower(NLspeed);
            backRight.setPower(-NLspeed);

        }

        // Stop all motion;

        //Note: This is outside our while statement, this will only activate once the time, or distance has been met

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        // show the driver how close they got to the last target

        //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
        //telemetry.addData("Path2",  "Running at %7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());

        //telemetry.update();

        //setting resetC as a way to check the current encoder values easily

        double resetC = ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));

        //Get the motor encoder resets in motion

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //keep waiting while the reset is running

        /*while (Math.abs(resetC) > 0){
            resetC =  ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));
            idle();
        } */

        // switch the motors back to RUN_USING_ENCODER mode

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(wait);   // optional pause after each move

    }

    public void strafeRight2(double Lspeed, double Rspeed,  double Inches, double timeoutS, double rampup, int wait) throws InterruptedException {
        double     COUNTS_PER_MOTOR_REV    = 383.6 ;    //Set for NevRest 20 drive. For 40's change to 1120. For 60's 1680
        double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is the ratio between the motor axle and the wheel
        double     WHEEL_DIAMETER_INCHES   = 3.93700787 ; // 100mm to Inches
        double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

        //initialise some variables for the subroutine

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Determine new target position, and pass to motor controller we only do this in case the encoders are not totally zero'd

        newLeftTarget = -((this.frontLeft.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH));
        newRightTarget = (this.frontRight.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH);

        // reset the timeout time and start motion.

        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target

        while ( (runtime.seconds() < timeoutS) &&
                this.frontLeft.getCurrentPosition() < newLeftTarget && opModeIsActive() ) {

            double rem = (Math.abs(this.frontLeft.getCurrentPosition() + this.backLeft.getCurrentPosition())+Math.abs(this.frontRight.getCurrentPosition() + this.backRight.getCurrentPosition()))/4;

            double NLspeed;
            double NRspeed;

            //To Avoid spinning the wheels, this will "Slowly" ramp the motors up over

            //the amount of time you set for this SubRun

            double R = runtime.seconds();

            if (R < rampup) {

                double ramp = R / rampup;
                NLspeed = Lspeed * ramp;
                NRspeed = Rspeed * ramp;

            }

//Keep running until you are about two rotations out

            else if(rem > (1000) )
            {
                NLspeed = Lspeed;
                NRspeed = Rspeed;
            }

            //start slowing down as you get close to the target

            else if(rem > (200) && (Lspeed*.2) > .1 && (Rspeed*.2) > .1) {
                NLspeed = Lspeed * (rem / 1000);
                NRspeed = Rspeed * (rem / 1000);

            }

            //minimum speed

            else {
                NLspeed = Lspeed * .2;
                NRspeed = Rspeed * .2;


            }

            //Pass the seed values to the motors
            //telemetry.addData("backLeftPosition", backLeft.getCurrentPosition());
            telemetry.addData("frontLeftPosition", frontLeft.getCurrentPosition());
            //telemetry.addData("backRightPosition", backRight.getCurrentPosition());
            telemetry.addData("frontRightPosition", frontRight.getCurrentPosition());
            telemetry.update();

            frontLeft.setPower(NLspeed);
            frontRight.setPower(-NLspeed);
            backLeft.setPower(-NLspeed);
            backRight.setPower(NLspeed);

        }

        // Stop all motion;

        //Note: This is outside our while statement, this will only activate once the time, or distance has been met

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        // show the driver how close they got to the last target

        //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
        //telemetry.addData("Path2",  "Running at %7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());

        //telemetry.update();

        //setting resetC as a way to check the current encoder values easily

        double resetC = ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));

        //Get the motor encoder resets in motion

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //keep waiting while the reset is running

        /*while (Math.abs(resetC) > 0){
            resetC =  ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));
            idle();
        } */

        // switch the motors back to RUN_USING_ENCODER mode

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(wait);   // optional pause after each move


    }
    public void strafeRight(double Lspeed, double Rspeed,  double Inches, double timeoutS, double rampup, int wait) throws InterruptedException {
        double     COUNTS_PER_MOTOR_REV    = 383.6 ;    //Set for NevRest 20 drive. For 40's change to 1120. For 60's 1680
        double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is the ratio between the motor axle and the wheel
        double     WHEEL_DIAMETER_INCHES   = 3.93700787 ; // 100mm to Inches
        double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

        //initialise some variables for the subroutine

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Determine new target position, and pass to motor controller we only do this in case the encoders are not totally zero'd

        newLeftTarget = ((this.frontLeft.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH));
        newRightTarget = (this.frontRight.getCurrentPosition()) + (int)(Inches * COUNTS_PER_INCH);

        // reset the timeout time and start motion.

        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target

        while ( (runtime.seconds() < timeoutS) &&
                this.frontLeft.getCurrentPosition() < newLeftTarget && opModeIsActive() ) {

            double rem = (Math.abs(this.frontLeft.getCurrentPosition() + this.backLeft.getCurrentPosition())+Math.abs(this.frontRight.getCurrentPosition() + this.backRight.getCurrentPosition()))/4;

            double NLspeed;
            double NRspeed;

            //To Avoid spinning the wheels, this will "Slowly" ramp the motors up over

            //the amount of time you set for this SubRun

            double R = runtime.seconds();

            if (R < rampup) {

                double ramp = R / rampup;
                NLspeed = Lspeed * ramp;
                NRspeed = Rspeed * ramp;

            }

//Keep running until you are about two rotations out

            else if(rem > (1000) )
            {
                NLspeed = Lspeed;
                NRspeed = Rspeed;
            }

            //start slowing down as you get close to the target

            else if(rem > (200) && (Lspeed*.2) > .1 && (Rspeed*.2) > .1) {
                NLspeed = Lspeed * (rem / 1000);
                NRspeed = Rspeed * (rem / 1000);

            }

            //minimum speed

            else {
                NLspeed = Lspeed * .2;
                NRspeed = Rspeed * .2;


            }

            //Pass the seed values to the motors
            telemetry.addData("backLeftPosition", backLeft.getCurrentPosition());
            telemetry.addData("frontLeftPosition", frontLeft.getCurrentPosition());
            telemetry.addData("backRightPosition", backRight.getCurrentPosition());
            telemetry.addData("frontRightPosition", frontRight.getCurrentPosition());
            telemetry.update();

            frontLeft.setPower(NLspeed);
            frontRight.setPower(-NLspeed);
            backLeft.setPower(-NLspeed);
            backRight.setPower(NLspeed);

        }

        // Stop all motion;

        //Note: This is outside our while statement, this will only activate once the time, or distance has been met

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        // show the driver how close they got to the last target

        //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
        //telemetry.addData("Path2",  "Running at %7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());

        //telemetry.update();

        //setting resetC as a way to check the current encoder values easily

        double resetC = ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));

        //Get the motor encoder resets in motion

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //keep waiting while the reset is running

        /* while (Math.abs(resetC) > 0){
            resetC =  ((Math.abs(frontLeft.getCurrentPosition()) + Math.abs(backLeft.getCurrentPosition())+ Math.abs(frontRight.getCurrentPosition())+Math.abs(backRight.getCurrentPosition())));
            idle();
        } */

        // switch the motors back to RUN_USING_ENCODER mode

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(wait);   // optional pause after each move

    }

    public void grip(double leftAngle, double rightAngle, int timer){
        leftFoundation.setPosition(leftAngle);
        rightFoundation.setPosition(rightAngle);
        sleep(timer);

    }
    /*public void dropRollers() {

        rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightIntake.setPower(-.6);
        frontLeft.setPower(1);
        frontRight.setPower(1);
        backLeft.setPower(1);
        backRight.setPower(1);
        sleep(100);
        frontLeft.setPower(-1);
        frontRight.setPower(-1);
        backLeft.setPower(-1);
        backRight.setPower(-1);
        sleep(100);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        rightIntake.setPower(0);

        //encoderDrive(1.0,1.0,12.0,1.0,20.0,10);

    } */








    public void runOpMode() throws InterruptedException{
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        leftFoundation = hardwareMap.servo.get("leftFoundation");
        rightFoundation = hardwareMap.servo.get("rightFoundation");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        leftLift.setDirection(DcMotor.Direction.REVERSE);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //rightIntake = hardwareMap.get(DcMotor.class, "rightIntake");
        //rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();


        //strafeRight(.6, .6, 25, 18, 2, 750);
        backward(-.6,-.6,25,20,1,1000);
        leftLift.setPower(-.5);
        rightLift.setPower(-.5);
        sleep(400);
        leftLift.setPower(0);
        rightLift.setPower(0);
        backward(-.6,-.6,27,20,1,1000);
        strafeLeft(.6,.6,10, 18, 2, 2000);
        leftLift.setPower(.5);
        rightLift.setPower(.5);
        sleep(300);
        leftLift.setPower(0);
        rightLift.setPower(0);
        strafeLeft(.6,.6,20, 18, 2, 2000);
        //backward(-.6,-.6,38,20,1,1000);
        //forward(.6,.6,48,20,1,1000);
        //grip(0,0,1500); //Down?
        //grip(0,0,1000); //Down?
        //forward(.8,.8,45,20,1,1000);
        //grip(180,0,1000); //Up

        //strafeLeft(.6,.6,30, 18, 2, 750);
        //backward(-.6,-.6,26.5,20,1,1000);
        //dropRollers();
        //strafeLeft(.6,.6,25, 20, 2, 500);
        //strafeLeft(.75, .75, 20, 20, 2, 750);
        //strafeRight(.75,.75,20, 20, 2, 500);
        //backward(-1,-1,10,20,2,250);
        //grip(0,0,0);// Down
        //grip(180,0,0); //Up
        //dropRollers();



    }

}


