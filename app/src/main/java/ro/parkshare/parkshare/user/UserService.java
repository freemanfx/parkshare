package ro.parkshare.parkshare.user;


import rx.Observable;

public class UserService {

    private Long currentUserId;

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Observable<Object> login(String username, String password) {
        return Observable.just(new Object());
    }
}
