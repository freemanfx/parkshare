package ro.parkshare.parkshare;

import android.annotation.SuppressLint;
import android.content.Context;

import ro.parkshare.parkshare.user.UserService;

public class BeanProvider {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static UserService userService;

    public static void init(Context context) {
        BeanProvider.context = context;
    }

    public static UserService userService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
}
