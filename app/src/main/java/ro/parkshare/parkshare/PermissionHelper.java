package ro.parkshare.parkshare;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class PermissionHelper {

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
    };

    public static boolean grantedLocation(String[] permissions, int[] grantResults) {
        List<String> expected = Arrays.asList(LOCATION_PERMISSIONS);
        List<String> actual = Arrays.asList(permissions);
        boolean samePermissions = actual.containsAll(expected);
        boolean grantedResults = Arrays.asList(grantResults).contains(PERMISSION_GRANTED);

        return samePermissions && grantedResults;
    }

    public static boolean hasLocation(Context context) {
        boolean hasFineLocation = checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED;
        return hasFineLocation;
    }

    public static void requestLocation(Activity activity) {
        ActivityCompat.requestPermissions(activity, LOCATION_PERMISSIONS, RequestCode.LOCATION.ordinal());
    }

    public enum RequestCode {
        LOCATION
    }
}
