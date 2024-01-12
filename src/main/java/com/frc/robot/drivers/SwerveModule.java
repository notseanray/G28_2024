package com.frc.robot.drivers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.ctre.phoenix.sensors.CANCoder;
import com.seanlib.CTREConfigs;
import com.seanlib.CTREModuleState;
import com.seanlib.SwerveModuleConstants;
import com.seanlib.Constants;
import com.seanlib.Conversions;
import com.seanlib.REVConfigs;
import com.team254.lib.drivers.TalonFXFactory;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.CAN;

import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.SparkFlexExternalEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;

public class SwerveModule {
    public int moduleNumber;
    public double angleOffset;
    private CANSparkMax mAngleMotor;
    private CANSparkMax mDriveMotor;
    // private TalonFX mDriveMotor;
    private AbsoluteEncoder angleEncoder;
    private double lastAngle;

    private double anglekP;
    private double anglekI;
    private double anglekD;

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.SwerveConstants.driveKS, Constants.SwerveConstants.driveKV, Constants.SwerveConstants.driveKA);

    public SwerveModulePosition toSwerveModulePosition() {
        // return new SwerveModulePosition(mDriveMotor.getActiveTrajectoryPosition(), getState().angle);
        // TODO convert to distance via Conversions.java
        return new SwerveModulePosition(mDriveMotor.getEncoder().getPosition(), getState().angle);
    }

    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants){
        this.moduleNumber = moduleNumber;
        angleOffset = moduleConstants.angleOffset;
        
        // TODO if cancoder is -1
        /* Angle Encoder Config */
        if (moduleConstants.cancoderID < 0) {
            angleEncoder = mAngleMotor.getAbsoluteEncoder(SparkAbsoluteEncoder.Type.kDutyCycle);
        } else {
            // TODO handle this properly with interface to abstract over
            // angleEncoder = new CANCoder(moduleConstants.cancoderID);
        }
        configAngleEncoder();
        // angleEncoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 255);
        // angleEncoder.setStatusFramePeriod(CANCoderStatusFrame.VbatAndFaults, 255);

        /* Angle Motor Config */
        mAngleMotor = REVConfigs.configureCANSparkMax(moduleConstants.angleMotorID);
        configAngleMotor();
        TalonFXConfiguration angleConfiguration = CTREConfigs.swerveAngleFXConfig();
        anglekP = angleConfiguration.slot0.kP;
        anglekI = angleConfiguration.slot0.kI;
        anglekD = angleConfiguration.slot0.kD;

        /* Drive Motor Config */
        // mDriveMotor = TalonFXFactory.createDefaultTalon(moduleConstants.driveMotorID);

        // TODO handle config separately for this
        mDriveMotor = REVConfigs.configureCANSparkMax(moduleConstants.driveMotorID);
        configDriveMotor();

        lastAngle = getState().angle.getDegrees();
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop){
        desiredState = CTREModuleState.optimize(desiredState, getState().angle); //Custom optimize command, since default WPILib optimize assumes continuous controller which CTRE is not

        if(isOpenLoop){
            double percentOutput = desiredState.speedMetersPerSecond / Constants.SwerveConstants.maxSpeed;
            // mDriveMotor.set(ControlMode.PercentOutput, percentOutput);
            mDriveMotor.set(percentOutput);
        }
        else {
            // TODO need to figure out what to do for this
            // TODO PID CONTROLLER
            // double velocity = Conversions.MPSToFalcon(desiredState.speedMetersPerSecond, Constants.SwerveConstants.wheelCircumference, Constants.SwerveConstants.driveGearRatio);
            // mDriveMotor.set(ControlMode.Velocity, velocity, DemandType.ArbitraryFeedForward, feedforward.calculate(desiredState.speedMetersPerSecond));

        }

        double angle = (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.SwerveConstants.maxSpeed * 0.01)) ? lastAngle : desiredState.angle.getDegrees(); //Prevent rotating module if speed is less then 1%. Prevents Jittering.
        // mAngleMotor.set(ControlMode.Position, Conversions.degreesToFalcon(angle, Constants.SwerveConstants.angleGearRatio)); 
        lastAngle = angle;
    }

    public void resetToAbsolute(){
        // double absolutePosition = Conversions.degreesToFalcon(getCanCoder().getDegrees() - angleOffset, Constants.SwerveConstants.angleGearRatio);
        // mAngleMotor.get(absolutePosition);
    }

    private void configAngleEncoder(){        
        // USE THIS FOR TALON
        // angleEncoder.configFactoryDefault();
        // angleEncoder.configAllSettings(CTREConfigs.swerveCancoderConfig());
    }

    private void configAngleMotor(){
        mAngleMotor.restoreFactoryDefaults();
        // TODO angle config
        // mAngleMotor.configAllSettings(CTREConfigs.swerveAngleFXConfig());
        mAngleMotor.setInverted(Constants.SwerveConstants.angleMotorInvert);
        mAngleMotor.setIdleMode(IdleMode.kCoast);
        resetToAbsolute();
    }

    private void configDriveMotor(){        
        // USE THIS FOR TALON
        // mDriveMotor.configFactoryDefault();
        // mDriveMotor.configAllSettings(CTREConfigs.swerveDriveFXConfig());
        // mDriveMotor.setInverted(Constants.SwerveConstants.driveMotorInvert);
        // mDriveMotor.setNeutralMode(Constants.SwerveConstants.driveNeutralMode);
        // mDriveMotor.setSelectedSensorPosition(0);

        mDriveMotor.setInverted(Constants.SwerveConstants.driveMotorInvert);
    }

    public void updateAnglePID(double kP, double kI, double kD) {
        if (anglekP != kP) {
            anglekP = kP;
            mAngleMotor.getPIDController().setP(anglekP);
        }
        if (anglekI != kI) {
            anglekI = kI;
            mAngleMotor.getPIDController().setI(anglekI);
        }
        if (anglekD != kP) {
            anglekD = kD;
            mAngleMotor.getPIDController().setD(anglekD);        
        }
    }

    public double[] getAnglePIDValues() {
        double[] values = {anglekP, anglekI, anglekD};
        return values;
    }

    public Rotation2d getCanCoder(){
        return Rotation2d.fromDegrees(angleEncoder.getPosition());
    }

    public double getTargetAngle() {
        return lastAngle;
    }

    public SwerveModuleState getState(){
        // double velocity = Conversions.falconToMPS(mDriveMotor.getSelectedSensorVelocity(), Constants.SwerveConstants.wheelCircumference, Constants.SwerveConstants.driveGearRatio);
        double velocity = Conversions.neoToMPS(mDriveMotor.getEncoder().getVelocity(), Constants.SwerveConstants.wheelCircumference, Constants.SwerveConstants.driveGearRatio);
        Rotation2d angle = Rotation2d.fromDegrees(angleEncoder.getPosition());
        return new SwerveModuleState(velocity, angle);
    }
    
}

