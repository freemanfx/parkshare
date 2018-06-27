package ro.parkshare.parkshare.consumer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.Offer;

public class CarParkedActivity extends AppCompatActivity {

    public static String OFFER = "OFFER";

    private Offer offer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_parked);
        setTitle(R.string.your_car_parked_here);

        offer = (Offer) getIntent().getSerializableExtra(OFFER);
    }
}
