package ro.parkshare.parkshare.map;

import android.content.Context;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.ParkingLocation;
import rx.Observable;

public class MapMarkerFactory {

    private static MapMarkerFactory instance;
    private final Context context;

    private MapMarkerFactory(Context context) {
        this.context = context;
    }

    public static MapMarkerFactory getInstance(Context context) {
        if (instance == null) {
            instance = new MapMarkerFactory(context);
        }
        return instance;
    }

    public Observable<MarkerOptions> fromOffers(List<Offer> offers) {
        return Observable.from(offers)
                .map(this::offerToMarker);
    }

    private MarkerOptions offerToMarker(Offer offer) {
        ParkingLocation parkingLocation = offer.getParking();
        return new MarkerOptions()
                .position(parkingLocation.getLatLang());
    }
}
