package frc.robot;

public class Constants {
    
    public class climberConstants{
        public static final int beamBreakLeftPort = 1;
        public static final int beamBrakeRightPort = 6;

        public static final int climbLeftMotorCanID = 16;
        public static final int climbRightMotorCanID = 6;

        public static final boolean climbLeftMotorInverted = false;
        public static final boolean climbRightMotorInverted = false;
        public static final boolean invertBeamBrake = false;

        public static final int currentLimit = 50;

        public static final double climbRaiseSpeed = -1;
        public static final double climbLowerSpeed = 1;
    }

    public class intakeConstants{
        public static final int intakeMotorCanID = 5;
        public static final int intakeBeamBreakPort = 3;
        
        public static final boolean intakeInverted = false;

        public static final int intakeMotorCurrentLimit = 40;
        
        public static final double intakeSpeed = -.5;
        public static final double intakeEjectSpeed = .5;
    }

    public class intakeWristConstants{
        public static final int intakeWristMotorCanID = 4;
        
        public static final boolean intakeWristInverted = false;
        public static final boolean wristEncoderInverted = false;

        public static final int intakeWristMotorSmartCurrentLimit = 50;

        public static final double intakeWristUpSpeed = .6;
        public static final double intakeWristDownSpeed = -.6;

        public class wristPID{
            public static final double kP = .008;
            public static final double kI = .0;
            public static final double kD = .0001;
        }
    }

    public class shooterConstants{
        public static final int shooterMotorCanID = 14;
        public static final int feederMotorCanID = 15;
        public static final int shooterBeamBreakPort = 5;
        
        public static final boolean shooterInverted = false;
        public static final boolean feederInverted = true;

        public static final int shooterCurrentLimit = 50;
        public static final int feederMotorCurrentLimit = 40;

        public static final double shootCloseSpeed = 3800;
        public static final double shootFarSpeed = 6000;
        public static final double shootAmpSpeed = 600;
        public static final double shootIntakeSpeed = -400;
        
        public static final double feedInSpeed = -.15;
        public static final double feedEjectSpeed = 1;

        public class shooterPID{
            public static final double kP = .001;
            public static final double kI = 0;
            public static final double kD = 0.00003;
        }
    }

    public class shooterWristConstants{

        public static final int shooterWristMotorCanID = 17;
        
        public static final boolean shooterWristInverted = true;
        public static final boolean wristEncoderInverted = false;

        public static final int shooterWristMotorSmartCurrentLimit = 50;

        public static final double shooterWristUpSpeed = .6;
        public static final double shooterWristDownSpeed = -.6;

        public class wristPID{
            public static final double kP = .01;
            public static final double kI = .000000;
            public static final double kD = .0002;
        }
    }
}
