package org.firstinspires.ftc.team2981;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutonTest", group = "Blue Side")
public class CurrentAuton extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor leftLift = null;
    private DcMotor rightLift = null;
    private Servo leftFoundation = null;
    private Servo rightFoundation = null;
    private DcMotor rightIntake = null;
    private Servo backServo = null;


    private void forward(double distance, double speed, long wait)
    {
        //Resets the encoders on the drive
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Turns on the encoders on the drive
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Stores the current value of the encoders in separate variables for easy access to these values
        double frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
        double backLeftPosition = Math.abs(backLeft.getCurrentPosition());
        double frontRightPosition = Math.abs(frontRight.getCurrentPosition());
        double backRightPosition = Math.abs(backRight.getCurrentPosition());

        //The while loop checks that the encoder values on the wheels are less than the distance inputted.
        while(frontLeftPosition < distance && frontRightPosition < distance && backLeftPosition < distance && backRightPosition < distance)
        {
            //While this condition is true, the bot moves at the speed inputted.
            frontLeft.setPower(speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed);

            //The position of each wheel is updated and stored in the variables created earlier.
            frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
            backLeftPosition = Math.abs(backLeft.getCurrentPosition());
            frontRightPosition = Math.abs(frontRight.getCurrentPosition());
            backRightPosition = Math.abs(backRight.getCurrentPosition());
        }
        //Once the loop condition fails(the bot has reached the distance), the power on each motor is set to zero.
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        //Optional time between each action on the bot.
        sleep(wait);

    }
    //These methods below follow the same pattern as the forward method above. The difference is how power is sent to the motors.
    private void backward(double distance, double speed, long wait)
    {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
        double backLeftPosition = Math.abs(backLeft.getCurrentPosition());
        double frontRightPosition = Math.abs(frontRight.getCurrentPosition());
        double backRightPosition = Math.abs(backRight.getCurrentPosition());

        while(frontLeftPosition < distance && frontRightPosition < distance && backLeftPosition < distance && backRightPosition < distance)
        {
            frontLeft.setPower(-speed);
            frontRight.setPower(-speed);
            backLeft.setPower(-speed);
            backRight.setPower(-speed);

            frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
            backLeftPosition = Math.abs(backLeft.getCurrentPosition());
            frontRightPosition = Math.abs(frontRight.getCurrentPosition());
            backRightPosition = Math.abs(backRight.getCurrentPosition());
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        sleep(wait);

    }
    private void strafeLeft(double distance, double speed, long wait)
    {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
        double backLeftPosition = Math.abs(backLeft.getCurrentPosition());
        double frontRightPosition = Math.abs(frontRight.getCurrentPosition());
        double backRightPosition = Math.abs(backRight.getCurrentPosition());

        while(frontLeftPosition < distance && frontRightPosition < distance && backLeftPosition < distance && backRightPosition < distance)
        {
            frontLeft.setPower(-speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(-speed);

            frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
            backLeftPosition = Math.abs(backLeft.getCurrentPosition());
            frontRightPosition = Math.abs(frontRight.getCurrentPosition());
            backRightPosition = Math.abs(backRight.getCurrentPosition());
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        sleep(wait);

    }
    private void strafeRight(double distance, double speed, long wait)
    {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
        double backLeftPosition = Math.abs(backLeft.getCurrentPosition());
        double frontRightPosition = Math.abs(frontRight.getCurrentPosition());
        double backRightPosition = Math.abs(backRight.getCurrentPosition());

        while(frontLeftPosition < distance && frontRightPosition < distance && backLeftPosition < distance && backRightPosition < distance)
        {
            frontLeft.setPower(speed);
            frontRight.setPower(-speed);
            backLeft.setPower(-speed);
            backRight.setPower(speed);

            frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
            backLeftPosition = Math.abs(backLeft.getCurrentPosition());
            frontRightPosition = Math.abs(frontRight.getCurrentPosition());
            backRightPosition = Math.abs(backRight.getCurrentPosition());
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        sleep(wait);

    }
    private void rotateLeft(double distance, double speed, long wait)
    {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
        double backLeftPosition = Math.abs(backLeft.getCurrentPosition());
        double frontRightPosition = Math.abs(frontRight.getCurrentPosition());
        double backRightPosition = Math.abs(backRight.getCurrentPosition());

        while(frontLeftPosition < distance && frontRightPosition < distance && backLeftPosition < distance && backRightPosition < distance)
        {
            frontLeft.setPower(-speed);
            frontRight.setPower(speed);
            backLeft.setPower(-speed);
            backRight.setPower(speed);

            frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
            backLeftPosition = Math.abs(backLeft.getCurrentPosition());
            frontRightPosition = Math.abs(frontRight.getCurrentPosition());
            backRightPosition = Math.abs(backRight.getCurrentPosition());
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        sleep(wait);

    }
    private void rotateRight(double distance, double speed, long wait)
    {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
        double backLeftPosition = Math.abs(backLeft.getCurrentPosition());
        double frontRightPosition = Math.abs(frontRight.getCurrentPosition());
        double backRightPosition = Math.abs(backRight.getCurrentPosition());

        while(frontLeftPosition < distance && frontRightPosition < distance && backLeftPosition < distance && backRightPosition < distance)
        {
            frontLeft.setPower(speed);
            frontRight.setPower(-speed);
            backLeft.setPower(speed);
            backRight.setPower(-speed);

            frontLeftPosition = Math.abs(frontLeft.getCurrentPosition());
            backLeftPosition = Math.abs(backLeft.getCurrentPosition());
            frontRightPosition = Math.abs(frontRight.getCurrentPosition());
            backRightPosition = Math.abs(backRight.getCurrentPosition());
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        sleep(wait);

    }
    //Method used to grab the foundation. If true is inputted into the method, the foundation mover servos grab onto the foundation. If false, they release.
    private void grabFoundation(boolean x, long wait)
    {
        if(x)
        {
            leftFoundation.setPosition(0);
            rightFoundation.setPosition(0);
        }
        else
            {
                leftFoundation.setPosition(1);
                rightFoundation.setPosition(1);
        }
        sleep(wait);
    }
    private void grabStone(boolean x, long wait){
        if(x)
        {
           backServo.setPosition(0);
        }
        else
        {
            backServo.setPosition(.5);
        }
        sleep(wait);
    }



    public void runOpMode() throws InterruptedException{
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        leftFoundation = hardwareMap.servo.get("leftFoundation");
        rightFoundation = hardwareMap.servo.get("rightFoundation");
        backServo = hardwareMap.servo.get("backServo");

        waitForStart();

        //Code is easy to read as it just requires methods to be called to perform specific actions.
        //Process for Stone 1
        backward(1695, .22, 500); //The bot moves backwards
        strafeLeft(260, .22, 500);
        //strafeRight(250, .22, 500); //
        grabStone(true,500);// The bot grabs the stone
        forward(450, .37, 500);// the bot moves the stone to align with skybridge
        rotateRight(1420, .22, 500);//the bot rotates
        backward(3150,.37,500);
        strafeRight(1700,.22,500);
        grabStone(false, 5000);
        strafeLeft(1700,.22,500);//inverse
        forward(3200,.37,500);
        strafeLeft(150,.22,500);
        rotateLeft(1400,.22,500);
//Stone 2




        //strafeLeft(2500, .22, 500);// the bot moves under the skybridge with the stone
        grabStone(false, 1000);// the bot releases the stone
        rotateRight(1400, .22, 500);

        //Stone 2
        strafeRight(1500, .22, 50);
        backward(2000, .22, 50);




//ignore
        forward(1000,.22,1000);
        grabFoundation(true, 2000);
        grabFoundation(false, 2000);
        forward(1000,.22,1000);
        backward(1000,.3,1000);
        strafeLeft(1500,.22,1000);
        strafeRight(1500,.22,1000);
        rotateLeft(1400,.22,1000);// 90 degree turn
        rotateRight(1400,.22,1000); // 90 degree turn
        rotateLeft(5600,.22,1000);// 360 degree turn
        rotateRight(5600,.22,1000); // 360 degree turn



    }

}


