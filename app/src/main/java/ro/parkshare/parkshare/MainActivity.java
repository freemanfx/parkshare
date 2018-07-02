package ro.parkshare.parkshare;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static ro.parkshare.parkshare.BeanProvider.activityNavigator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.my_toolbar));

        Button findMode = findViewById(R.id.findModeButton);
        Button offerMode = findViewById(R.id.offerModeButton);

        findMode.setOnClickListener((View v) -> ActivityNavigator.toFind(this));
        offerMode.setOnClickListener((View v) -> ActivityNavigator.toParkingLocations(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.confirm_logout)
                    .setPositiveButton(R.string.yes, (dialog, i) -> {
                        dialog.dismiss();
                        activityNavigator().toLogin();
                    })
                    .setNegativeButton(R.string.no, (dialog, i) -> {
                        dialog.dismiss();
                    }).show();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
