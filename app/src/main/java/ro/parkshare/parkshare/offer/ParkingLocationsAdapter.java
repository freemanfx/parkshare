package ro.parkshare.parkshare.offer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.service.ParkingLocation;

public class ParkingLocationsAdapter extends RecyclerView.Adapter<ParkingLocationsAdapter.ViewHolder> {
    private List<ParkingLocation> dataSet;

    public ParkingLocationsAdapter(List<ParkingLocation> dataSet) {
        this.dataSet = dataSet;
        dataSet.add(new ParkingLocation(1L, "Piata Victoriei", 44.67, 27.33));
        dataSet.add(new ParkingLocation(1L, "Grozavesti", 44.67, 27.33));
        dataSet.add(new ParkingLocation(1L, "Aviatiei", 44.67, 27.33));
    }

    @Override
    public ParkingLocationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parking_locations_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParkingLocation parkingLocation = dataSet.get(position);
        holder.name.setText(parkingLocation.getName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void replaceData(List<ParkingLocation> parkingLocations) {
        dataSet = parkingLocations;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout view;
        public TextView name;

        ViewHolder(LinearLayout view) {
            super(view);
            name = view.findViewById(R.id.name);
        }
    }
}
