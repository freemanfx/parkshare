package ro.parkshare.parkshare;

import android.app.Application;

public class ParkShareApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BeanProvider.init(this);
    }
}
