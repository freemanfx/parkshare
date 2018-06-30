package ro.parkshare.parkshare.consumer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import ro.parkshare.parkshare.ActivityNavigator;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.api.ApiResponse;
import ro.parkshare.parkshare.helper.DateHelper;
import ro.parkshare.parkshare.helper.ToastHelper;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.OffersService;
import ro.parkshare.parkshare.service.Validity;

import static ro.parkshare.parkshare.helper.ErrorHelperFactory.errorHelper;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class TakeOfferDialog extends DialogFragment {
    public static final String OFFER_KEY = "OFFER";

    @BindView(R.id.from_text)
    TextView from;
    @BindView(R.id.to_text)
    TextView to;
    private Offer offer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_take_offer, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(OFFER_KEY)) {
            offer = (Offer) bundle.getSerializable(OFFER_KEY);

            Validity validity = offer.getValidity();
            DateHelper dateHelper = new DateHelper(getContext());
            from.setText(dateHelper.shortFormat(validity.getStart()));
            to.setText(dateHelper.shortFormat(validity.getEnd()));
        }

        return view;
    }

    @OnClick(R.id.cancel)
    public void onCancelClick() {
        dismiss();
    }

    @OnClick(R.id.park_here)
    public void onParkHereClick() {
        OffersService.getInstance()
                .bookOffer(offer)
                .observeOn(mainThread())
                .subscribe(this::onBookedSuccessfully, this::onBookError);
    }

    private void onBookError(Throwable e) {
        errorHelper(getContext()).longToast(R.string.error_sending_data, e);
    }

    private void onBookedSuccessfully(Response<ApiResponse> response) {
        ActivityNavigator.carParked(getContext(), offer);
        dismiss();
        ToastHelper.of(getContext()).show(getString(R.string.booked_offer));
    }
}
