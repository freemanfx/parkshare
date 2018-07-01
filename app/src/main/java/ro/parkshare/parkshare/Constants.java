package ro.parkshare.parkshare;

import com.google.android.gms.maps.model.LatLng;

public interface Constants {
    String PROD_API_URL = "http://ec2-18-185-48-244.eu-central-1.compute.amazonaws.com:8080";
    String DEV_API_URL = "https://637f18da.ngrok.io";

    String SERVER_URL = PROD_API_URL;

    LatLng IASI_POSITION = new LatLng(47.1731649, 27.5663222);
    LatLng BUCURESTI_POSITION = new LatLng(44.4520456, 26.0853351);

    LatLng INITIAL_POSITION = BUCURESTI_POSITION;

    interface ServerErrors {
        String OFFER_ALREADY_BOOKED = "Offer is already booked!";
    }

    interface DateFormats {
        String JAVA_DATE_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ssZ";
        String SHORT_OFFER = "EEEE dd/MM HH:mm";
    }
}
