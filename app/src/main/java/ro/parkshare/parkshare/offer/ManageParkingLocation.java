package ro.parkshare.parkshare.offer;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.ParkingLocation;
import ro.parkshare.parkshare.service.ParkingService;

public class ManageParkingLocation extends AppCompatActivity implements OnMapReadyCallback {
    public static final String PARKING_ID = "PARKING_ID";

    private EditText name;
    private Button saveButton;
    private GoogleMap map;
    private ParkingLocation parkingLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_parking_location);
        name = findViewById(R.id.name);
        saveButton = findViewById(R.id.save);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(PARKING_ID)) {
            viewMode();
            Long parkingId = extras.getLong(PARKING_ID);
            ParkingService.getInstance()
                    .getParkingLocationsForCurrentUser()
                    .flatMapIterable(x -> x)
                    .filter(p -> p.getId().equals(parkingId))
                    .first()
                    .subscribe(this::displayLocationOnMap, this::onErrorRetrievingParking);
        } else {
            addMode();
        }

        MapFragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
    }

    private void viewMode() {
        setTitle(R.string.manage_parking_edit_title);
        saveButton.setVisibility(ViewGroup.GONE);
    }

    private void addMode() {
        setTitle(R.string.add_parking_location);
    }

    private void onErrorRetrievingParking(Throwable throwable) {
    }

    private void displayLocationOnMap(ParkingLocation parkingLocation) {
        this.parkingLocation = parkingLocation;
        name.setText(parkingLocation.getName());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}
