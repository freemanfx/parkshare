package ro.parkshare.parkshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import ro.parkshare.parkshare.find.FindMapsActivity;


public class ActivityNavigator {
    public static void toFind(AppCompatActivity activity) {
        startActivity(activity, FindMapsActivity.class);
    }

    public static void toOffer(AppCompatActivity activity) {

    }

    private static void startActivity(AppCompatActivity activity, Class<?> activityClass) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }
}
