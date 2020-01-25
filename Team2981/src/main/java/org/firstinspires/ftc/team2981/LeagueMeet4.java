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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="LeagueMeet4", group="Iterative Opmode")


public class LeagueMeet4 extends OpMode
{
    private DcMotor backLeft = null ;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor leftLift = null;
    private DcMotor rightLift = null;
    private DcMotor intakeClaw = null;
    private Servo leftFoundation = null;
    private Servo rightFoundation = null;
    private Servo backServo = null;

    private double deadzoneX = 0;
    private double deadzoneY = 0;
    private double deadzoneRotate = 0;
    private int level = 0;

    private boolean slowMode = false;


    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        //backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        //frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        leftLift.setDirection(DcMotor.Direction.REVERSE);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFoundation = hardwareMap.servo.get("leftFoundation");
        rightFoundation = hardwareMap.servo.get("rightFoundation");
        backServo = hardwareMap.servo.get("backServo");
        intakeClaw =  hardwareMap.get(DcMotor.class, "intakeClaw");

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

        //Implementation of a Controller Deadzone to prevent accidental movement due to uncentered joystick.
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

        //Mecanum wheel drive calculations
        double r = Math.hypot(deadzoneX, -deadzoneY); //deadzones are incorporated into these values
        double robotAngle = Math.atan2(-deadzoneY, deadzoneX) - Math.PI / 4;
        double rightX = deadzoneRotate/1.25;
        final double v1 = r * Math.cos(robotAngle) + rightX;
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        //Power set to each motor based on these calculations
        frontLeft.setPower(-v1);
        frontRight.setPower(-v2);
        backLeft.setPower(-v3);
        backRight.setPower(-v4);
//----
        if(gamepad1.a){ //Allows for control of Servos during Teleop to move the foundation during endgame.
            leftFoundation.setPosition(0);
            rightFoundation.setPosition(0);
        }
        if(gamepad1.b){
            leftFoundation.setPosition(1);
            rightFoundation.setPosition(1);
        }

//----
        if(gamepad1.dpad_up){ //Allows for movement of Servo on the back of the bot that carries blocks if pushbotting is needed.
            backServo.setPosition(.50);
        }
        if(gamepad1.dpad_down){
            backServo.setPosition(0);
        }
 //----

         if(gamepad1.dpad_left){ //Sets negative power to motor to open claw.
             intakeClaw.setPower(-1);
         }
         else if(gamepad1.dpad_right){ //Turns of motor so that rubber bands return claw to default position.
             intakeClaw.setPower(0);
         }
 //----

//Lift Code
        if(gamepad1.left_trigger > 0){ // Lowers the lift on the bot.
            leftLift.setPower(.4);
            rightLift.setPower(.4);
        }
        else if(gamepad1.right_trigger > 0){ //Raises the lift on the bot
            leftLift.setPower(-.4);
            rightLift.setPower(-.4);
        }
        else{ //If nothing is pressed, set the motors to power 0 in order to have them brake.
            leftLift.setPower(0);
            rightLift.setPower(0);
        }
//----
        //Telemetry used for testing encoder values.
        telemetry.addData("frontLeftPosition", frontLeft.getCurrentPosition());
        telemetry.addData("backLeftPosition", backLeft.getCurrentPosition());
        telemetry.addData("frontRightPosition", frontRight.getCurrentPosition());
        telemetry.addData("backRightPosition", backRight.getCurrentPosition());
    }

    @Override
    public void stop() {
    }

}
