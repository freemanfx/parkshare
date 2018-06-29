package ro.parkshare.parkshare.api;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ro.parkshare.parkshare.service.Offer;
import rx.Observable;

public interface OffersAPI {

    @GET("offers/bounds/{swLat}/{swLong}/{neLat}/{neLong}")
    Observable<List<Offer>> getOffersForBounds(
            @Path("swLat") double swLat,
            @Path("swLong") double swLong,
            @Path("neLat") double neLat,
            @Path("neLong") double neLong
    );


    @GET("offers")
    Observable<List<Offer>> getAll();

    @POST("offers")
    Observable<Response<Void>> saveOffer(@Body Offer offer);

    @GET("offers/parking/{parkingId}")
    Observable<List<Offer>> getByParkingId(@Path("parkingId") Long parkingId);
}
