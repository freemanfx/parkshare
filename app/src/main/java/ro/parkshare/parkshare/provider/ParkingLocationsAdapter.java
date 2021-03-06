package ro.parkshare.parkshare.provider;

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
    private OnItemClickListener itemClickListener;

    public ParkingLocationsAdapter(List<ParkingLocation> dataSet, OnItemClickListener itemClickListener) {
        this.dataSet = dataSet;
        this.itemClickListener = itemClickListener;
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

        holder.item = parkingLocation;
        holder.name.setText(parkingLocation.getName());

        holder.bind(parkingLocation, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void replaceData(List<ParkingLocation> parkingLocations) {
        dataSet = parkingLocations;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ParkingLocation parkingLocation);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        ParkingLocation item;

        ViewHolder(LinearLayout view) {
            super(view);
            name = view.findViewById(R.id.name);
        }

        void bind(ParkingLocation item, OnItemClickListener clickListener) {
            itemView.setOnClickListener((view) -> clickListener.onItemClick(item));
        }
    }
}
