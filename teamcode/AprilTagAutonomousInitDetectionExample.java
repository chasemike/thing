/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.ArrayList;

@Autonomous(name = "camera", group = "TeleOp")
public class AprilTagAutonomousInitDetectionExample extends LinearOpMode
{
    Robot robot = new Robot();
    OpenCvCamera camera;
    DcMotor motor;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;
    AprilTagDetection tag;

    // Tag ids of sleeve
    int left = 1;
    int middle = 2;
    int right = 3;
    double Kp = .005;

    boolean atPos = false;
    double wheelCir = Math.PI * 2.95276;
    double ticks = 100;
    double ticksPerInch = ticks / wheelCir;
    double travelDistance = ticksPerInch * wheelCir;

    AprilTagDetection tagOfInterest;



    @Override
    public void runOpMode()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "cam"), cameraMonitorViewId);
        //camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "cam"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        motor = hardwareMap.dcMotor.get("motor");

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);
        String wasInside = "nope";
        String action = "nope";

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;
                boolean foundAtStart = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == left || tag.id == middle || tag.id == right)
                    {

                        wasInside = "I was inside";
                        tagOfInterest = tag;
                        tagFound = true;
//                        if (!foundAtStart) {
//                            tagOfInterest = tag;
//                            foundAtStart = !foundAtStart;
//                        }

                        //waitForStart();

//                        if (time.seconds() % 2 == 0 && time.seconds() > 3) && tag.id)  {
//
//                        } else
                        //break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            AprilTagDetection dummydummyTag = tagOfInterest;

            if (tagOfInterest != null) {
                setTag(tagOfInterest);
            }

            telemetry.update();
            sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */
        /* Actually do something useful */
        double power = 0;

        // CODEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
        if(returnTag() == null || returnTag().id == left){
            //Left
            action = "left";
            GOAHEAD(24);
            atPos = false;

        }else if(returnTag().id == middle ){
            //middle code
            action = "middle";
            GOAHEAD(24);
            atPos = false;
        }else if (returnTag().id == right){
            //right code
            action = "right";
            GOAHEAD(24);
            atPos = false;
        }
        telemetry.addData("tag: ", action);
        telemetry.addData("thing ", tagOfInterest);
        telemetry.addData("was inside ", wasInside);
        telemetry.update();


        setTag(tagOfInterest);

        /* Update the telemetry */
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
        while (opModeIsActive()) {sleep(20);}
    }

    public void GOAHEAD(double GO) {
        while (!atPos) {
            double encoderPos = robot.frontLeft.getCurrentPosition() + robot.backRight.getCurrentPosition();
            encoderPos /= 2;
            double error = GO - encoderPos;
            double out = (Kp * error);

            robot.mecDrive(out, out, out, out);

            if (out < .1 && (error < 10 || error > -10)) {
                atPos = true;
                out = 0;
            }
            motor.setPower(out);
        }
    }

    public void TURNLEFT(double GO) {
        while (!atPos) {
            double encoderPos = robot.frontLeft.getCurrentPosition() + robot.backRight.getCurrentPosition();
            encoderPos /= 2;
            double error = GO - encoderPos;
            double out = (Kp * error);

            robot.mecDrive(-out, out, -out, out);

            if (out < .1 && (error < 10 || error > -10)) {
                atPos = true;
                out = 0;
            }
            motor.setPower(out);
        }
    }
    public void setTag(AprilTagDetection tag) {
        this.tag = tag;
    }

    public AprilTagDetection returnTag() {
        return this.tag;
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}