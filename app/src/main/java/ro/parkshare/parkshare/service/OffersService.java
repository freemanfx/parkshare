package ro.parkshare.parkshare.service;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import ro.parkshare.parkshare.api.RestClient;
import rx.Observable;

import static rx.schedulers.Schedulers.io;

public class OffersService {
    private static OffersService instance;

    private OffersService() {
    }

    public static OffersService getInstance() {
        if (instance == null) {
            instance = new OffersService();
        }
        return instance;
    }

    public Observable<List<Offer>> getOffersForBounds(LatLngBounds bounds) {
        Observable<List<Offer>> observable = RestClient
                .getInstance()
                .offersAPI()
                .getAll()
                .subscribeOn(io());

        return observable;
    }
}