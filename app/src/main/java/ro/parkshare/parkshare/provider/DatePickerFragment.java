package ro.parkshare.parkshare.provider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String TYPE_KEY = "TYPE";
    private static final String START_DATE_KEY = "START_DATE";

    private DatePickerListener.Type type;
    private Date start;

    public static DatePickerFragment instance(DatePickerListener.Type type, Date start) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE_KEY, type);
        bundle.putSerializable(START_DATE_KEY, start);

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (DatePickerListener.Type) getArguments().getSerializable(TYPE_KEY);
        start = (Date) getArguments().getSerializable(START_DATE_KEY);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        c.setTime(start);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        DatePickerListener datePickerListener = (DatePickerListener) getActivity();
        datePickerListener.datePickerSelected(type, year, month, day);
    }
}