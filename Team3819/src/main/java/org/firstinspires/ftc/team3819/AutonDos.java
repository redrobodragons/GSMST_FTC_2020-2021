package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonDos")
public class AutonDos extends LinearOpMode {

    private Hardware robot = null;

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
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.frontLeft.getCurrentPosition(),
                robot.frontRight.getCurrentPosition(),
                robot.backLeft.getCurrentPosition(),
                robot.backRight.getCurrentPosition());
        telemetry.update();

        waitForStart();
        //add paths and stuff now
        inches(.5, 1);
        waitCustom(5000);
        //turn2(.5, 90);
        //waitCustom(1000);
        inches(.5, 1);
        waitCustom(1000);
        //turn2(.5, -90);

    }

    public void inches(double pow, int in)
    {
        int newLeftTarget;
        int newRightTarget;

        if(opModeIsActive() && !isStopRequested())
        {
            //newLeftTarget = robot.frontLeft.getCurrentPosition() + (int)(in * robot.CPI);
            //newRightTarget = robot.frontRight.getCurrentPosition() + (int)(in * robot.CPI);
            newLeftTarget = (int)(in * robot.CPI);
            newRightTarget = (int)(in * robot.CPI);


            robot.frontLeft.setTargetPosition(newLeftTarget);
            robot.frontRight.setTargetPosition(newRightTarget);
            robot.backLeft.setTargetPosition(newLeftTarget);
            robot.backRight.setTargetPosition(newRightTarget);

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

            while (opModeIsActive() && /*(time.seconds() < timeout) &&*/
                    (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) && !isStopRequested()) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running at %7d :%7d" ,newLeftTarget,  newRightTarget);
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

    /*public void driveInches(double pow, int in) {
        robot.resetEncoders();
        int target = -1*(int)(in*robot.CPI);
        int dir = in >= 0 ? 1 : -1;
        robot.left.setPower(pow*dir);
        robot.right.setPower(pow*dir);
        while( (robot.left.getCurrentPosition()>target + 50||robot.left.getCurrentPosition()<target-50) &&
                (robot.right.getCurrentPosition()>target + 50||robot.right.getCurrentPosition()<target-50) &&
                opModeIsActive()) {
            telemetry.addLine("Left: " + robot.left.getCurrentPosition());
            telemetry.addLine("Right: " + robot.right.getCurrentPosition());
            telemetry.update();
        }
        robot.stop();
    }

    /*public void turn(double degrees) {
        robot.resetEncoders();
        int powL = degrees >= 0 ? 1 : -1;
        int powR = powL * -1;

        int targetL = ((int) ((degrees / 360) * robot.CIRCUMFRENCE * robot.CPI)); //left gets a negative
        int targetR = targetL * -1;

        robot.left.setPower(powL*.25);
        robot.right.setPower(powR*.25);

        while ((robot.left.getCurrentPosition() > targetL + 10 || robot.left.getCurrentPosition() < targetL - 10) &&
                (robot.right.getCurrentPosition() > targetR + 10 || robot.right.getCurrentPosition() < targetR - 10) &&
                opModeIsActive()) {
            telemetry.addLine("Left: " + robot.left.getCurrentPosition());
            telemetry.addLine("Right: " + robot.right.getCurrentPosition());
            telemetry.update();
        }
        robot.stop();
    }*/

    public void driveInches(double pow, int in)
    {
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int target = (int)(in*robot.CPI);

        robot.frontLeft.setTargetPosition(target);
        robot.frontRight.setTargetPosition(target);
        robot.backLeft.setTargetPosition(target);
        robot.backRight.setTargetPosition(target);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        robot.frontLeft.setPower(pow);
        robot.frontRight.setPower(pow);
        robot.backLeft.setPower(pow);
        robot.backRight.setPower(pow);

        while(robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy())
        {
            //waiting until done running
        }

        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void turn(double pow, double degrees)
    {
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeft.setTargetPosition(-1*(int)((360.0/degrees)*robot.CIRCUMFRENCE*robot.CPI));
        robot.frontRight.setTargetPosition((int)((360.0/degrees)*robot.CIRCUMFRENCE*robot.CPI));
        robot.backLeft.setTargetPosition(-1*(int)((360.0/degrees)*robot.CIRCUMFRENCE*robot.CPI));
        robot.backRight.setTargetPosition((int)((360.0/degrees)*robot.CIRCUMFRENCE*robot.CPI));
    }


    public void waitCustom(int ms) {
        time.reset();
        while(time.milliseconds()<ms && opModeIsActive() && !isStopRequested())
        {

        }
    }

}