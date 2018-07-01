package ro.parkshare.parkshare.provider;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import ro.parkshare.parkshare.provider.TimePickerListener.TimeType;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public static final boolean IS_24_HOUR_VIEW = true;
    private static final String TYPE_KEY = "TYPE";
    private static final String START_DATE_KEY = "START_DATE";
    private TimeType type;
    private Date start;

    public static TimePickerFragment instance(TimeType type, Date start) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE_KEY, type);
        bundle.putSerializable(START_DATE_KEY, start);

        TimePickerFragment datePickerFragment = new TimePickerFragment();
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (TimeType) getArguments().getSerializable(TYPE_KEY);
        start = (Date) getArguments().getSerializable(START_DATE_KEY);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        c.setTime(start);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, IS_24_HOUR_VIEW);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TimePickerListener timePickerListener = (TimePickerListener) getActivity();
        timePickerListener.timePickerSelected(type, hourOfDay, minute);
    }
}