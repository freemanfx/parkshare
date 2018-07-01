package ro.parkshare.parkshare.consumer;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import retrofit2.Response;
import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.OffersService;

import static ro.parkshare.parkshare.BeanProvider.activityNavigator;
import static ro.parkshare.parkshare.BeanProvider.errorHelper;

public class LeaveParkingDialog extends DialogFragment {

    private static final String OFFER_KEY = "OFFER_KEY";

    public static LeaveParkingDialog newInstance(Offer offer) {
        Bundle args = new Bundle();
        args.putSerializable(OFFER_KEY, offer);

        LeaveParkingDialog dialog = new LeaveParkingDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Offer offer = (Offer) getArguments().getSerializable(OFFER_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.leave_parking_dialog_message)
                .setPositiveButton(R.string.yes, (dialog, id) ->
                        OffersService
                                .getInstance()
                                .leaveOffer(offer)
                                .subscribe(this::onLeaveParkingSuccess, this::onLeaveParkingError)
                )
                .setNegativeButton(R.string.no, (dialog, id) -> {
                    dismiss();
                });
        return builder.create();
    }

    private void onLeaveParkingSuccess(Response<Void> voidResponse) {
        dismiss();
        activityNavigator().toMain();
    }

    private void onLeaveParkingError(Throwable throwable) {
        errorHelper().longToast(R.string.error_sending_data, throwable);
    }
}
