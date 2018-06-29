package ro.parkshare.parkshare.api;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ro.parkshare.parkshare.service.ParkingLocation;
import rx.Observable;

public interface ParkingAPI {
    @GET("parking/user/{userId}")
    Observable<List<ParkingLocation>> getAll(@Path("userId") Long userId);

    @POST("parking")
    Observable<Response<Void>> save(@Body ParkingLocation parkingLocation);
}
