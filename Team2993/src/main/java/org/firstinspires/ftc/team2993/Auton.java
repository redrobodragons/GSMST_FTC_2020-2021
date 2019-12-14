package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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

        robot.setAllDrive(0.75);
        robot.setLift(1);
        waitCustom(1250);

        robot.setAllDrive(0);
        robot.setLift(.6);
        waitCustom(1000);

        robot.setLift(.05);
        robot.setAllDrive(0);
        waitCustom(500);

        robot.setLift(.05);
        robot.setAllDrive(-.75);
        waitCustom(1400);

        robot.turnDrive(1);
        waitCustom(1500);


        robot.setAllDrive(0);

        robot.setLift(-1);
        waitCustom(1500);

        robot.setLift(0);

        robot.setAllDrive(-1);
        waitCustom(800);
        robot.setAllDrive(0);
    }



    public void waitCustom(int ms) {
        time.reset();
        while(time.milliseconds()<ms)
        {

        }
    }

}
