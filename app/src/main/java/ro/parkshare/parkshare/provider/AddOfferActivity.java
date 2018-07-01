package ro.parkshare.parkshare.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.ToastHelper;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.OffersService;
import ro.parkshare.parkshare.service.ParkingLocation;
import ro.parkshare.parkshare.service.Validity;

import static ro.parkshare.parkshare.BeanProvider.dateHelper;
import static ro.parkshare.parkshare.BeanProvider.errorHelper;
import static ro.parkshare.parkshare.BeanProvider.parkingService;
import static ro.parkshare.parkshare.provider.DatePickerFragment.instance;
import static ro.parkshare.parkshare.provider.DatePickerListener.Type.TYPE_DATE_FROM;
import static ro.parkshare.parkshare.provider.DatePickerListener.Type.TYPE_DATE_TO;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class AddOfferActivity extends AppCompatActivity implements DatePickerListener {

    public static final String PARKING_ID = "PARKING_ID";

    @BindView(R.id.from_date_text)
    TextView from_date_text;
    @BindView(R.id.from_time_text)
    TextView from_time_text;

    @BindView(R.id.to_date_text)
    TextView to_date_text;
    @BindView(R.id.to_time_text)
    TextView to_time_text;

    @BindView(R.id.duration_text)
    TextView duration_text;

    private ParkingLocation parkingLocation;
    private Date start;
    private Date end;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add_offer_activity_title);
        setContentView(R.layout.add_offer_activity);
        ButterKnife.bind(this);

        Calendar calendar = dateHelper().getCalendar();
        start = calendar.getTime();

        calendar.add(Calendar.HOUR, 1);
        end = calendar.getTime();

        updateDateTimeFields();

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(PARKING_ID)) {
            Long parkingId = extras.getLong(PARKING_ID);
            parkingService()
                    .getParkingLocationById(parkingId)
                    .observeOn(mainThread())
                    .subscribe(
                            this::onLocationRetrieved,
                            e -> errorHelper().longToast(R.string.error_retrieve_data, e)
                    );
        }

    }

    private void updateDateTimeFields() {
        from_date_text.setText(dateHelper().justDate(start));
        from_time_text.setText(dateHelper().justTime(start));

        to_date_text.setText(dateHelper().justDate(end));
        to_time_text.setText(dateHelper().justTime(end));

        Validity validity = new Validity(start, end);
        String durationString = getResources().getString(R.string.amount_minutes_format, validity.minutes());
        duration_text.setText(durationString);
    }

    private void onLocationRetrieved(ParkingLocation parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    @OnClick(R.id.add_offer)
    public void onAddOfferButtonClicked() {
        Offer offer = new Offer(parkingLocation, new Validity(start, end));
        OffersService.getInstance()
                .addOffer(offer)
                .observeOn(mainThread())
                .subscribe(this::offerSaved, this::onAddOfferError);
    }

    private void offerSaved(Response<Void> response) {
        ToastHelper.of(this).show(getString(R.string.offer_added));
        finish();
    }

    private void onAddOfferError(Throwable throwable) {
        errorHelper().longToast(R.string.error_sending_data, throwable);
    }

    @OnClick(R.id.from_date_text)
    public void onFromDateClicked() {
        DatePickerFragment instance = instance(TYPE_DATE_FROM, start);
        instance.show(getSupportFragmentManager(), "FROM_DATE");
    }

    @OnClick(R.id.to_date_text)
    public void onToDateClicked() {
        DatePickerFragment instance = instance(TYPE_DATE_TO, start);
        instance.show(getSupportFragmentManager(), "TO_DATE");
    }

    @Override
    public void datePickerSelected(Type type, int year, int month, int day) {
        if (type == TYPE_DATE_FROM) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.set(year, month, day);

            start = calendar.getTime();
        }

        if (type == TYPE_DATE_TO) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.set(year, month, day);

            end = calendar.getTime();
        }

        updateDateTimeFields();
    }
}
