package ro.parkshare.parkshare.offer;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ro.parkshare.parkshare.ActivityNavigator;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.ParkingLocation;

public class AddParkingLocation extends AppCompatActivity implements OnMapReadyCallback {

    private EditText name;
    private Button saveButton;

    private GoogleMap map;
    private Marker currentPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking_location);
        setTitle(R.string.add_parking_location);

        name = findViewById(R.id.name);
        saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(this::saveParking);

        MapFragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
    }

    private void saveParking(View view) {
        String nameText = name.getText().toString();
        if (!nameText.isEmpty()) {
            storeParking(nameText);
            ActivityNavigator.toOffer(this);
        } else {
            Snackbar.make(saveButton, "Please enter a name for the location", Snackbar.LENGTH_LONG).show();
        }
    }

    private void storeParking(String nameText) {
        LatLng target = map.getCameraPosition().target;
        ParkingLocation parkingLocation = new ParkingLocation();
        parkingLocation.setName(nameText);
        parkingLocation.setLatitude(target.latitude);
        parkingLocation.setLatitude(target.longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings ui = map.getUiSettings();
        ui.setZoomControlsEnabled(true);

        map.setOnCameraIdleListener(this::onCameraMoved);
    }

    private void onCameraMoved() {
        map.clear();
        LatLng target = map.getCameraPosition().target;
        currentPosition = map.addMarker(
                new MarkerOptions().position(target)
        );
    }
}
