package ro.parkshare.parkshare;

import android.annotation.SuppressLint;
import android.content.Context;

import ro.parkshare.parkshare.helper.DateHelper;
import ro.parkshare.parkshare.helper.ErrorHelper;
import ro.parkshare.parkshare.user.UserService;

public class BeanProvider {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static UserService userService;
    private static DateHelper dateHelper;
    private static ErrorHelper errorHelper;

    public static void init(Context context) {
        BeanProvider.context = context;
    }

    public static UserService userService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public static DateHelper dateHelper() {
        if (dateHelper == null) {
            dateHelper = new DateHelper(context);
        }
        return dateHelper;
    }

    public static ErrorHelper errorHelper() {
        if (errorHelper == null) {
            errorHelper = new ErrorHelper(context);
        }
        return errorHelper;
    }
}
