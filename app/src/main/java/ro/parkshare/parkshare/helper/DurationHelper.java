package ro.parkshare.parkshare.helper;

public class DurationHelper {

    public static final int MILLISECONDS_IN_A_MINUTE = (1000 * 60);
    public static final int MINUTES_IN_AN_HOUR = 60;

    public static String milisToString(long millis) {
        long minutes = millis / MILLISECONDS_IN_A_MINUTE;
        long hours = minutes / MINUTES_IN_AN_HOUR;

        if (hours > 0) {
            long remainingMinutes = minutes % MINUTES_IN_AN_HOUR;
            return "" + hours + "h " + remainingMinutes + " minutes";
        } else {
            return minutes + " minutes";
        }
    }
}
