package ro.parkshare.parkshare.helper;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    private Context context;

    public DateHelper(Context context) {
        this.context = context;
    }

    public String shortFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM hh:mm", getCurrentLocale());
        return dateFormat.format(date);
    }

    private Locale getCurrentLocale() {
        return context.getResources().getConfiguration().locale;
    }
}
