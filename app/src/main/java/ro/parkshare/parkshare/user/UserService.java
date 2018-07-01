package ro.parkshare.parkshare.user;


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
                        .doOnNext(this::saveAuthentication)
                        .subscribeOn(io());
//        return Observable.just(new UserAuthentication());
    }

    private void saveAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    public Long getCurrentUserId() {
        return userAuthentication.getUserId();
    }
}
