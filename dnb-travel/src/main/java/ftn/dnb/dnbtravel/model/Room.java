package ftn.dnb.dnbtravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ftn.dnb.dnbtravel.dto.RoomDTO;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private int roomNumber;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Hotel hotel;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<HotelPriceListItem> hotelPriceListItems;

    public Room() {
        super();
    }

    public Room(int roomNumber, int floor, double rating, int capacity, Hotel hotel, Set<HotelPriceListItem> hotelPriceListItems) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.rating = rating;
        this.capacity = capacity;
        this.hotel = hotel;
        this.hotelPriceListItems = hotelPriceListItems;
    }

    public Room(RoomDTO roomDTO) {
        this.roomNumber = roomDTO.getRoomNumber();
        this.floor = roomDTO.getFloor();
        this.rating = roomDTO.getRating();
        this.capacity = roomDTO.getCapacity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Set<HotelPriceListItem> getHotelPriceListItems() {
        return hotelPriceListItems;
    }

    public void setHotelPriceListItems(Set<HotelPriceListItem> hotelPriceListItems) {
        this.hotelPriceListItems = hotelPriceListItems;
    }
}
