package ro.parkshare.parkshare.provider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.ErrorHelper;
import ro.parkshare.parkshare.service.OffersService;

import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class OffersManagementFragment extends Fragment {
    private static final String PARKING_ID = "PARKING_ID";

    private OffersAdapter offersAdapter;
    private Long parkingId;
    private ErrorHelper errorHelper = ErrorHelper.with(getActivity());

    public OffersManagementFragment() {
    }

    static OffersManagementFragment newInstance(Long parkingId) {
        Bundle bundle = new Bundle();
        bundle.putLong(PARKING_ID, parkingId);

        OffersManagementFragment fragment = new OffersManagementFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(PARKING_ID)) {
            parkingId = arguments.getLong(PARKING_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offers_management, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        offersAdapter = new OffersAdapter(this.getActivity(), emptyList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(offersAdapter);

        getData();

        return rootView;
    }

    private void getData() {
        if (parkingId != null) {
            OffersService.getInstance()
                    .getOffersByParkingId(parkingId)
                    .observeOn(mainThread())
                    .subscribe(offersAdapter::replaceData, throwable -> errorHelper.longToast("Error while retrieving offers", throwable));
        }
    }
}
