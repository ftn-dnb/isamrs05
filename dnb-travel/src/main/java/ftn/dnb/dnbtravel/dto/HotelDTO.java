package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.AdditionalService;
import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.Hotel;
import ftn.dnb.dnbtravel.model.Room;

import java.util.ArrayList;

public class HotelDTO {

    private Long id;
    private String name;
    private String description;
    private double rating;
    private Address address;
    private UserDTO administrator;
    private Long currentPriceListID;
    private ArrayList<RoomDTO> rooms;
    private ArrayList<AdditionalService> additionalServices;

    public HotelDTO() {
        super();
    }

    public HotelDTO(Long id, String name, String description, double rating, Address address, UserDTO administrator, Long currentPriceListID, ArrayList<AdditionalService> additionalServices, ArrayList<RoomDTO> rooms) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.address = address;
        this.administrator = administrator;
        this.currentPriceListID = currentPriceListID;
        this.additionalServices = additionalServices;
        this.rooms = rooms;
    }

    public HotelDTO(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.description = hotel.getDescription();
        this.rating = hotel.getRating();
        this.address = hotel.getAddress();
        this.administrator = new UserDTO(hotel.getAdministrator());
        this.currentPriceListID = hotel.getCurrentPriceList().getId();
        this.additionalServices = new ArrayList<AdditionalService>(hotel.getAdditionalServices());
        this.rooms = new ArrayList<>();
        for (Room room : hotel.getRooms()) {
            this.rooms.add(new RoomDTO(room));
        }
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

    public Long getCurrentPriceListID() {
        return currentPriceListID;
    }

    public void setCurrentPriceListID(Long currentPriceListID) {
        this.currentPriceListID = currentPriceListID;
    }

    public ArrayList<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(ArrayList<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public ArrayList<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomDTO> rooms) {
        this.rooms = rooms;
    }
}
