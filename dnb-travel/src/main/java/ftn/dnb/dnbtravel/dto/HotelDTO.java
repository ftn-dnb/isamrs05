package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.Hotel;

public class HotelDTO {

    private String name;
    private String description;
    private double rating;
    private Address address;

    public HotelDTO() {
        super();
    }

    public HotelDTO(String name, String description, double rating, Address address) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.address = address;
    }

    public HotelDTO(Hotel hotel) {
        this.name = hotel.getName();
        this.description = hotel.getDescription();
        this.rating = hotel.getRating();
        this.address = hotel.getAddress();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
