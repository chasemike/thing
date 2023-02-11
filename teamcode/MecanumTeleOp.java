package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.ArrayList;

@TeleOp(name = "TeleOp" , group = "TeleOps")

public class MecanumTeleOp extends OpMode {
    public Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Initialized, Press A + Start for controller 1");
        telemetry.addLine("Initialized, Press B + Start for controller 2");

    }
    //Constructor to set breaks for the Mecanum Wheel
    public void zeroMode(double x, double y) {
        if (x == 0 && y == 0) {
            robot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            robot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    @Override
    public void loop() {
        /*
        We only gather x and y value from the left stick for robot movement. However right stick only uses x
        value to turn robot, y value is not needed.
         */
        double x = (-gamepad1.left_stick_x); // Left stick x value [-1,1]
        double y = (-gamepad1.left_stick_y); // Left stick y value [-1,1]
        double turn = (-gamepad1.right_stick_x); // Right stick x value [-1, 1]

        // Desired robot speed −1 to 1
        double speed = Math.hypot(x, y);
        // Desired robot angle from -pi to pi
        double angle = Math.atan2(y, x);

        // Force against wheels for FR and BL
        double sin = Math.sin(angle - (Math.PI / 4));
        // Force against wheels for FL and BR
        double cos = Math.cos(angle - (Math.PI / 4));
        // This accounts for the the full range of the controller for power limits
        double maxPower = Math.max(Math.abs(sin), Math.abs(cos));

        /*
        The next four line sets power to a specific variable name
        for a motor which is later called using robot.mecDrive()
         */
        double frontLeft = (((speed * cos) + turn) / maxPower);
        double frontRight = (((speed * sin) - turn) / maxPower);
        double backLeft = (((speed * sin) + turn) / maxPower);
        double backRight = (((speed * cos) - turn) / maxPower);

        // Makes sure we don't exceed the [-1,1] motor speed range
        if ((speed + Math.abs(turn)) > 1) {
            frontLeft /= speed + turn;
            frontRight /= speed + turn;
            backLeft /= speed + turn;
            backRight /= speed + turn;
        }
        // Calls the robot class to set motor speed to each wheels
        robot.mecDrive(-frontLeft * .75, frontRight * .75, backLeft * .75, backRight * .75);


        zeroMode(x, y);

        //Telemetry Feedback to drivers
        telemetry.addData("x", -x);
        telemetry.addData("y", y);
        telemetry.addData("Turn", turn);
        telemetry.addData("angle (tan)", angle);
        telemetry.addData("speed (hypo)", speed);
        telemetry.addData("frontLeft", frontLeft);
        telemetry.addData("frontRight", frontRight);
        telemetry.addData("backLeft", backLeft);
        telemetry.addData("backRight", backRight);

        telemetry.update();
    }
}