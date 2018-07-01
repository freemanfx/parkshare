package ro.parkshare.parkshare.api;


import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface UserAPI {

    @POST("user/login")
    Observable<UserAuthentication> login(@Body LoginRequest loginRequest);
}
