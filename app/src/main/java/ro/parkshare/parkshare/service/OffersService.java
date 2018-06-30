package ro.parkshare.parkshare.service;

import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import ro.parkshare.parkshare.api.ApiException;
import ro.parkshare.parkshare.api.ApiResponse;
import ro.parkshare.parkshare.api.OffersAPI;
import ro.parkshare.parkshare.api.RestClient;
import rx.Observable;

import static ro.parkshare.parkshare.BeanProvider.userService;
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
        return api
                .getOffersForBounds(
                        bounds.southwest.latitude,
                        bounds.southwest.longitude,
                        bounds.northeast.latitude,
                        bounds.northeast.longitude)
                .subscribeOn(io());
    }

    public Observable<List<Offer>> getOffersByParkingId(Long parkingId) {
        return api
                .getByParkingId(parkingId)
                .subscribeOn(io());
    }

    public Observable<Response<Void>> addOffer(Offer offer) {
        return api.addOffer(offer)
                .subscribeOn(io());
    }

    public Observable<Response<ApiResponse>> bookOffer(Offer offer) {
        return api.bookOffer(offer.getId(), userService().getCurrentUserId())
                .map(this::throwExceptionIfUnsuccessful)
                .subscribeOn(io());
    }

    private Response<ApiResponse> throwExceptionIfUnsuccessful(Response<ApiResponse> response) throws ApiException {
        try {
            if (!response.isSuccessful()) {
                String errorBody = response.errorBody().string();
                ApiResponse apiResponse = ApiResponse.fromBody(errorBody);
                throw new ApiException(apiResponse.getMessage());
            }
            return response;
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }
}
