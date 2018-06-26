package ro.parkshare.parkshare.consumer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.MapHelper;
import ro.parkshare.parkshare.helper.PermissionHelper;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.OffersService;

import static ro.parkshare.parkshare.helper.PermissionHelper.RequestCode.LOCATION;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class FindMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = FindMapsActivity.class.getName();
    private static final String TAKE_OFFER_DIALOG_TAG = "TakeOfferDialog";

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

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (LOCATION.ordinal() == requestCode) {
            if (PermissionHelper.grantedLocation(permissions, grantResults)) {
                map.setMyLocationEnabled(true);
            }
        }
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
        MapHelper.displayOffers(offers, map)
                .subscribe(marker -> {

                }, this::onAddMarkerError);
    }

    private void onAddMarkerError(Throwable throwable) {
        Log.e(TAG, getString(R.string.error_adding_marker), throwable);
    }

    @SuppressLint("MissingPermission")
    private void setupMap() {
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        map.setInfoWindowAdapter(new MarkerInfoWindowAdapter(this));
        map.setOnInfoWindowClickListener(this::onInfoWindowClick);
        map.setOnMarkerClickListener(this::onMarkerClick);

        if (PermissionHelper.hasLocation(this)) {
            map.setMyLocationEnabled(true);
        } else {
            PermissionHelper.requestLocation(this);
        }
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


    public void onInfoWindowClick(Marker marker) {
        TakeOfferDialog takeOfferDialog = new TakeOfferDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable(TakeOfferDialog.OFFER_KEY, (Offer) marker.getTag());
        takeOfferDialog.setArguments(bundle);

        takeOfferDialog.show(getSupportFragmentManager(), TAKE_OFFER_DIALOG_TAG);
    }

    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }
}
