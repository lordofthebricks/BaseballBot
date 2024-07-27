package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/*
 * This OpMode executes a POV Game style Teleop for a direct drive robot
 * The code is structured as a LinearOpMode
 *
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the arm using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Robot: Teleop POV", group="Robot")
//@Disabled
public class baseballrobotteleop extends LinearOpMode {

    /* Declare OpMode members. */
    public DcMotor  leftfrontDrive   = null;
    public DcMotor  rightfrontDrive  = null;
    public DcMotor  leftbackDrive     = null;
    public DcMotor  rightbackDrive     = null;
    public DcMotor    leftflywheel    = null;
    public DcMotor  rightflywheel   = null;
    public Servo baseBall = null;
    @Override
    public void runOpMode() {
        double leftFront;
        double rightFront;
        double leftBack;
        double rightBack;
        double leftFlywheel;
        double rightFlywheel;
        double max;

        // Define and Initialize Motors
        leftfrontDrive  = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightfrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftbackDrive   = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightbackDrive  = hardwareMap.get(DcMotor.class, "right_back_drive");
        rightflywheel = hardwareMap.get(DcMotor.class, "right_flywheel");
        baseBall = hardwareMap.get(Servo.class, "fire");
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftfrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftbackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightfrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightfrontDrive.setDirection(DcMotor.Direction.FORWARD);

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forward, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.

            leftFront = gamepad1.left_stick_y;
            leftBack = gamepad1.left_stick_y;
            rightFront = gamepad1.right_stick_y;
            rightBack = gamepad1.right_stick_y;

            // Normalize the values so neither exceed +/- 1.0
            max = Math.abs(leftFront);
            max = Math.abs(leftBack);
            max = Math.abs(rightBack);
            max = Math.abs(rightFront);

            if (max > 1.0)
            {
                leftFront /= max;
                leftBack /= max;
                rightFront /= max;
                rightBack /= max;
            }

            // Output the safe vales to the motor drives.
            leftfrontDrive.setPower(leftFront);
            leftbackDrive.setPower(leftBack);
            rightfrontDrive.setPower(rightFront);
            rightbackDrive.setPower(rightBack);

            // Move both servos to new position.  Assume servos are mirror image of each other.
            //clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            //leftClaw.setPosition(MID_SERVO + clawOffset);
            //rightClaw.setPosition(MID_SERVO - clawOffset);

            // Use gamepad buttons to move arm up (Y) and down (A)
            if (gamepad1.y)
                leftflywheel.setPower(1.0);
            else if (gamepad1.a)
                rightflywheel.setPower(1.0);
            else
                leftflywheel.setPower(0.0);
            rightflywheel.setPower(0.0);
            // Send telemetry message to signify robot running;
            //telemetry.addData("fire",  "Offset = %.2f", fireOffset);
            //telemetry.addData("left",  "%.2f", left);
            //telemetry.addData("right", "%.2f", right);
            //telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
