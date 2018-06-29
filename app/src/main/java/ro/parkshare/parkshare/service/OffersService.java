package ro.parkshare.parkshare.service;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import retrofit2.Response;
import ro.parkshare.parkshare.api.OffersAPI;
import ro.parkshare.parkshare.api.RestClient;
import rx.Observable;

import static rx.schedulers.Schedulers.io;

public class OffersService {
    private static OffersService instance;

    private OffersAPI api;

    private OffersService(OffersAPI api) {
        this.api = api;
    }

    public static OffersService getInstance() {
        if (instance == null) {
            instance = new OffersService(RestClient.getInstance().offersAPI());
        }
        return instance;
    }

    public Observable<List<Offer>> getOffersForBounds(LatLngBounds bounds) {
        //TODO: replace errorHelper proper api call
        Observable<List<Offer>> observable = api
                .getAll()
                .subscribeOn(io());

        return observable;
    }

    public Observable<List<Offer>> getOffersByParkingId(Long parkingId) {
        return api
                .getByParkingId(parkingId)
                .subscribeOn(io());
    }

    public Observable<Void> saveOffer(Offer offer) {
        return api.saveOffer(offer)
                .map(Response::body)
                .subscribeOn(io());
    }
}
