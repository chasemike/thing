package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "szymon", group = "skdakh")
public class autoPID extends LinearOpMode {
    Robot robot = new Robot();

    final double Kp = 0.1;
    final double Ki = 0.1;
    final double Kd = 0.1;

    double lastError = 0;
    ElapsedTime timer = new ElapsedTime();
    double integralSum = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("before loop", "hey");
        robot.mecDrive(-1,-1,1,1);

        waitForStart();
        while (opModeIsActive()) {
            double locations = robot.frontLeft.getCurrentPosition() + robot.frontRight.getCurrentPosition() + robot.backLeft.getCurrentPosition() + robot.backRight.getCurrentPosition();
            double encoderPos = robot.frontLeft.getCurrentPosition();
            double error = 50 - encoderPos;

            double out = (Kp * error);

            this.sleep(2000);
        robot.backRight.setPower(1);
        robot.frontRight.setPower(1);
        this.sleep(2000);
            // reset the timer for next time
            timer.reset();
            telemetry.addData("end of loop", "hi");
            telemetry.addData("location: ", locations);
            telemetry.addData("encoderPos: ", encoderPos);
            telemetry.addData("power: ", robot.frontLeft.getPower());
            telemetry.update();
        }
    }
}