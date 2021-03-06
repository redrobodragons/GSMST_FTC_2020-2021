package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="blue")
public class Auton extends LinearOpMode {

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

        robot.moveDrive(.5, 0, 0);//move forward a bit so you are oof the wall, move lift up
        robot.setLift(.3);
        waitCustom(400);

        robot.moveDrive(0, 0, 0);//stop drive
        robot.setLift(0);
        waitCustom(500);

        time.reset();//move laterally and sense how far from the wall you are to line up with platform
        robot.moveDrive(0, 0, -.35);
        while(time.milliseconds() < 2000 && robot.leftDist.getDistance(DistanceUnit.CM) > 40) {
            waitCustom(10);
        }

        robot.moveDrive(.7, 0, 0);//drive forward into the platform
        robot.setLift(0);
        waitCustom(800);

        robot.moveDrive(-.17, 0, 0);//drive forward into the platform
        robot.setLift(0);
        waitCustom(200);

        robot.moveDrive(0, 0, 0);// drop lift down, clamp onto the platform
        robot.setLift(0);
        waitCustom(1000);

        robot.moveDrive(0, 0, 0);// drop lift down, clamp onto the platform
        robot.setLift(-.5);
        waitCustom(1300);


        robot.moveDrive(-.7, 0, 0);//decrease lift power, pull platform back
        robot.setLift(-.06);
        waitCustom(2900);

        robot.moveDrive(0, 0, 0);//stop drive, lift off the platform
        robot.setLift(.45);
        waitCustom(700);

        robot.moveDrive(0, 0, 0);//stop drive, lift off the platform
        robot.setLift(0);
        waitCustom(700);

//        robot.moveDrive(.16, 0, 0);
//        robot.setLift(0);
//        waitCustom(700);
//
//        robot.moveDrive(-.16, 0, 0);
//        robot.setLift(.15);
//        waitCustom(600);

        robot.moveDrive(0, 0, 1);//move laterally and get under the fence
        robot.setLift(0);
        waitCustom(3000);

        robot.moveDrive(.7, 0, 0);//move laterally and get under the fence
        robot.setLift(0);
        waitCustom(800);


        robot.moveDrive(0, -.3, 0);//move laterally and get under the fence
        robot.setLift(0);
        waitCustom(400);

        robot.moveDrive(0, 0, -1);//move laterally and get under the fence
        robot.setLift(0);
        waitCustom(2000);

        robot.moveDrive(-.6, 0, 0);//move laterally and get under the fence
        robot.setLift(-.30);
        waitCustom(800);

        robot.moveDrive(0, 0, 1);//move laterally and get under the fence
        robot.setLift(0);
        waitCustom(1000);

    }



    public void waitCustom(int ms) {
        time.reset();
        while(time.milliseconds()<ms)
        {

        }
    }

}
