package ro.parkshare.parkshare.provider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.ToastHelper;

public class AddOfferActivity extends AppCompatActivity {

    public static final String PARKING_ID = "PARKING_ID";

    @BindView(R.id.from_text)
    TextView from_text;

    @BindView(R.id.to_text)
    TextView to_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add_offer_activity_title);
        setContentView(R.layout.add_offer_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_offer)
    public void onAddOfferButtonClicked() {
        ToastHelper.of(this).show("Works");
    }
}
