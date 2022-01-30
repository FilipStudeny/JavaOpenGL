package Engine;

public class Time {

    public static double TimeStarted = System.nanoTime();

    public static float GetTime(){
        return (float)((System.nanoTime() - TimeStarted) * 1E-9);
    }
}


