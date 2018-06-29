package ro.parkshare.parkshare;

import android.app.Application;

import static ro.parkshare.parkshare.BeanProvider.userService;

public class ParkShareApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BeanProvider.init(this);

        //TODO: call after login
        userService().setCurrentUserId(1L);
    }
}
