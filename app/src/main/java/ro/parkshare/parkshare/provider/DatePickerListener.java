package ro.parkshare.parkshare.provider;

public interface DatePickerListener {
    void datePickerSelected(Type type, int year, int month, int day);

    enum Type {
        TYPE_DATE_FROM,
        TYPE_DATE_TO
    }
}
