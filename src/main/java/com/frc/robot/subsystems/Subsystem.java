package com.frc.robot.subsystems;

import com.seanlib.ILooper;

public abstract class Subsystem {
    public void writeToLog() {}

    public void bufferedInput() {}

    public void bufferedOutput() {}

    public abstract void stop();

    public void zeroSensors() {}

    public void registerEnabledLoops(ILooper enabledLooper) {}

    public abstract boolean checkSystem();

    public boolean hasEmergency = false;
}
