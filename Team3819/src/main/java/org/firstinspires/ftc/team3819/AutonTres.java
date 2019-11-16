package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonTres")
@Disabled
public class AutonTres extends LinearOpMode {

    private Hardware robot = null;

    private ElapsedTime runtime = new ElapsedTime();

    private ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public void initialize() {
        robot = new Hardware(hardwareMap);

        //color = (ModernRoboticsI2cColorSensor) hardwareMap.get(ColorSensor.class, "color");
    }


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();


        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        /*telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.frontLeft.getCurrentPosition(),
                robot.frontRight.getCurrentPosition(),
                robot.backLeft.getCurrentPosition(),
                robot.backRight.getCurrentPosition());
        telemetry.update();*/

        waitForStart();
        //add paths and stuff now
        encoderDrive(.5,.5, 2, 5, 2);
        waitCustom(5000);
        //turn2(.5, 90);
        //waitCustom(1000);
        encoderDrive(.5,.5, 2, 5, 2);
        waitCustom(1000);
        //turn2(.5, -90);

    }

    public void encoderDrive(double Lspeed, double Rspeed,  double Inches, double timeoutS, double rampup) throws InterruptedException {
        double     COUNTS_PER_MOTOR_REV    = robot.CPR ;    //Set for NevRest 20 drive. For 40's change to 1120. For 60's 1680
        double     DRIVE_GEAR_REDUCTION    = robot.GEARING ;     // This is the ratio between the motor axle and the wheel
        double     WHEEL_DIAMETER_INCHES   = robot.DIAMETER ; // 100mm to Inches
        double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

        //initialise some variables for the subroutine

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active

        // Determine new target position, and pass to motor controller we only do this in case the encoders are not totally zero'd

        newLeftTarget = (this.robot.backLeft.getCurrentPosition() + this.robot.backLeft.getCurrentPosition() )/2 + (int)(Inches * COUNTS_PER_INCH);
        newRightTarget = (this.robot.backRight.getCurrentPosition() + this.robot.backRight.getCurrentPosition() )/2 + (int)(Inches * COUNTS_PER_INCH);

        // reset the timeout time and start motion.

        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target

        while ( /*(runtime.seconds() < timeoutS) &&*/
                (Math.abs(this.robot.frontLeft.getCurrentPosition() + this.robot.backLeft.getCurrentPosition()) /2 < newLeftTarget  &&
                        Math.abs(this.robot.frontRight.getCurrentPosition() + this.robot.backRight.getCurrentPosition())/2 < newRightTarget) && !isStopRequested()) {

            telemetry.update();
            telemetry.addLine("targetPos: "+newLeftTarget);
            telemetry.addLine("frontLeft: " + robot.frontLeft.getCurrentPosition());
            telemetry.addLine("frontRight: " + robot.frontRight.getCurrentPosition());
            telemetry.addLine("backLeft: " + robot.backLeft.getCurrentPosition());
            telemetry.addLine("backRight: " + robot.backRight.getCurrentPosition());
            //telemetry.update();

            double rem = (Math.abs(this.robot.frontLeft.getCurrentPosition() + this.robot.backLeft.getCurrentPosition())+Math.abs(this.robot.frontRight.getCurrentPosition() + this.robot.backRight.getCurrentPosition()))/4;

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

            /*robot.frontLeft.setPower(NLspeed);
            robot.frontRight.setPower(NRspeed);
            robot.backLeft.setPower(NLspeed);
            robot.backRight.setPower(NRspeed);*/

        }

        // Stop all motion;

        //Note: This is outside our while statement, this will only activate once the time, or distance has been met

        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
        // show the driver how close they got to the last target

        /*telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
        telemetry.addData("Path2",  "Running at %7d :%7d", robot.frontLeft.getCurrentPosition(), robot.frontRight.getCurrentPosition());

        telemetry.update();*/

        //setting resetC as a way to check the current encoder values easily

        double resetC = ((Math.abs(robot.frontLeft.getCurrentPosition()) + Math.abs(robot.backLeft.getCurrentPosition())+ Math.abs(robot.frontRight.getCurrentPosition())+Math.abs(robot.backRight.getCurrentPosition())));

        //Get the motor encoder resets in motion

        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //keep waiting while the reset is running

        while (Math.abs(resetC) > 0 && !isStopRequested()){
            resetC =  ((Math.abs(robot.frontLeft.getCurrentPosition()) + Math.abs(robot.backLeft.getCurrentPosition())+ Math.abs(robot.frontRight.getCurrentPosition())+Math.abs(robot.backRight.getCurrentPosition())));
            idle();
        }

        // switch the motors back to RUN_USING_ENCODER mode

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(250);   // optional pause after each move

    }

    public void turn2(double pow, double degrees)
    {

        if(opModeIsActive())
        {

            robot.frontLeft.setTargetPosition(-1*(int)((360.0/degrees)*robot.CIRCUMFRENCE*robot.CPI));
            robot.frontRight.setTargetPosition((int)((360.0/degrees)*robot.CIRCUMFRENCE*robot.CPI));
            robot.backLeft.setTargetPosition(-1*(int)((360.0/degrees)*robot.CIRCUMFRENCE*robot.CPI));
            robot.backRight.setTargetPosition((int)((360.0/degrees)*robot.CIRCUMFRENCE*robot.CPI));

            // Turn On RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            time.reset();

            robot.frontLeft.setPower(Math.abs(pow));
            robot.frontRight.setPower(Math.abs(pow));
            robot.backLeft.setPower(Math.abs(pow));
            robot.backRight.setPower(Math.abs(pow));

            while (opModeIsActive() &&
                    (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.frontLeft.getCurrentPosition(),
                        robot.frontRight.getCurrentPosition(),
                        robot.backLeft.getCurrentPosition(),
                        robot.backRight.getCurrentPosition());
                telemetry.update();
            }

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(100);   // optional pause after each move
        }
    }

    public void waitCustom(int ms) {
        time.reset();
        while(time.milliseconds()<ms && opModeIsActive() && !isStopRequested())
        {

        }
    }

}