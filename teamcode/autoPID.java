package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "thing", group = "stuff")
public class autoPID extends LinearOpMode {
    robot robot = new robot();
    boolean atPos = false;
    double Kp = .005;
    final double wheelDIm = 4;
    double wheelCir = Math.PI * wheelDIm;
    //double ticks = 100;
    double ticks = 1120;
    double ticksPerInch = ticks / wheelCir;
    double distanceFoward = 28;
    double distanceRight = 24;
    double travelDistance = 0;

//    double flBrEncoder = robot.frontLeft.getCurrentPosition() - (7 * ticksPerInch);
//    double frBLEncoder = robot.frontRight.getCurrentPosition() - (7 * ticksPerInch);
//    double mean = (flBrEncoder + frBLEncoder) / 2;
//    double ticksOffDistanceWithTravel = travelDistance - mean;

    public void resetEveryone() {
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        resetEveryone();

        waitForStart();

        travelDistance = ticksPerInch * distanceFoward;
        while (!atPos) {
            double encoderPos = robot.frontLeft.getCurrentPosition() + robot.backRight.getCurrentPosition();
            encoderPos /= 2;
            double error = travelDistance - encoderPos;
            double out = (Kp * error);

            if (out < .1 && Math.abs(error) < 1) {
                atPos = true;
                out = 0;
            }
            robot.mecDriveLazy(out);

            telemetry.addData("FIRST ", true);
            telemetry.addData("encoderPos ", encoderPos);
            telemetry.addData("going to ", travelDistance);
            telemetry.addData("error ", error);
            telemetry.addData("frontLeft ", robot.frontLeft.getCurrentPosition());
            telemetry.addData("frontRight ", robot.frontRight.getCurrentPosition());
            telemetry.addData("backLeft ", robot.backLeft.getCurrentPosition());
            telemetry.addData("backRight ", robot.backRight.getCurrentPosition());
            telemetry.addData("power ", out);
            telemetry.update();
        }

        atPos = false;
        resetEveryone();
        travelDistance = ticksPerInch * distanceRight;

        while (!atPos) {
            double encoderPos = -robot.frontLeft.getCurrentPosition() + -robot.backRight.getCurrentPosition();
            encoderPos /= 2;
            double error = travelDistance - encoderPos;
            double out = (Kp * error);

            if (out < .1 && Math.abs(error) < 1) {
                atPos = true;
                out = 0;
            }
            robot.mecDriveLazyTurn(-out, out);

            telemetry.addData("SECOND ", true);
            telemetry.addData("encoderPos ", encoderPos);
            telemetry.addData("going to ", travelDistance);
            telemetry.addData("error ", error);
            telemetry.addData("frontLeft ", robot.frontLeft.getCurrentPosition());
            telemetry.addData("frontRight ", robot.frontRight.getCurrentPosition());
            telemetry.addData("backLeft ", robot.backLeft.getCurrentPosition());
            telemetry.addData("backRight ", robot.backRight.getCurrentPosition());
            telemetry.addData("power ", out);
            telemetry.update();
        }

        atPos = false;
        resetEveryone();
        travelDistance = ticksPerInch * distanceRight;

        while (!atPos) {
            double encoderPos = robot.frontLeft.getCurrentPosition() + robot.backRight.getCurrentPosition();
            encoderPos /= 2;
            double error = travelDistance - encoderPos;
            double out = (Kp * error);

            if (out < .1 && Math.abs(error) < 1) {
                atPos = true;
                out = 0;
            }
            robot.mecDriveLazyTurn(out, -out);

            telemetry.addData("SECOND ", true);
            telemetry.addData("encoderPos ", encoderPos);
            telemetry.addData("going to ", travelDistance);
            telemetry.addData("error ", error);
            telemetry.addData("frontLeft ", robot.frontLeft.getCurrentPosition());
            telemetry.addData("frontRight ", robot.frontRight.getCurrentPosition());
            telemetry.addData("backLeft ", robot.backLeft.getCurrentPosition());
            telemetry.addData("backRight ", robot.backRight.getCurrentPosition());
            telemetry.addData("power ", out);
            telemetry.update();
        }
    }
}
