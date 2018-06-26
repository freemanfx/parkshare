package ro.parkshare.parkshare.provider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.parkshare.parkshare.R;

public class OffersManagementFragment extends Fragment {

    public OffersManagementFragment() {
    }

    static OffersManagementFragment newInstance(Long parkingId) {
        Bundle bundle = new Bundle();
        bundle.putLong("PARKING_ID", parkingId);

        OffersManagementFragment fragment = new OffersManagementFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offers_management, container, false);
        return rootView;
    }
}
