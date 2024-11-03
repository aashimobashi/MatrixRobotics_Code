import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "arm-auto")

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
            move.armMove(-80, 10, telemetry, -.8);
            move.linearSlide(-2000, 60, telemetry);
            sleep(5000);
            move.linearSlide(0, 60, telemetry);
            move.armMove(0, 10, telemetry, -.1);
        }
    }
}