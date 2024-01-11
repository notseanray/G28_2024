// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.frc.robot;

import com.frc.robot.subsystems.RobotStateEstimator;
import com.frc.robot.subsystems.Swerve;
import com.seanlib.CTREConfigs;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.seanlib.CrashTracker;

import com.seanlib.LoopScheduler;

public class Robot extends TimedRobot {
  	// instantiate enabled and disabled loopers
	private final LoopScheduler mEnabledLooper = new LoopScheduler();
	private final LoopScheduler mDisabledLooper = new LoopScheduler();
	// instantiate logging looper
	private final LoopScheduler mLoggingLooper = new LoopScheduler();

	// declare necessary class objects
	// private ShuffleBoardInteractions mShuffleBoardInteractions;
	public static CTREConfigs ctreConfigs;

	// subsystem instances

	private final SubsystemManager mSubsystemManager = SubsystemManager.getInstance();
	private final Swerve mSwerve = Swerve.getInstance();

	// robot state estimator
	private final RobotStateEstimator mRobotStateEstimator = RobotStateEstimator.getInstance();

	// logging system
	// private LoggingSystem mLogger = LoggingSystem.getInstance();

	// auto instances
	// private AutoModeExecutor mAutoModeExecutor;
	// private AutoModeSelector mAutoModeSelector = new AutoModeSelector();

	public Robot() {
		CrashTracker.logRobotConstruction();
	}
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
