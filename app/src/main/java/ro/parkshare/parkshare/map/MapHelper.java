package ro.parkshare.parkshare.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.ParkingLocation;
import rx.Observable;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class MapHelper {

    private static MarkerOptions offerToMarker(Offer offer) {
        ParkingLocation parkingLocation = offer.getParking();
        return new MarkerOptions()
                .position(parkingLocation.getLatLang());
    }


    public static Observable<Marker> displayOffers(List<Offer> offers, GoogleMap map) {
        return Observable.from(offers)
                .map(offer -> {
                    MarkerOptions markerOptions = offerToMarker(offer);
                    Marker marker = map.addMarker(markerOptions);
                    marker.setTag(offer);
                    return marker;
                })
                .subscribeOn(mainThread())
                .observeOn(mainThread());
    }
}
