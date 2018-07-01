package ro.parkshare.parkshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button findMode = findViewById(R.id.findModeButton);
        Button offerMode = findViewById(R.id.offerModeButton);

        findMode.setOnClickListener((View v) -> ActivityNavigator.toFind(this));
        offerMode.setOnClickListener((View v) -> ActivityNavigator.toParkingLocations(this));
    }
}
