package com.seanlib;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public final class REVConfigs {
    public static CANSparkMax configureCANSparkMax(int canid) {
        CANSparkMax spark = new CANSparkMax(canid, MotorType.kBrushless);
        return spark;
    }
    // public static TalonFXConfiguration swerveDriveFXConfig() {
/*
        TalonFXConfiguration angleConfig = new TalonFXConfiguration();
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.SwerveConstants.angleEnableCurrentLimit, 
            Constants.SwerveConstants.angleContinuousCurrentLimit, 
            Constants.SwerveConstants.anglePeakCurrentLimit, 
            Constants.SwerveConstants.anglePeakCurrentDuration);

        angleConfig.slot0.kP = Constants.SwerveConstants.angleKP;
        angleConfig.slot0.kI = Constants.SwerveConstants.angleKI;
        angleConfig.slot0.kD = Constants.SwerveConstants.angleKD;
        angleConfig.slot0.kF = Constants.SwerveConstants.angleKF;
        angleConfig.supplyCurrLimit = angleSupplyLimit;
        angleConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;
         */    
}
