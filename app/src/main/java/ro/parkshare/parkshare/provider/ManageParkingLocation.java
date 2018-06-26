package ro.parkshare.parkshare.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.MarkerOptions;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.ParkingLocation;
import ro.parkshare.parkshare.service.ParkingService;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class ManageParkingLocation extends AppCompatActivity implements OnMapReadyCallback {
    public static final String PARKING_ID = "PARKING_ID";
    private static final String TAG = ManageParkingLocation.class.getName();

    private TextView name;
    private GoogleMap map;
    private ParkingLocation parkingLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_parking_location);
        setTitle(R.string.manage_parking_edit_title);

        name = findViewById(R.id.name);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(PARKING_ID)) {
            Long parkingId = extras.getLong(PARKING_ID);
            ParkingService.getInstance()
                    .getParkingLocationById(parkingId)
                    .observeOn(mainThread())
                    .subscribe(this::onLocationRetrieved, this::onErrorRetrievingParking);
        }

        SupportMapFragment googleMapFragment = SupportMapFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, googleMapFragment)
                .commit();
        googleMapFragment.getMapAsync(this);
    }

    private void onErrorRetrievingParking(Throwable throwable) {
        String message = throwable.toString();
        Log.e(TAG, message, throwable);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void onLocationRetrieved(ParkingLocation parkingLocation) {
        name.setText(parkingLocation.getName());
        this.parkingLocation = parkingLocation;

        displayOnMap();
        addOffersFragment();
    }

    private void addOffersFragment() {
        OffersManagementFragment omf = OffersManagementFragment.newInstance(parkingLocation.getId());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.offers_container, omf)
                .commit();
    }

    private void displayOnMap() {
        if (map != null && parkingLocation != null) {
            MarkerOptions markerOptions = new MarkerOptions().position(parkingLocation.getLatLang());
            map.addMarker(markerOptions);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(parkingLocation.getLatLang(), 20.0f);
            map.moveCamera(cameraUpdate);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings ui = map.getUiSettings();
        ui.setAllGesturesEnabled(false);
        ui.setZoomControlsEnabled(true);

        displayOnMap();
    }
}
