package org.firstinspires.ftc.team2993;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends OpMode {
    Hardware robot;
    byte[] oldArr;
    {
        try {
            oldArr = gamepad1.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    byte[] newArr;
    byte[] original;
    @Override
    public void init() {
        robot = new Hardware(hardwareMap);
    }
    private void driveRobot(){
        robot.driveMechanum(gamepad1);
//        telemetry.addLine("Y stick 1 " + gamepad1.left_stick_y);
//        telemetry.addLine("X stick 2 " + gamepad1.right_stick_x);
//        telemetry.addLine("Left Trigger " + gamepad1.left_trigger);
        telemetry.addLine("left: " + robot.leftDist.getDistance(DistanceUnit.CM));
        telemetry.addLine("right: " + robot.rightDist.getDistance(DistanceUnit.CM));
        telemetry.addLine("back: " + robot.backDist.getDistance(DistanceUnit.CM));
        telemetry.update();
    }

    public void loop(){
        driveRobot();
    }

    void byteControllerTest(){
        if(oldArr == null){
            try {
                original = gamepad1.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            newArr = gamepad1.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < newArr.length; i++) {
                if (oldArr[i] != newArr[i] || newArr[i] != original[i]) {
                    telemetry.addLine("" + i + " : " + String.valueOf(newArr[i]) + "  d/dx: " + (newArr[i]-oldArr[i]));
                }
            }
        }catch (Exception e){
            telemetry.addLine("exc");
        }

        oldArr = newArr;
    }
}