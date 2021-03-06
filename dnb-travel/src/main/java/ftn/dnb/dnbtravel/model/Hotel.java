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

    @OneToOne(cascade = CascadeType.ALL)
    private HotelPriceList currentPriceList;

    @OneToOne(cascade = CascadeType.ALL)
    private User administrator;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<HotelReservation> hotelReservations;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<AdditionalService> additionalServices;

    public Hotel() {
        super();
    }

    public Hotel(String name, String description, double rating, Address address, Set<Room> rooms, Set<HotelPriceList> hotelPriceLists, User administrator,
                 HotelPriceList currentPriceList, Set<HotelReservation> hotelReservations, Set<AdditionalService> additionalServices) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.address = address;
        this.rooms = rooms;
        this.hotelPriceLists = hotelPriceLists;
        this.administrator = administrator;
        this.currentPriceList = currentPriceList;
        this.hotelReservations = hotelReservations;
        this.additionalServices = additionalServices;
    }

    public Hotel(HotelDTO hotelDTO) {
        this.id = hotelDTO.getId();
        this.name = hotelDTO.getName();
        this.description = hotelDTO.getDescription();
        this.rating = hotelDTO.getRating();
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
        if (this.hotelPriceLists == null) {
            this.hotelPriceLists = new HashSet<>();
        }
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

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public HotelPriceList getCurrentPriceList() {
        if (this.currentPriceList == null) {
            this.currentPriceList = new HotelPriceList();
        }
        return currentPriceList;
    }

    public void setCurrentPriceList(HotelPriceList currentPriceList) {
        this.currentPriceList = currentPriceList;
    }

    public Set<HotelReservation> getHotelReservations() {
        if (this.hotelReservations == null) {
            this.hotelReservations = new HashSet<>();
        }
        return hotelReservations;
    }

    public void setHotelReservations(Set<HotelReservation> hotelReservations) {
        this.hotelReservations = hotelReservations;
    }

    public Set<AdditionalService> getAdditionalServices() {
        if (this.additionalServices == null) {
            this.additionalServices = new HashSet<>();
        }
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }
}
