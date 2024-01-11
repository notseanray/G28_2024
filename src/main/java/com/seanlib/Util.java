package com.seanlib;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
    public static String readableTimestamp() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}
