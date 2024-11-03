import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Movement extends LinearOpMode{
    DcMotor leftFrontDrive;
    DcMotor rightFrontDrive;
    DcMotor rightBackDrive;
    DcMotor leftBackDrive;
    DcMotor linearSlide;
    DcMotor armLeft;
    DcMotor armRight;

    double integralSum = 0;
    double Kp = 0.1;
    double Ki = 0;
    double Kd = 0;
    // double Kp = 0.05;
    // double Ki = 0.0150;
    // double Kd = 0.000001;
    ElapsedTime timer = new ElapsedTime();

    public void initialize(HardwareMap hardwareMap){
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        armLeft = hardwareMap.get(DcMotor.class, "arm_left");
        armRight = hardwareMap.get(DcMotor.class, "arm_right");
        linearSlide = hardwareMap.get(DcMotor.class, "linear_slide");

        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);

        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private double PIDControl(double reference, double lastError, DcMotor motor) {
        double state = motor.getCurrentPosition();
        double error = reference - state;
        if(error < 100 && error > -100) {
            error = 0;
        }
        integralSum += error * timer.seconds();
        double derivative = (error-lastError) / timer.seconds();

        lastError = error;

        timer.reset();

        double out = (error*Kp) + (derivative * Kd) + (integralSum * Ki);
        return out;
    }
    // tuned power
    public void strafe(int reference, int variance) {
        while(leftFrontDrive.getCurrentPosition() > reference - variance
                || leftFrontDrive.getCurrentPosition() < reference + variance
        ) {
            double power = PIDControl(reference, reference, leftFrontDrive);
            leftFrontDrive.setPower(power);
            rightFrontDrive.setPower(power);
            rightBackDrive.setPower(-power);
            leftBackDrive.setPower(-power);
            if(leftFrontDrive.getCurrentPosition() >= reference - (variance+1)
                    && leftFrontDrive.getCurrentPosition() <= reference + (variance+1)) {
                leftFrontDrive.setPower(0);
                rightFrontDrive.setPower(0);
                rightBackDrive.setPower(0);
                leftBackDrive.setPower(0);
                break;
            }
        }
    }
    //manual power version
    public void strafe(int reference, int variance, double power) {
        while(leftFrontDrive.getCurrentPosition() > reference - variance
                || leftFrontDrive.getCurrentPosition() < reference + variance) {
            leftFrontDrive.setPower(power);
            rightFrontDrive.setPower(power);
            rightBackDrive.setPower(-power);
            leftBackDrive.setPower(-power);
        }
    }

    public void moveForwardToThree(int reference, int variance) {
        while(leftFrontDrive.getCurrentPosition() > reference + variance
                || leftFrontDrive.getCurrentPosition() < reference - variance) {
            double power = PIDControl(reference, reference, leftFrontDrive);
            leftFrontDrive.setPower(power * 0.5);
            rightFrontDrive.setPower(-power * 0.5);
            leftBackDrive.setPower(power * 0.5);
            rightBackDrive.setPower(-power * 0.5);
        }
    }
    public void left(int reference, int variance) {
        while(leftFrontDrive.getCurrentPosition() < reference - variance
                || leftFrontDrive.getCurrentPosition() > reference + variance) {
            double power = PIDControl(reference, reference, leftFrontDrive);
            leftFrontDrive.setPower(0.5 * power);
            leftBackDrive.setPower(0.5 * power);
            rightBackDrive.setPower(power);
            rightFrontDrive.setPower(power);
        }
    }
    // public void scoreOnBoard(int reference, int variance, Telemetry telemetry) {

    //     while(linearSlide.getCurrentPosition() < reference - variance
    //             || linearSlide.getCurrentPosition() > reference + variance) {
    //         double power = PIDControl (reference, reference, linearSlide);
    //         linearSlide.setPower(power);
    //         telemetry.addData("position: ", linearSlide.getCurrentPosition());
    //         telemetry.update();
    //     }
    //     telemetry.addData("finished looping", "a");
    //     telemetry.update();
    //     linearSlide.setPower(0);
    //     tray.setPower(-0.5);
    //     sleep(500);
    //     tray.setPower(0);
    // }
    public void armMove(int reference, int variance, Telemetry telemetry, double power) {
        while(armLeft.getCurrentPosition() < reference - variance || armLeft.getCurrentPosition() > reference + variance) {
            //double power = PIDControl(reference, reference, armLeft);
            armLeft.setPower(power);
            armRight.setPower(-power);
            telemetry.addData("armPos", armLeft.getCurrentPosition());
            telemetry.update();
        }
        armLeft.setPower(-.4);
        armRight.setPower(.4);
    }

    public void linearSlide(int reference, int variance, Telemetry telemetry) {
        int y = linearSlide.getCurrentPosition();
        if(reference > y) {
            armLeft.setPower(-.5);
            armRight.setPower(.5);
        }

        reference /= 2;
        while(linearSlide.getCurrentPosition() < reference - variance
                || linearSlide.getCurrentPosition() > reference + variance) {
            double power = PIDControl (reference, reference, linearSlide);
            linearSlide.setPower(power);
            telemetry.addData("position: ", linearSlide.getCurrentPosition());
            telemetry.update();
        }
        if(reference < y + 1000) {
            armLeft.setPower(-.6);
            armRight.setPower(.6);
        }else if(reference > y) {
            armLeft.setPower(-.4);
            armRight.setPower(.4);
        }else {
            armLeft.setPower(-.5);
            armRight.setPower(.5);
        }

        reference *= 2;
        while(linearSlide.getCurrentPosition() < reference - variance
                || linearSlide.getCurrentPosition() > reference + variance) {
            double power = PIDControl (reference, reference, linearSlide);
            linearSlide.setPower(power);
            telemetry.addData("position: ", linearSlide.getCurrentPosition());
            telemetry.update();
        }

        if(reference < y + 1000) {
            armLeft.setPower(-1);
            armRight.setPower(1);
        }else if(reference > y) {
            armLeft.setPower(-.4);
            armRight.setPower(.4);
        }else {
            armLeft.setPower(-.5);
            armRight.setPower(.5);
        }
    }

    // public void retract(int reference, int variance, Telemetry telemetry) {
    //     reference = -reference;
    //     tray.setPower(-0.5);
    //     sleep(500);
    //     tray.setPower(0);
    //     while(linearSlide.getCurrentPosition() < reference - variance
    //             || linearSlide.getCurrentPosition() > reference + variance) {
    //         double power = PIDControl (reference, reference, linearSlide);
    //         linearSlide.setPower(power);
    //         telemetry.addData("position: ", linearSlide.getCurrentPosition());
    //         telemetry.update();
    //     }
    // }
    public void resetPower() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        linearSlide.setPower(0);
    }
    public void runOpMode() {
    }

}