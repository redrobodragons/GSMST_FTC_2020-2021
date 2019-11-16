package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TimeAutoRed")
public class TimeAutoRed extends LinearOpMode {

    private Hardware robot = null;

    private ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public void initialize() {
        robot = new Hardware(hardwareMap);

        //color = (ModernRoboticsI2cColorSensor) hardwareMap.get(ColorSensor.class, "color");
    }


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        robot.drive(1);//going forward toward foundation
        waitCustom(1000);
        robot.drive(-1);//backward for intake
        waitCustom(600);
        robot.stop();
        waitCustom(2000);
        robot.drive(-1);//back toward wall
        waitCustom(500);
        robot.stop();
        torqueUp(0.5); //intake up
        waitCustom(200);
        torqueStop();
        waitCustom(1000);
        strafeLeft(1); //moving out of way of foundation
        waitCustom(500);
        robot.stop();
        waitCustom(1000);
        torqueDown(); //bringing it under 14
        waitCustom(100);
        torqueStop();
        strafeLeft(1); // going to park
        waitCustom(200);
        robot.stop();
    }

    private void torqueStop()
    {
        robot.torqueFrontRight.setPower(0);
        robot.torqueFrontLeft.setPower(0);
        robot.torqueBackRight.setPower(0);
    }

    private void torqueDown()
    {
        robot.torqueFrontRight.setPower(-0.6);
        robot.torqueFrontLeft.setPower(-0.6);
        robot.torqueBackRight.setPower(-1);
    }

    private void torqueUp(double power)
    {
        robot.torqueFrontRight.setPower(power);
        robot.torqueFrontLeft.setPower(power);
        robot.torqueBackRight.setPower(power);
    }
    private void strafeRight(double power)
    {
        robot.frontLeft.setPower(power);
        robot.backLeft.setPower(-power);
        robot.frontRight.setPower(-power);
        robot.backRight.setPower(power);
    }

    private void strafeLeft(double power)
    {
        robot.frontLeft.setPower(-power);
        robot.backLeft.setPower(power);
        robot.frontRight.setPower(power);
        robot.backRight.setPower(-power);
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

    public void turn(double degrees) {
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