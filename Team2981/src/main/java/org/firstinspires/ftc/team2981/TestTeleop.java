/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.team2981;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="TestServo", group="Iterative Opmode")
@Disabled

public class TestTeleop extends OpMode
{
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor leftIntake = null;
    private DcMotor rightIntake = null;
    private Servo leftDropper = null;
    private Servo rightDropper = null;
    private Servo foundLeft = null; // left foundation mover
    private Servo foundRight = null; // right foundation mover
    private Servo claw = null;
    private DcMotor leftLift = null;
    private DcMotor rightLift = null;

    private boolean aPressed = false;
    private boolean spinning = false;
    private double contPower = 0;
    private static final double CR_SERVO_STOP = 0.0;
    private static final double CR_SERVO_FORWARD = 1.0;
    private static final double CR_SERVO_REVERSE = -1.0;
    private double deadzoneX = 0.0;
    private double deadzoneY = 0.0;
    private double deadzoneRotate = 0.0;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        leftIntake = hardwareMap.get(DcMotor.class, "leftIntake");
        rightIntake = hardwareMap.get(DcMotor.class, "rightIntake");
        rightIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftDropper = hardwareMap.servo.get("leftDropper");
        rightDropper = hardwareMap.servo.get("rightDropper");
        foundLeft = hardwareMap.servo.get("foundLeft");
        foundRight = hardwareMap.servo.get("foundRight");
        claw = hardwareMap.servo.get("claw");

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");


    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {


        
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {



    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry

        if(Math.abs(gamepad1.left_stick_x) < 0.05){
            deadzoneX = 0;
        }
        else{
            deadzoneX = gamepad1.left_stick_x;
        }

        if(Math.abs(gamepad1.left_stick_y) < 0.05){
            deadzoneY = 0;
        }
        else{
            deadzoneY = gamepad1.left_stick_y;
        }

        if(Math.abs(gamepad1.right_stick_x) < 0.05){
            deadzoneRotate = 0;
        }
        else{
            deadzoneRotate = gamepad1.right_stick_x;
        }



        double r = Math.hypot(deadzoneX, -deadzoneY);
        double robotAngle = Math.atan2(-deadzoneY, deadzoneX) - Math.PI / 4;
        double rightX = deadzoneRotate/1.25;
        final double v1 = r * Math.cos(robotAngle) + rightX;
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;



        /*frontLeft.setPower(v1);
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);
*/
        if(gamepad1.a){
            leftIntake.setPower(-1);
            rightIntake.setPower(-1);
        }
        else if (gamepad1.b){
            leftIntake.setPower(1);
            rightIntake.setPower(1);
        }
        else{
            leftIntake.setPower(0);
            rightIntake.setPower(0);
        }





        if (gamepad1.dpad_left){
            foundLeft.setPosition(0);
            foundRight.setPosition(0);
        }
        else if(gamepad1.dpad_right){
            foundLeft.setPosition(.5);
            foundRight.setPosition(.5);
        }

       /* if(gamepad1.x){
            claw.setPosition(.555); // opens the claw
        }
        else if(gamepad1.y){
            claw.setPosition(0); // closes the claw
        }
*/
        /* WILL FIX LATER if(gamepad1.a && !aPressed){
            aPressed = true;
            if(!spinning){
                leftIntake.setPower(1);
                rightIntake.setPower(1);
            } else {
                leftIntake.setPower(0);
                rightIntake.setPower(0);
            }
            spinning = !spinning;
        } else if(!gamepad1.a && aPressed){
            aPressed = false;
        }
    */


        // Show the elapsed game time and wheel power.

        //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Servo pos Left", foundLeft.getPosition());
        telemetry.addData("Servo pos Right", foundRight.getPosition());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
