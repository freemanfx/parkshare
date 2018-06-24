package ro.parkshare.parkshare.find;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.map.MapMarkerFactory;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.OffersService;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class FindMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setupMap();
        setMapMoveListener();
    }

    private void setMapMoveListener() {
        map.setOnCameraIdleListener(() -> {
            LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
            OffersService.getInstance()
                    .getOffersForBounds(bounds)
                    .observeOn(mainThread())
                    .subscribe(this::displayOffers, this::onGetOffersError);
        });
    }

    private void onGetOffersError(Throwable throwable) {
        String message = getString(R.string.error_retrieve_offers) + throwable.toString();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void displayOffers(List<Offer> offers) {
        map.clear();
        MapMarkerFactory.getInstance(this)
                .fromOffers(offers)
                .observeOn(mainThread())
                .subscribe(map::addMarker);
    }

    private void setupMap() {
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        setInitialPoint();

        UiSettings ui = map.getUiSettings();
        ui.setMapToolbarEnabled(true);
        ui.setZoomControlsEnabled(true);
    }

    private void setInitialPoint() {
        LatLng iasi = new LatLng(47.1731649, 27.5663222);
        LatLng bucuresti = new LatLng(44.4520456, 26.0853351);
        float zoomLevel = 19.0f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(bucuresti, zoomLevel);
        map.moveCamera(cameraUpdate);
    }
}
