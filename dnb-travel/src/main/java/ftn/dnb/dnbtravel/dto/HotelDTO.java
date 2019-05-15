package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.Hotel;

public class HotelDTO {

    private Long id;
    private String name;
    private String description;
    private double rating;
    private Address address;
    private UserDTO administrator;

    public HotelDTO() {
        super();
    }

    public HotelDTO(Long id, String name, String description, double rating, Address address, UserDTO administrator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.address = address;
        this.administrator = administrator;
    }

    public HotelDTO(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.description = hotel.getDescription();
        this.rating = hotel.getRating();
        this.address = hotel.getAddress();
        this.administrator = new UserDTO(hotel.getAdministrator());
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

    public UserDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(UserDTO administrator) {
        this.administrator = administrator;
    }
}
