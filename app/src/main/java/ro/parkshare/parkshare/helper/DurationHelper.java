package ro.parkshare.parkshare.helper;

public class DurationHelper {

    private static final int MILLISECONDS_IN_A_SECOND = 1000;
    private static final int SECONDS_IN_A_MINUTE = 60;
    private static final int MILLISECONDS_IN_A_MINUTE = (MILLISECONDS_IN_A_SECOND * SECONDS_IN_A_MINUTE);
    private static final int MINUTES_IN_AN_HOUR = 60;

    public static String millisToString(long millis) {
        long seconds = ((millis % MILLISECONDS_IN_A_MINUTE) / MILLISECONDS_IN_A_SECOND);
        long minutes = millis / MILLISECONDS_IN_A_MINUTE;
        long hours = minutes / MINUTES_IN_AN_HOUR;

        if (hours > 0) {
            long remainingMinutes = minutes % MINUTES_IN_AN_HOUR;
            return "" + hours + "h " + remainingMinutes + "";
        } else {
            return minutes + "m " + seconds + "s";
        }
    }

    public static String millisToHM(long millis) {
        long minutes = millis / MILLISECONDS_IN_A_MINUTE;
        long hours = minutes / MINUTES_IN_AN_HOUR;

        if (hours > 0) {
            long remainingMinutes = minutes % MINUTES_IN_AN_HOUR;
            String minutesString = remainingMinutes > 0 ? remainingMinutes + " min." : "";
            return hours + "h " + minutesString;
        } else {
            return minutes + " min.";
        }
    }
}
