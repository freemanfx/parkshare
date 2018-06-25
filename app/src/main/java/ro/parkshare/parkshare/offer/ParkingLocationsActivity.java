package ro.parkshare.parkshare.offer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.ParkingLocation;

public class ParkingLocationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ParkingLocationsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_locations);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ParkingLocationsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

//        ParkingService.getInstance()
//                .getParkingLocationsForCurrentUser()
//                .observeOn(mainThread())
//                .subscribe(this::displayParkingLocations, this::onErrorParkingLocations);
    }

    private void displayParkingLocations(List<ParkingLocation> parkingLocations) {
        adapter.replaceData(parkingLocations);
    }

    private void onErrorParkingLocations(Throwable throwable) {
        String message = getString(R.string.error_retrieve_parking_locations) + throwable.toString();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
