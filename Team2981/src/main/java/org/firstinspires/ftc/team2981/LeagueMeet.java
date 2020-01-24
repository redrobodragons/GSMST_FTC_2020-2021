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

@TeleOp(name="LeagueMeetCurrent", group="Iterative Opmode")


public class LeagueMeet extends OpMode
{
    private DcMotor backLeft = null ;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    //private DcMotor leftIntake = null;
    //private DcMotor rightIntake = null;
    private DcMotor leftLift = null;
    private DcMotor rightLift = null;
    //private Servo midIntake = null;
    //private DcMotor midIntake = null;
    private DcMotor leftIntake = null;
    private DcMotor rightIntake = null;
    //private Servo leftDropper = null;
    //private Servo rightDropper = null;
    private Servo leftRollers = null;
    private Servo rightRollers = null;
    private Servo rightGrip = null;





    private double deadzoneX = 0;
    private double deadzoneY = 0;
    private double deadzoneRotate = 0;

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
        leftRollers = hardwareMap.servo.get("leftRollers");
        rightRollers = hardwareMap.servo.get("rightRollers");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        leftLift.setDirection(DcMotor.Direction.REVERSE);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake = hardwareMap.get(DcMotor.class, "rightIntake");
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftIntake = hardwareMap.get(DcMotor.class, "leftIntake");
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftIntake.setDirection(DcMotor.Direction.REVERSE);
        //leftDropper = hardwareMap.servo.get("leftDropper");
        //rightDropper = hardwareMap.servo.get("rightDropper");
        rightGrip = hardwareMap.servo.get("rightGrip");




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

        /*for(int b = 0; b<9; b++){ // Drops the rollers
            for (double i = 0; i <= 1; i = i + 0.05) {
                double j  = 1-i;
                leftDropper.setPosition(i);
                rightDropper.setPosition(j);
            }

        }*/




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


    if(!slowMode){frontLeft.setPower(v1);
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);

    }

    else if(slowMode){
        frontLeft.setPower(v1/2);
        frontRight.setPower(v2/2);
        backLeft.setPower(v3/2);
        backRight.setPower(v4/2);
    }

        if(gamepad2.dpad_left){
            rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightIntake.setPower(.3);
        }


        if(gamepad2.x){
                leftIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                telemetry.addLine("Encoders Reset");
        }

        if(gamepad1.b){
            leftRollers.setPosition(0);
            rightRollers.setPosition(1);
        }
        else if(gamepad1.a){
            leftRollers.setPosition(1);
            rightRollers.setPosition(0);
        }
        else{
            leftRollers.setPosition(0.5);
            rightRollers.setPosition(0.5);
        }


        if(gamepad1.dpad_left){

            //rightIntake.setTargetPosition(-5);
            //rightIntake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightIntake.setPower(.3);
        }
        else if(gamepad1.dpad_right){
            //rightIntake.setTargetPosition(-108);
            //rightIntake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightIntake.setPower(-.3);
        }


        else if(gamepad1.dpad_down){ //When the bot clamps down on a block.
            leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftIntake.setPower(-.6);
            rightIntake.setPower(-.6);
        }

        if(gamepad1.y){
            slowMode = !slowMode;
        }
       




//Lift Code
        if(gamepad1.right_trigger > 0){
            leftLift.setPower(.4);
            rightLift.setPower(.4);
        }
        else if(gamepad1.left_trigger > 0){
            leftLift.setPower(-.4);
            rightLift.setPower(-.4);
        }
        else{
            leftLift.setPower(0);
            rightLift.setPower(0);
        }

 //Platform Mover
        if(gamepad1.x){
            if(rightGrip.getPosition() == 0){
                rightGrip.setPosition(180);
            }
            else{
                rightGrip.setPosition(0);
            }
        }



        telemetry.addData("LeftEncoderPosition", leftIntake.getCurrentPosition());
        telemetry.addData("RightEncoderPosition", rightIntake.getCurrentPosition());
        telemetry.addData("SlowMode", slowMode);
        //telemetry.addData("backLeftPosition", backLeft.getCurrentPosition());
        //telemetry.addData("frontLeftPosition", frontLeft.getCurrentPosition());
        //telemetry.addData("backRightPosition", backRight.getCurrentPosition());
        //telemetry.addData("frontRightPosition", frontRight.getCurrentPosition());

        //telemetry.addData("leftMotorSpeed", leftIntake.getPower());
        //telemetry.addData("rightMotorSpeed", rightIntake.getPower());


    }



    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
