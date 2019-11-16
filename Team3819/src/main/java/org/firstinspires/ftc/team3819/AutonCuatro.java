package org.firstinspires.ftc.team3819;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonCuatro")
public class AutonCuatro extends LinearOpMode {

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

        waitForStart();
        //add paths and stuff now
        driveInches(.5, 3);
        waitCustom(5000);
        //turn2(.5, 90);
        //waitCustom(1000);
        driveInches(.5, -3);
        waitCustom(1000);
        //turn2(.5, -90);

    }

    public void driveInches(double pow, int in) {
        robot.resetEncoders();
        int target = -1*(int)(in*robot.CPI);
        int dir = in >= 0 ? 1 : -1;
        robot.frontLeft.setPower(pow*dir);
        robot.frontRight.setPower(pow*dir);
        robot.backLeft.setPower(pow*dir);
        robot.backRight.setPower(pow*dir);
        while( (robot.frontLeft.getCurrentPosition()>target + 50||robot.frontLeft.getCurrentPosition()<target-50) &&
                ((robot.frontRight.getCurrentPosition()>target + 50||robot.frontRight.getCurrentPosition()<target-50)) &&
                (robot.backLeft.getCurrentPosition()>target + 50||robot.backLeft.getCurrentPosition()<target-50) &&
                (robot.backRight.getCurrentPosition()>target + 50||robot.backRight.getCurrentPosition()<target-50) &&
                opModeIsActive()) {
            telemetry.addLine("frontLeft: " + robot.frontLeft.getCurrentPosition());
            telemetry.addLine("frontRight: " + robot.frontRight.getCurrentPosition());
            telemetry.addLine("backLeft: " + robot.backLeft.getCurrentPosition());
            telemetry.addLine("backRight: " + robot.backRight.getCurrentPosition());
            telemetry.update();
        }
        robot.stop();
    }

    public void waitCustom(int ms) {
        time.reset();
        while(time.milliseconds()<ms && opModeIsActive() && !isStopRequested())
        {

        }
    }

}