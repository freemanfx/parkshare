package ro.parkshare.parkshare.service;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParkingLocation implements Serializable {
    private Long id;
    @Expose
    private String name;
    private Double latitude;
    private Double longitude;
    @Expose
    @SerializedName("userId")
    private Long userId;

    private ParkingLocation() {
    }

    public ParkingLocation(String name, Double latitude, Double longitude, Long userId) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LatLng getLatLang() {
        return new LatLng(latitude, longitude);
    }
}
