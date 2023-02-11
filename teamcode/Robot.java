package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;

    public void init(HardwareMap hwMap) {
        frontLeft = hwMap.dcMotor.get("front_left");
        frontRight = hwMap.dcMotor.get("front_right");
        backLeft = hwMap.dcMotor.get("back_left");
        backRight = hwMap.dcMotor.get("back_right");

        //Reversing one side of the Mecanum drive
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        //Making forward absolute for the other side to go FORWARD
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    public void mecDrive(double frontLeft, double frontRight, double  backLeft,  double backRight){
        this.frontLeft.setPower(frontLeft);
        this.frontRight.setPower(frontRight);
        this.backLeft.setPower(backLeft);
        this.backRight.setPower(backRight);
    }

}
