package ro.parkshare.parkshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import ro.parkshare.parkshare.find.FindMapsActivity;
import ro.parkshare.parkshare.offer.ManageParkingLocation;
import ro.parkshare.parkshare.offer.ParkingLocationsActivity;


public class ActivityNavigator {
    public static void toFind(AppCompatActivity activity) {
        startActivity(activity, FindMapsActivity.class);
    }

    public static void toOffer(AppCompatActivity activity) {
        startActivity(activity, ParkingLocationsActivity.class);
    }

    private static void startActivity(AppCompatActivity activity, Class<?> activityClass) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }

    public static void addNewLocationActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, ManageParkingLocation.class);
        activity.startActivity(intent);
    }
}
