package seanlib;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import frc.robot.Ports;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Constants {
    // uint
    public static final int logBufferSize = 20;
    // toggle constants between comp bot and practice bot ("epsilon")
    public static final boolean isComp = true;
	
	// robot loop time
	public static final double kLooperDt = 0.02;
    
	/* Control Board */
	public static final double kTriggerThreshold = 0.2;

	public static final int kDriveControllerPort = 0;
	public static final int kOperatorControllerPort = 1;

	public static final double stickDeadband = 0.15;

	public static final class SwerveConstants {
        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

        /* Drivetrain Constants */
        // wheel to wheel for both
        public static final double trackWidth = Units.inches_to_meters(20.75);
        public static final double wheelBase = Units.inches_to_meters(20.75);

        // we need to compensate for some squish probably
        public static final double wheelDiameter = Units.inches_to_meters(4.0);
        public static final double wheelCircumference = wheelDiameter * Math.PI;

        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;

        public static final double driveGearRatio = 6.75;
        public static final double angleGearRatio = 21.43;
        
        public static final edu.wpi.first.math.geometry.Translation2d[] swerveModuleLocations = {
            new edu.wpi.first.math.geometry.Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new edu.wpi.first.math.geometry.Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
            new edu.wpi.first.math.geometry.Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new edu.wpi.first.math.geometry.Translation2d(-wheelBase / 2.0, -trackWidth / 2.0)
        };

        public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(swerveModuleLocations);

        /* Swerve Current Limiting */
        // TODO replace with dynamic limiting
        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
        public static final double anglePeakCurrentDuration = 0.1;
        public static final boolean angleEnableCurrentLimit = true;

        public static final int driveContinuousCurrentLimit = 35;
        public static final int drivePeakCurrentLimit = 60;
        public static final double drivePeakCurrentDuration = 0.1;
        public static final boolean driveEnableCurrentLimit = true;

        /* Angle Motor PID Values */
        public static final double angleKP = 0.3;
        public static final double angleKI = 0.0;
        public static final double angleKD = 0.0;
        public static final double angleKF = 0.0;

        /* Drive Motor PID Values */
        public static final double driveKP = 0.05;
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0;
        public static final double driveKF = 0.0;

        /* Drive Motor Characterization Values */
        public static final double driveKS = (0.32 / 12);
        public static final double driveKV = (1.51 / 12);
        public static final double driveKA = (0.27 / 12);

        /* Swerve Profiling Values */
        public static final double maxSpeed = 4.5; // meters per second
        public static final double maxAngularVelocity = 10.0;

        /* Neutral Modes */
        public static final NeutralMode angleNeutralMode = NeutralMode.Coast;
        public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

        /* Motor Inverts */
        public static final boolean driveMotorInvert = false;
        public static final boolean angleMotorInvert = true;

        /* Angle Encoder Invert */
        public static final boolean canCoderInvert = false;

        /* Controller Invert */
        public static final boolean invertYAxis = false;
        public static final boolean invertRAxis = false;
        public static final boolean invertXAxis = false; 


        /*** MODULE SPECIFIC CONSTANTS ***/

        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final double epsilonAngleOffset = 239.06;
            public static final double compAngleOffset = 68.37; 

            public static seanlib.SwerveModuleConstants SwerveModuleConstants() {
                return new SwerveModuleConstants(Ports.FL_DRIVE, Ports.FL_ROTATION, Ports.FL_ENCODER,
                        isComp ? compAngleOffset : epsilonAngleOffset);
            }
        }
        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final double epsilonAngleOffset = 339.96;
            public static final double compAngleOffset = 312.53; 
            
            public static SwerveModuleConstants SwerveModuleConstants() {
                return new SwerveModuleConstants(Ports.FR_DRIVE, Ports.FR_ROTATION, Ports.FR_ENCODER,
                        isComp ? compAngleOffset : epsilonAngleOffset);
            }
        }
        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final double epsilonAngleOffset = 317.20;
            public static final double compAngleOffset = 357.97;

            public static SwerveModuleConstants SwerveModuleConstants() {
                return new SwerveModuleConstants(Ports.BL_DRIVE, Ports.BL_ROTATION, Ports.BL_ENCODER,
                        isComp ? compAngleOffset : epsilonAngleOffset);
            }
        }
        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final double epsilonAngleOffset = 311.22;
            public static final double compAngleOffset = 295.2;

            public static SwerveModuleConstants SwerveModuleConstants() {
                return new SwerveModuleConstants(Ports.BR_DRIVE, Ports.BR_ROTATION, Ports.BR_ENCODER,
                        isComp ? compAngleOffset : epsilonAngleOffset);
            }
        }
    }    
}
