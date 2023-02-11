package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class autoForward extends OpMode {
    double wheelDiameterMM = 75;
    double circumferenceMM = wheelDiameterMM * Math.PI;
    double driveGearReduction = 30.21;
    double ticksPerMotorRev = 28;
    double ticksPerWheelRev = ticksPerMotorRev * driveGearReduction;
    double ticksPerMM = ticksPerWheelRev / circumferenceMM;
    double ticksPerInch = ticksPerMM * 25.4;
    Robot robot = new Robot();

    public void runMotors() {
        //runOpMode();
    }

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

    }
}
