package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "arm", group = "test")
public class encoderArm extends OpMode {
    Robot robot = new Robot();
    final double ticksPerDegree = 28.0 / 360;
    final double len1 = 12, len2 = 12;
    final double lowSetPointX = 0;
    final double lowSetPointY = 0;
    final double midSetPointX = 0;
    final double midSetPointY = 0;
    final double highSetPointX = 0;
    final double highSetPointY = 0;
    double x = 0;
    double y = 0;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

        if (gamepad1.x) {
            x = lowSetPointX;
            y = lowSetPointY;
        } else if (gamepad1.y){
            x = midSetPointX;
            y = midSetPointY;
        } else if (gamepad1.b) {
            x = highSetPointX;
            y = highSetPointY;
        }

        double q2 = Math.PI - Math.acos((Math.pow(len1, 2) + Math.pow(len2, 2) - Math.pow(x, 2) - Math.pow(y, 2)) / (2 * len1 * len2));
        double q1 = Math.atan(x / y) - Math.atan((len2 * Math.sin(q2)) / (len1 + len2 * Math.cos(q2)));
    }
}
