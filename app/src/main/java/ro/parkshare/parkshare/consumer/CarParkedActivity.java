package ro.parkshare.parkshare.consumer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.DateHelper;
import ro.parkshare.parkshare.helper.DurationHelper;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.ParkingLocation;
import rx.Subscription;

import static rx.Observable.interval;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class CarParkedActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static String OFFER = "OFFER";

    private static final String PARKING_START_DATE = "PARKING_START_DATE";
    private static final int INITIAL_DELAY = 0;
    private static final int PERIOD = 1;

    private Offer offer;
    private GoogleMap map;
    private Date startDate;

    @BindView(R.id.parking_duration_text)
    TextView parkingDuration;
    @BindView(R.id.starting_text)
    TextView startingText;

    private Subscription updateDurationSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_parked);
        setTitle(R.string.your_car_parked_here);
        ButterKnife.bind(this);

        offer = (Offer) getIntent().getSerializableExtra(OFFER);

        startDate = new Date();
        if (savedInstanceState != null) {
            startDate = (Date) savedInstanceState.getSerializable(PARKING_START_DATE);
        }

        DateHelper dateHelper = new DateHelper(this);
        startingText.setText(dateHelper.shortFormat(startDate));

        long duration = new Date().getTime() - startDate.getTime();
        parkingDuration.setText(DurationHelper.millisToString(duration));

        setupGoogleMaps();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDurationEveryMinute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateDurationSubscription.unsubscribe();
    }

    public void updateDurationEveryMinute() {
        updateDurationSubscription = interval(INITIAL_DELAY, PERIOD, TimeUnit.SECONDS)
                .observeOn(mainThread())
                .subscribe(aLong -> {
                    long duration = new Date().getTime() - startDate.getTime();
                    parkingDuration.setText(DurationHelper.millisToString(duration));
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(PARKING_START_DATE, startDate);
    }

    private void setupGoogleMaps() {
        SupportMapFragment googleMapFragment = SupportMapFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, googleMapFragment)
                .commit();
        googleMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings ui = map.getUiSettings();
        ui.setAllGesturesEnabled(false);
        ui.setZoomControlsEnabled(true);

        ParkingLocation parkingLocation = offer.getParking();
        MarkerOptions markerOptions = new MarkerOptions().position(parkingLocation.getLatLang());
        map.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(parkingLocation.getLatLang(), 20.0f);
        map.moveCamera(cameraUpdate);
    }

    @OnClick(R.id.leave_parking)
    public void onLeaveParking() {
        LeaveParkingDialog leaveParkingDialog = LeaveParkingDialog.newInstance(offer);
        leaveParkingDialog.show(getSupportFragmentManager(), "LeaveParkingDialog");
    }

    @Override
    public void onBackPressed() {
        onLeaveParking();
    }
}
