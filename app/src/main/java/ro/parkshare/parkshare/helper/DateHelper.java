package ro.parkshare.parkshare.helper;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ro.parkshare.parkshare.Constants;

public class DateHelper {
    private Context context;

    public DateHelper(Context context) {
        this.context = context;
    }

    public String shortFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(Constants.DateFormats.SHORT_OFFER, getCurrentLocale());
        return dateFormat.format(date);
    }

    private Locale getCurrentLocale() {
        return context.getResources().getConfiguration().locale;
    }

    public Calendar getCalendar() {
        Calendar instance = Calendar.getInstance();
        return instance;
    }
}
