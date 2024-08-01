package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.HardwareDevice;
//import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
// import com.qualcomm.robotcore.util.Range;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class servo extends OpMode {
    
    public Servo servo
    @Override
    public void init() {
        servo = hardwareMap.get(CRServo.class, "servo");
    }
    
    @Override
    public void loop() {
        if(gamepad1.a) {
            servo.setPosition(0);
        }
        
        if(gamepad1.b){
            
            servo.setPosition(1);
            
        }
        
      //  servo.setPower(0);
    }

}
