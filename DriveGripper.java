package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="DriveGripper", group="Exercises")
//@Disabled
public class DriveGripper extends LinearOpMode {
   
    Servo   armServo;
    float   leftY, rightY;
    double  armPosition;
    double  MIN_POSITION = 0, MAX_POSITION = 1;

    // called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException
    {
        armServo = hardwareMap.servo.get("armServo");
        telemetry.addData("Mode", "waiting");
        telemetry.update();

        // wait for start button.

        waitForStart();
        armPosition = .5;                   // set arm to half way up.   // set grip to full open.

        while (opModeIsActive())
        {
            leftY = gamepad1.left_stick_y * -1;
            rightY = gamepad1.right_stick_y * -1;

            telemetry.addData("Mode", "running");
            telemetry.addData("sticks", "  left=" + leftY + "  right=" + rightY);

            // check the gamepad buttons and if pressed, increment the appropriate position
            // variable to change the servo location.

            // move arm down on A button if not already at lowest position.
            if (gamepad1.a && armPosition > MIN_POSITION) armPosition -= .01;

            // move arm up on B button if not already at the highest position.
            if (gamepad1.b && armPosition < MAX_POSITION) armPosition += .01;

            // Set continuous servo power level and direction.
           
            // set the servo position/power values as we have computed them.
            armServo.setPosition(Range.clip(armPosition, MIN_POSITION, MAX_POSITION));
           
            telemetry.addData("arm servo", "position=" + armPosition + "  actual=" + armServo.getPosition());
           // telemetry.addData("grip servo", "position=" + gripPosition + "    actual=" + gripServo.getPosition());

            //telemetry.addData("arm servo", String.format("position=%.2f  actual=%.2f", armPosition,
            //    armServo.getPosition()));

            //telemetry.addData("grip servo", String.format("position=%.2f  actual=%.2f", gripPosition,
            //    gripServo.getPosition()));
            
            //telemetry.addData("arm servo", "position=%.2f  actual=%.2f", armPosition, armServo.getPosition());

            //telemetry.addData("grip servo", "position=%.2f  actual=%.2f", gripPosition, gripServo.getPosition());

            telemetry.update();
            idle(); 
        }
    }
}
