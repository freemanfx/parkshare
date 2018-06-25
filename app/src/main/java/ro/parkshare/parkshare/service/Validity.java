package ro.parkshare.parkshare.service;

import java.util.Date;

public class Validity {
    private static final int MILLIS_IN_A_MINUTE = 1000 * 60;

    private Date start;
    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int minutes() {
        Date currentDate = new Date();
        boolean currentAfterEnd = currentDate.after(end);
        boolean currentAfterStart = currentDate.after(start);

        if (currentAfterEnd) {
            return 0;
        } else if (currentAfterStart) {
            return millisToMinutes(end.getTime() - currentDate.getTime());
        } else {
            return millisToMinutes(end.getTime() - start.getTime());
        }
    }

    private int millisToMinutes(long milliseconds) {
        return (int) milliseconds / MILLIS_IN_A_MINUTE;
    }
}