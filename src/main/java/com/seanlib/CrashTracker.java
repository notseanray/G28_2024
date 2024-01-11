package seanlib;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

// TODO switch logs to using csv

public class CrashTracker {
    public static String markMarker = "λ";
    private static final UUID RUN_INSTANCE_UUID = UUID.randomUUID();

    private static String[] logBuffer = new String[Constants.logBufferSize];
    private static String[] logBufferTS = new String[Constants.logBufferSize];
    // uint
    private static int logIndex = 0;

    public static void logRobotStartup() {
        logMarker("robot startup");
    }

    public static void logRobotConstruction() {
        logMarker("robot startup");
    }

    public static void logRobotInit() {
        logMarker("robot init");
    }

    public static void logTeleopInit() {
        logMarker("teleop init");
    }

    public static void logAutoInit() {
        logMarker("auto init");
    }

    public static void logDisabledInit() {
        logMarker("disabled init");
    }

    // we are only using this to log information for diagnostics
    public static void logUtility(String message) {
        if (logIndex >= Constants.logBufferSize) {
            logIndex = 0;
            logMarkerBuffered(logBuffer, logBufferTS);
        }
        logBuffer[logIndex] = message;
        logBufferTS[logIndex] = Util.readableTimestamp();
        logIndex++;
    }

    public static void logUtilityFlush() {
        for (int i = logIndex; i < Constants.logBufferSize; i++) {
            logBuffer[i] = "";
            logBufferTS[i] = "";
        }
        logMarkerBuffered(logBuffer, logBufferTS);
    }

    public static void logThrowableCrash(Throwable throwable) {
        logMarker("Exception", throwable);
    }

    private static void logMarker(String mark) {
        logMarker(mark, null);
    }

    private static void logMarkerBuffered(String[] marks, String[] ts) {
        String data = "";
        for (int i = 0; i < Constants.logBufferSize; i++) {
            data += markMarker + " " + marks[i] + " " + ts[i] + " " + RUN_INSTANCE_UUID.toString();
            data += "\n";
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("/home/lvuser/crash_tracking.txt", true))) {
            writer.print(data);
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logMarker(String mark, Throwable nullableException) {

        try (PrintWriter writer = new PrintWriter(new FileWriter("/home/lvuser/crash_tracking.txt", true))) {
            writer.print(markMarker);
            writer.print(" ");
            writer.print(mark);
            writer.print(" ");
            writer.print(Util.readableTimestamp());
            writer.print(" ");
            writer.print(RUN_INSTANCE_UUID.toString());

            if (nullableException != null) {
                writer.print(", ");
                nullableException.printStackTrace(writer);
            }

            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
