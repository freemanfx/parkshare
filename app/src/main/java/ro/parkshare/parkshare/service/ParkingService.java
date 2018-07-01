package ro.parkshare.parkshare.service;

import java.util.List;

import retrofit2.Response;
import ro.parkshare.parkshare.api.ParkingAPI;
import ro.parkshare.parkshare.user.UserService;
import rx.Observable;

import static rx.schedulers.Schedulers.io;

public class ParkingService {
    private final UserService userService;
    private final ParkingAPI api;

    public ParkingService(ParkingAPI api, UserService userService) {
        this.api = api;
        this.userService = userService;
    }

    private Observable<List<ParkingLocation>> getAllParkingLocations(Long userId) {
        return api
                .getAll(userId)
                .subscribeOn(io());
    }

    public Observable<List<ParkingLocation>> getParkingLocationsForCurrentUser() {
        return getAllParkingLocations(userService.getCurrentUserId());
    }

    public Observable<ParkingLocation> getParkingLocationById(Long parkingId) {
        return getParkingLocationsForCurrentUser()
                .flatMapIterable(x -> x)
                .filter(p -> p.getId().equals(parkingId))
                .first();
    }

    public Observable<Response<Void>> saveParkingLocation(ParkingLocation parkingLocation) {
        return api.save(parkingLocation)
                .subscribeOn(io());
    }
}
