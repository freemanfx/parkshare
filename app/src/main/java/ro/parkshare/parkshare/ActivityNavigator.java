package ro.parkshare.parkshare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import ro.parkshare.parkshare.consumer.CarParkedActivity;
import ro.parkshare.parkshare.consumer.FindMapsActivity;
import ro.parkshare.parkshare.provider.AddOfferActivity;
import ro.parkshare.parkshare.provider.AddParkingLocation;
import ro.parkshare.parkshare.provider.ManageParkingLocation;
import ro.parkshare.parkshare.provider.ParkingLocationsActivity;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.ParkingLocation;


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
        Intent intent = new Intent(activity, AddParkingLocation.class);
        activity.startActivity(intent);
    }

    public static void manageLocation(AppCompatActivity activity, ParkingLocation parkingLocation) {
        Intent intent = new Intent(activity, ManageParkingLocation.class);
        intent.putExtra(ManageParkingLocation.PARKING_ID, parkingLocation.getId());
        activity.startActivity(intent);
    }

    public static void addOfferForLocation(AppCompatActivity activity, ParkingLocation parkingLocation) {
        Intent intent = new Intent(activity, AddOfferActivity.class);
        intent.putExtra(AddOfferActivity.PARKING_ID, parkingLocation.getId());
        activity.startActivity(intent);
    }

    public static void carParked(Context context, Offer offer) {
        Intent intent = new Intent(context, CarParkedActivity.class);
        intent.putExtra(CarParkedActivity.OFFER, offer);
        context.startActivity(intent);
    }
}
