package ro.parkshare.parkshare.api;


import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface UserAPI {

    @POST("user/login")
    Observable<Response<UserAuthentication>> login(@Body LoginRequest loginRequest);
}
