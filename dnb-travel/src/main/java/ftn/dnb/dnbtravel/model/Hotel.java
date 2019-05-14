package ftn.dnb.dnbtravel.model;


import ftn.dnb.dnbtravel.dto.HotelDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rating", nullable = false)
    private double rating;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> rooms;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<HotelPriceList> hotelPriceLists;

    public Hotel() {
        super();
    }

    public Hotel(String name, String description, double rating, Address address, Set<Room> rooms, Set<HotelPriceList> hotelPriceLists) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.address = address;
        this.rooms = rooms;
        this.hotelPriceLists = hotelPriceLists;
    }

    public Hotel(HotelDTO hotelDTO) {
        this.name = hotelDTO.getName();
        this.description = hotelDTO.getDescription();
        this.rating = hotelDTO.getRating();
        this.address = hotelDTO.getAddress();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Room> getRooms() {
        if (this.rooms == null) {
            this.rooms = new HashSet<>();
        }
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<HotelPriceList> getHotelPriceLists() {
        return hotelPriceLists;
    }

    public void setHotelPriceLists(Set<HotelPriceList> hotelPriceLists) {
        this.hotelPriceLists = hotelPriceLists;
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
}
