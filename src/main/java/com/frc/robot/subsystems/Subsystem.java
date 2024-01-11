package frc.robot.subsystems;

import seanlib.ILooper;

public abstract class Subsystem {
    public void writeToLog() {}

    public void bufferedInput() {}

    public void bufferedOutput() {}

    public abstract void stop();

    public void zeroSensors() {}

    public void registerEnabledLoops(ILooper enabledLooper) {}
}
