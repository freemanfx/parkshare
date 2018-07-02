package ro.parkshare.parkshare.consumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.DurationHelper;
import ro.parkshare.parkshare.service.Offer;

class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View infoWindow;

    MarkerInfoWindowAdapter(Context context) {
        this.infoWindow = LayoutInflater.from(context).inflate(R.layout.offer_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        Offer offer = (Offer) marker.getTag();

        TextView availability = infoWindow.findViewById(R.id.availability);

        if (offer != null) {
            long minutes = offer.getValidity().minutes();
            String availabilityText = DurationHelper.millisToHM(minutes * 1000 * 60);
            availability.setText(availabilityText);
        }

        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
