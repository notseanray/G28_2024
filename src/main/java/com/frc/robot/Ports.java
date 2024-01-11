package frc.robot;

public class Ports {

    /*** SWERVE MODULE PORTS ***/

    /*  
    Swerve Modules go:
        1 2
        3 4
    If encoder is set to -1, then we will treat it as taking from the turning motor
    */

    public static final int FL_DRIVE = 1;
    public static final int FL_ROTATION = 2;
    public static final int FL_ENCODER = -1; 

    public static final int FR_DRIVE = 3; 
    public static final int FR_ROTATION = 4;
    public static final int FR_ENCODER = -1; 

    public static final int BL_DRIVE = 5;
    public static final int BL_ROTATION = 6;
    public static final int BL_ENCODER = -1; 

    public static final int BR_DRIVE = 7;
    public static final int BR_ROTATION = 8;
    public static final int BR_ENCODER = -1; 

    // Pigeon
    public static final int PIGEON = 20;

    /*** SUBSYSTEM IDS ***/
    // Infrastucture
    public static final int PCM = 21;

}