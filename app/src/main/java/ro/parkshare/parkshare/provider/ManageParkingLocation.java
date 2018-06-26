package ro.parkshare.parkshare.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import ro.parkshare.parkshare.helper.ErrorHelper;
import ro.parkshare.parkshare.service.OffersService;
import ro.parkshare.parkshare.service.ParkingLocation;
import ro.parkshare.parkshare.service.ParkingService;

import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class ManageParkingLocation extends AppCompatActivity implements OnMapReadyCallback {
    public static final String PARKING_ID = "PARKING_ID";
    private static final String TAG = ManageParkingLocation.class.getName();

    private ErrorHelper errorHelper = ErrorHelper.with(this);

    private GoogleMap map;
    private ParkingLocation parkingLocation;
    private OffersAdapter offersAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_parking_location);
        setTitle(R.string.manage_parking_edit_title);

        setUpRecyclerView();
        retrieveParkingLocation();
        setupGoogleMaps();
        View addOfferFab = findViewById(R.id.fab);
        addOfferFab.setOnClickListener(this::onAddOfferFabClickListener);
    }

    private void onAddOfferFabClickListener(View view) {
        Toast.makeText(this, "Fab", Toast.LENGTH_LONG).show();
    }

    private void setupGoogleMaps() {
        SupportMapFragment googleMapFragment = SupportMapFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, googleMapFragment)
                .commit();
        googleMapFragment.getMapAsync(this);
    }

    private void retrieveParkingLocation() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(PARKING_ID)) {
            Long parkingId = extras.getLong(PARKING_ID);
            ParkingService.getInstance()
                    .getParkingLocationById(parkingId)
                    .observeOn(mainThread())
                    .subscribe(this::onLocationRetrieved, this::onErrorRetrievingParking);
        }
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        offersAdapter = new OffersAdapter(this, emptyList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(offersAdapter);
    }

    private void onErrorRetrievingParking(Throwable throwable) {
        String message = throwable.toString();
        Log.e(TAG, message, throwable);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void onLocationRetrieved(ParkingLocation parkingLocation) {
        this.parkingLocation = parkingLocation;

        setNameText(parkingLocation);
        displayOnMap();
        showOffers();
    }

    private void setNameText(ParkingLocation parkingLocation) {
        TextView name = findViewById(R.id.name);
        name.setText(parkingLocation.getName());
    }

    private void showOffers() {
        OffersService.getInstance()
                .getOffersByParkingId(parkingLocation.getId())
                .observeOn(mainThread())
                .subscribe(offersAdapter::replaceData, throwable -> errorHelper.longToast("Error while retrieving offers", throwable));
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
