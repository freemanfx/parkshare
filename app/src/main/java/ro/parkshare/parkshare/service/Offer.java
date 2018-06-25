package ro.parkshare.parkshare.service;

public class Offer {
    private Integer id;
    private ParkingLocation parking;
    private Validity validity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
