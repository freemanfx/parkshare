package ro.parkshare.parkshare.map;

import android.content.Context;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.Parking;
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
        Parking parking = offer.getParking();
        return new MarkerOptions()
                .title(markerTitle(offer))
                .position(parking.getLatLang());
    }

    private String markerTitle(Offer offer) {
        int minutes = offer.getValidity().minutes();
        if (minutes > 0) {
            return context.getString(R.string.parking_validity_title, minutes);
        } else {
            return context.getString(R.string.expired);
        }
    }
}
