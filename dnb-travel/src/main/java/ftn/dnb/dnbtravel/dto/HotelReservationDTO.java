package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.HotelReservation;

import java.util.ArrayList;
import java.util.Date;

public class HotelReservationDTO {
    private Long hotel_id;
    private Date date_arrival;
    private int overnight_stays;
    private ArrayList<HotelPriceListItemDTO> items;
    private ArrayList<UserDTO> users;

    HotelReservationDTO() { super(); }

    public HotelReservationDTO(Long hotel_id, Date date_arrival, int overnight_stays, ArrayList<HotelPriceListItemDTO> items, ArrayList<UserDTO> users) {
        this.hotel_id = hotel_id;
        this.date_arrival = date_arrival;
        this.overnight_stays = overnight_stays;
        this.items = items;
        this.users = users;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Date getDate_arrival() {
        return date_arrival;
    }

    public void setDate_arrival(Date date_arrival) {
        this.date_arrival = date_arrival;
    }

    public int getOvernight_stays() {
        return overnight_stays;
    }

    public void setOvernight_stays(int overnight_stays) {
        this.overnight_stays = overnight_stays;
    }

    public ArrayList<HotelPriceListItemDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<HotelPriceListItemDTO> items) {
        this.items = items;
    }

    public ArrayList<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserDTO> users) {
        this.users = users;
    }
}
