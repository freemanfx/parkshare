package ro.parkshare.parkshare.service;

public class Offer {
    private Integer id;
    private Parking parking;
    private Validity validity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public Validity getValidity() {
        return validity;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }
}
