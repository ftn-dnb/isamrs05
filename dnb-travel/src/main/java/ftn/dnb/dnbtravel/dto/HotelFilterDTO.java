package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;

public class HotelFilterDTO {
    private Address address;
    private double rating;

    public HotelFilterDTO() { super(); }

    public HotelFilterDTO(Address address, double rating) {
        this.address = address;
        this.rating = rating;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
