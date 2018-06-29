package ro.parkshare.parkshare.service;

import java.util.List;

import retrofit2.Response;
import ro.parkshare.parkshare.api.ParkingAPI;
import ro.parkshare.parkshare.api.RestClient;
import rx.Observable;

import static rx.schedulers.Schedulers.io;

public class ParkingService {
    private static ParkingService instance;
    private ParkingAPI api;

    private ParkingService() {
        api = RestClient
                .getInstance()
                .parkingAPI();
    }

    public static ParkingService getInstance() {
        if (instance == null) {
            instance = new ParkingService();
        }
        return instance;
    }

    private Observable<List<ParkingLocation>> getAllParkingLocations(Long userId) {
        return api
                .getAll(userId)
                .subscribeOn(io());
    }

    public Observable<List<ParkingLocation>> getParkingLocationsForCurrentUser() {
        long userId = 1L;
        return getAllParkingLocations(userId);
    }

    public Observable<ParkingLocation> getParkingLocationById(Long parkingId) {
        return getParkingLocationsForCurrentUser()
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

    public Observable<Response<Void>> saveParkingLocation(ParkingLocation parkingLocation) {
        return api.save(parkingLocation)
                .subscribeOn(io());
    }
}
