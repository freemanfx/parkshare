package ro.parkshare.parkshare.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.parkshare.parkshare.ActivityNavigator;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.ParkingLocation;
import rx.Subscription;

import static ro.parkshare.parkshare.BeanProvider.parkingService;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class ParkingLocationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ParkingLocationsAdapter adapter;
    private Subscription parkingLocationsSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.parking_locations_activity_title));
        setContentView(R.layout.activity_parking_locations);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::addNewParkingLocation);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ParkingLocationsAdapter(new ArrayList<>(), this::onItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parkingLocationsSubscription = parkingService()
                .getParkingLocationsForCurrentUser()
                .observeOn(mainThread())
                .subscribe(this::displayParkingLocations, this::onErrorParkingLocations);
    }

    @Override
    protected void onPause() {
        super.onPause();
        parkingLocationsSubscription.unsubscribe();
    }

    private void addNewParkingLocation(View view) {
        ActivityNavigator.addNewLocationActivity(this);
    }

    private void onItemClickListener(ParkingLocation parkingLocation) {
        ActivityNavigator.manageLocation(this, parkingLocation);
    }

    private void displayParkingLocations(List<ParkingLocation> parkingLocations) {
        adapter.replaceData(parkingLocations);
    }

    private void onErrorParkingLocations(Throwable throwable) {
        String message = getString(R.string.error_retrieve_parking_locations) + throwable.toString();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
