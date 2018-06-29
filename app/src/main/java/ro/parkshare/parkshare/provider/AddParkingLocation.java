package ro.parkshare.parkshare.provider;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.maps.model.MarkerOptions;

import ro.parkshare.parkshare.ActivityNavigator;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.PermissionHelper;
import ro.parkshare.parkshare.service.ParkingLocation;
import ro.parkshare.parkshare.service.ParkingService;

import static ro.parkshare.parkshare.BeanProvider.userService;
import static ro.parkshare.parkshare.helper.ErrorHelperFactory.errorHelper;
import static ro.parkshare.parkshare.helper.PermissionHelper.RequestCode.LOCATION;

public class AddParkingLocation extends AppCompatActivity implements OnMapReadyCallback {

    private EditText name;
    private Button saveButton;

    private GoogleMap map;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (LOCATION.ordinal() == requestCode) {
            if (PermissionHelper.grantedLocation(permissions, grantResults)) {
                map.setMyLocationEnabled(true);
            }
        }
    }

    private void saveParking(View view) {
        String nameText = name.getText().toString();
        if (!nameText.isEmpty()) {
            storeParking(nameText);
        } else {
            Snackbar.make(saveButton, "Please enter a name for the location", Snackbar.LENGTH_LONG).show();
        }
    }

    private void storeParking(String nameText) {
        LatLng target = map.getCameraPosition().target;
        ParkingLocation parkingLocation = new ParkingLocation(
                nameText,
                target.latitude,
                target.longitude,
                userService().getCurrentUserId()
        );

        ParkingService.getInstance()
                .saveParkingLocation(parkingLocation)
                .subscribe(pl -> ActivityNavigator.toOffer(this), e -> errorHelper(this).longToast(R.string.error_save_location, e));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (PermissionHelper.hasLocation(this)) {
            map.setMyLocationEnabled(true);
        } else {
            PermissionHelper.requestLocation(this);
        }

        UiSettings ui = map.getUiSettings();
        ui.setZoomControlsEnabled(true);
        ui.setMapToolbarEnabled(true);
        ui.setMyLocationButtonEnabled(true);

        map.setOnCameraIdleListener(this::onCameraMoved);
    }

    private void onCameraMoved() {
        map.clear();
        LatLng target = map.getCameraPosition().target;
        map.addMarker(
                new MarkerOptions().position(target)
        );
    }
}
