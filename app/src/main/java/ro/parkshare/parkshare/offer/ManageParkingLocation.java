package ro.parkshare.parkshare.offer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ro.parkshare.parkshare.R;

public class ManageParkingLocation extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_parking_location);

        setTitle(getString(R.string.add_parking_location));
    }
}
