package ro.parkshare.parkshare.provider;

public interface TimePickerListener {
    void timePickerSelected(TimeType type, int hourOfDay, int minute);

    enum TimeType {
        START,
        END
    }
}
