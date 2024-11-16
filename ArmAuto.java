import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "score")
public class ArmAuto extends LinearOpMode{
    public void runOpMode() {
        Movement move = new Movement();
        move.initialize(hardwareMap);


        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();


        if (opModeIsActive()) {
            //move.strafe(-500, 50);
            //move.moveForward(-800, 25, telemetry);
            move.strafe(-500, 50, telemetry);
            move.moveForward(-1250, 25, telemetry);
            move.left(-650, 50);
            move.moveForward(-1200, 25, telemetry);
            move.armMove(-55, 10, telemetry, -.8);
            move.linearSlide(-1800, 100, telemetry);
            //move.left(-400, 25);
            move.claw(false);
            sleep(1000);
            move.claw(true);
            move.linearSlide(0, 60, telemetry);
            //move.armMove(0, 10, telemetry, -.1);
        }
    }
}