package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Room;

public class RoomDTO {

    private int roomNumber;
    private int floor;
    private double rating;
    private int capacity;
    private int hotelID;

    public RoomDTO() {
        super();
    }

    public RoomDTO(Room room) {
        this.roomNumber = room.getRoomNumber();
        this.floor = room.getFloor();
        this.rating = room.getRating();
        this.capacity = room.getCapacity();
        //this.hotelID = room.getHotel().getId();
    }

    public RoomDTO(int roomNumber, int floor, double rating, int capacity, int hotelID) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.rating = rating;
        this.capacity = capacity;
        this.hotelID = hotelID;
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

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }
}
