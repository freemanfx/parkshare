package ro.parkshare.parkshare.consumer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.MapHelper;
import ro.parkshare.parkshare.helper.PermissionHelper;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.OffersService;

import static ro.parkshare.parkshare.Constants.INITIAL_POSITION;
import static ro.parkshare.parkshare.helper.ErrorHelperFactory.errorHelper;
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
                    .subscribe(this::displayOffers, throwable -> errorHelper(this).longToast(R.string.error_retrieve_offers, throwable));
        });
    }

    private void displayOffers(List<Offer> offers) {
        map.clear();
        MapHelper.displayOffers(offers, map)
                .subscribe(marker -> {
                        },
                        throwable -> errorHelper(this).longToast(R.string.error_adding_marker, throwable));
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
        float zoomLevel = 19.0f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(INITIAL_POSITION, zoomLevel);
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
