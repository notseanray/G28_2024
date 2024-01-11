package com.frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.seanlib.ILooper;

import com.seanlib.Loop;

import com.seanlib.LoopScheduler;

import com.frc.robot.subsystems.Subsystem;

/**
 * Used to reset, start, stop, and update all subsystems at once
 */
public class SubsystemManager implements ILooper {
    public static SubsystemManager mInstance = null;

    private List<Subsystem> mAllSubsystems;
    private List<Loop> mLoops = new ArrayList<>();

    private SubsystemManager() {}

    public static SubsystemManager getInstance() {
        if (mInstance == null) {
            mInstance = new SubsystemManager();
        }

        return mInstance;
    }

    public boolean checkSubsystems() {
        boolean ret_val = true;

        for (Subsystem s : mAllSubsystems) {
            ret_val &= s.checkSystem();
        }

        return ret_val;
    }

    public void stop() {
        mAllSubsystems.forEach(Subsystem::stop);
    }

    public List<Subsystem> getSubsystems() {
        return mAllSubsystems;
    }

    public void setSubsystems(Subsystem... allSubsystems) {
        mAllSubsystems = Arrays.asList(allSubsystems);
    }

    private class EnabledLoop implements Loop {
        @Override
        public void onStart(double timestamp) {
            mLoops.forEach(l -> l.onStart(timestamp));
        }

        @Override
        public void onLoop(double timestamp) {
            mAllSubsystems.forEach(Subsystem::bufferedInput);
            mLoops.forEach(l -> l.onLoop(timestamp));
            mAllSubsystems.forEach(Subsystem::bufferedOutput);
        }

        @Override
        public void onStop(double timestamp) {
            mLoops.forEach(l -> l.onStop(timestamp));
        }

        @Override
        public void deferToNext() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'deferToNext'");
        }
    }

    private class DisabledLoop implements Loop {
        @Override
        public void onStart(double timestamp) {}

        @Override
        public void onLoop(double timestamp) {
            mAllSubsystems.forEach(Subsystem::bufferedInput);
        }

        @Override
        public void onStop(double timestamp) {}

        @Override
        public void deferToNext() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'deferToNext'");
        }
    }

    public void registerEnabledLoops(LoopScheduler enabledLooper) {
        mAllSubsystems.forEach(s -> s.registerEnabledLoops(this));
        enabledLooper.register(new EnabledLoop());
    }

    // public void registerDisabledLoops(Looper disabledLooper) {
    //     disabledLooper.register(new DisabledLoop());
    // }

    // public void registerLoggingSystems(LoggingSystem LS) {
    //     mAllSubsystems.forEach(s -> s.registerLogger(LS));
    // }

    @Override
    public void register(Loop loop) {
        mLoops.add(loop);
    }
}
