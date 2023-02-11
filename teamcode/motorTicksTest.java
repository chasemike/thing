package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "encoderTest", group = "testing")
public class motorTicksTest extends OpMode {

    double ticks = 100;
    double wheelCir = Math.PI * 2.95276;
    double ticksPerInch = ticks / wheelCir;
    double travelDistance = ticksPerInch * wheelCir;


    RobotTesting robot = new RobotTesting();
    @Override
    public void init() {
        robot.init(hardwareMap);
//        robot.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("pos", robot.motor.getCurrentPosition());
        telemetry.update();
    }

    double Kp = .005;
    double Kd = .001;
    double Ki = .001;
    double lastError = 0;
    ElapsedTime timer = new ElapsedTime();
    boolean atPos = false;
    double timerNowForDiff = timer.now(TimeUnit.SECONDS);
    double integralSum = 0;

    @Override
    public void loop() {
        while (!atPos) {
            double encoderPos = robot.motor.getCurrentPosition();
            double error = travelDistance - encoderPos;
            double derivative = (error - lastError) / timer.now(TimeUnit.SECONDS) - timerNowForDiff;
            integralSum += (error * timer.now(TimeUnit.SECONDS) - timerNowForDiff);
            double out = (Kp * error);

//            if () {
//                out = 0;
//                atPos = true;
//            }
            robot.motor.setPower(out);
            lastError = error;

            telemetry.addData("time: ", timer.seconds());
            telemetry.addData("time-atStart: ", timerNowForDiff);
            telemetry.addData("timeNow: ", timer.now(TimeUnit.SECONDS));
            telemetry.addData("divDiff: ", (timer.now(TimeUnit.SECONDS) - timerNowForDiff));
            telemetry.addData("distanceToTicks: ", ticksPerInch);
            telemetry.addData("atPos: ", atPos);
            telemetry.addData("encoderPos: ", encoderPos);
            telemetry.addData("OUT: ", out);
            telemetry.addData("error: ", error);
            telemetry.addData("derivative: ", derivative);
            telemetry.addData("derivative output: ", (derivative * Kd));
            telemetry.addData("lastError: ", lastError);
            telemetry.addData("gettingPower", robot.motor.getPower());
            telemetry.update();
            timer.reset();
//        }
            double newTarget = travelDistance;
            robot.motor.setTargetPosition((int) newTarget);
            robot.motor.setPower(.1);
            robot.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            telemetry.addData("travel", travelDistance);
            telemetry.addData("we at ", robot.motor.getCurrentPosition());
            telemetry.update();

        }
    }
}
