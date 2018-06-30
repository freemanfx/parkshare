package ro.parkshare.parkshare.service;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Offer implements Serializable {

    @Expose
    private Long id;
    @Expose
    private ParkingLocation parking;
    @Expose
    private Validity validity;

    public Offer(ParkingLocation parking, Validity validity) {
        this.parking = parking;
        this.validity = validity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingLocation getParking() {
        return parking;
    }

    public void setParking(ParkingLocation parkingLocation) {
        this.parking = parkingLocation;
    }

    public Validity getValidity() {
        return validity;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }
}
