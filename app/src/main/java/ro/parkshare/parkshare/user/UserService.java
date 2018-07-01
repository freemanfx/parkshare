package ro.parkshare.parkshare.user;


import java.io.IOException;

import retrofit2.Response;
import ro.parkshare.parkshare.api.ApiException;
import ro.parkshare.parkshare.api.ApiResponse;
import ro.parkshare.parkshare.api.LoginRequest;
import ro.parkshare.parkshare.api.UserAPI;
import ro.parkshare.parkshare.api.UserAuthentication;
import rx.Observable;

import static rx.Observable.fromCallable;
import static rx.schedulers.Schedulers.io;

public class UserService {

    private final UserAPI userAPI;

    private UserAuthentication userAuthentication = new UserAuthentication(1L, "auth-token");

    public UserService(UserAPI userAPI) {
        this.userAPI = userAPI;
    }

    public Observable<UserAuthentication> login(String username, String password) {
        Observable<LoginRequest> loginObservable = fromCallable(() -> new LoginRequest(username, password));

        return
                loginObservable.flatMap(userAPI::login)
                        .map(this::throwExceptionIfUnsuccessful)
                        .map(Response::body)
                        .doOnNext(this::saveAuthentication)
                        .subscribeOn(io());
    }

    private Response<UserAuthentication> throwExceptionIfUnsuccessful(Response<UserAuthentication> response) throws ApiException {
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

    private void saveAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    public Long getCurrentUserId() {
        return userAuthentication.getUserId();
    }

    public String getAuthToken() {
        return userAuthentication.getAuthToken();
    }
}
