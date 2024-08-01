package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.CRServo;
// import com.qualcomm.robotcore.util.Range;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class servo extends OpMode {
    
    public CRServo servo;
    @Override
    public void init() {
        servo = hardwareMap.get(CRServo.class, "servo");
    }
    
    @Override
    public void loop() {
        while(gamepad1.a) {
            servo.setPower(1);
        }
        
        while(gamepad1.b){
            
            servo.setPower(-1);
            
        }
        
        servo.setPower(0);
    }

}
