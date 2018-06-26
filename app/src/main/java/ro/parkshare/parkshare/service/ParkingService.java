package ro.parkshare.parkshare.service;

import java.util.List;

import ro.parkshare.parkshare.api.RestClient;
import rx.Observable;

import static rx.schedulers.Schedulers.io;

public class ParkingService {
    private static ParkingService instance;

    private ParkingService() {
    }

    public static ParkingService getInstance() {
        if (instance == null) {
            instance = new ParkingService();
        }
        return instance;
    }

    private Observable<List<ParkingLocation>> getAllParkingLocations(Long userId) {
        Observable<List<ParkingLocation>> observable = RestClient
                .getInstance()
                .parkingAPI()
                .getAll(userId)
                .subscribeOn(io());

        return observable;
    }

    public Observable<List<ParkingLocation>> getParkingLocationsForCurrentUser() {
        long userId = 1L;
        return getAllParkingLocations(userId);
    }

    public Observable<ParkingLocation> getParkingLocationById(Long parkingId) {
        return ParkingService.getInstance()
                .getParkingLocationsForCurrentUser()
                .flatMapIterable(x -> x)
                .filter(p -> p.getId().equals(parkingId))
                .first();
    }

    public Observable<Offer> bookOffer(Offer offer) {
        //TODO: make api call
        return Observable
                .just(offer)
                .subscribeOn(io());
    }
}
