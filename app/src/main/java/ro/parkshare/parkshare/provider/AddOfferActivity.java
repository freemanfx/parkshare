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
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.DateHelper;
import ro.parkshare.parkshare.helper.ToastHelper;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.OffersService;
import ro.parkshare.parkshare.service.ParkingLocation;
import ro.parkshare.parkshare.service.ParkingService;
import ro.parkshare.parkshare.service.Validity;

import static ro.parkshare.parkshare.helper.ErrorHelperFactory.errorHelper;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class AddOfferActivity extends AppCompatActivity {

    public static final String PARKING_ID = "PARKING_ID";

    @BindView(R.id.from_text)
    TextView from_text;

    @BindView(R.id.to_text)
    TextView to_text;

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

        Calendar calendar = Calendar.getInstance();
        start = calendar.getTime();

        calendar.add(Calendar.HOUR, 1);
        end = calendar.getTime();

        DateHelper dateHelper = new DateHelper(this);
        from_text.setText(dateHelper.shortFormat(start));
        to_text.setText(dateHelper.shortFormat(end));

        Validity validity = new Validity(start, end);
        String durationString = getResources().getString(R.string.amount_minutes_format, validity.minutes());
        duration_text.setText(durationString);


        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(PARKING_ID)) {
            Long parkingId = extras.getLong(PARKING_ID);
            ParkingService.getInstance()
                    .getParkingLocationById(parkingId)
                    .observeOn(mainThread())
                    .subscribe(
                            this::onLocationRetrieved,
                            e -> errorHelper(this).longToast(R.string.error_retrieve_data, e)
                    );
        }

    }

    private void onLocationRetrieved(ParkingLocation parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    @OnClick(R.id.add_offer)
    public void onAddOfferButtonClicked() {
        Offer offer = new Offer(parkingLocation, new Validity(start, end));
        OffersService.getInstance()
                .saveOffer(offer)
                .observeOn(mainThread())
                .subscribe(this::offerSaved,
                        e -> errorHelper(this).longToast(R.string.error_sending_data, e));
    }

    private void offerSaved(Offer offer) {
        ToastHelper.of(this).show(getString(R.string.offer_added));
        finish();
    }
}