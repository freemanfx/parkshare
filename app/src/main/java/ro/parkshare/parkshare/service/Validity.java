package ro.parkshare.parkshare.service;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

public class Validity implements Serializable {
    private static final int MILLIS_IN_A_MINUTE = 1000 * 60;

    @Expose
    private Date start;
    @Expose
    private Date end;

    private Validity() {
    }

    public Validity(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

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

    public long minutes() {
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

    private long millisToMinutes(long milliseconds) {
        return milliseconds / MILLIS_IN_A_MINUTE;
    }
}
