package ro.parkshare.parkshare;

import android.annotation.SuppressLint;
import android.content.Context;

import ro.parkshare.parkshare.api.RestClient;
import ro.parkshare.parkshare.helper.DateHelper;
import ro.parkshare.parkshare.helper.ErrorHelper;
import ro.parkshare.parkshare.service.ParkingService;
import ro.parkshare.parkshare.user.UserService;

public class BeanProvider {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static UserService userService;
    private static DateHelper dateHelper;
    private static ErrorHelper errorHelper;
    private static ParkingService parkingService;
    private static ActivityNavigator activityNavigator;

    public static void init(Context context) {
        BeanProvider.context = context;
    }

    public static UserService userService() {
        if (userService == null) {
            userService = new UserService(RestClient.getInstance().userAPI());
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

    public static ParkingService parkingService() {
        if (parkingService == null) {
            parkingService = new ParkingService(RestClient.getInstance().parkingAPI(), userService());
        }
        return parkingService;
    }

    public static ActivityNavigator activityNavigator() {
        if (activityNavigator == null) {
            activityNavigator = new ActivityNavigator(context);
        }
        return activityNavigator;
    }
}
