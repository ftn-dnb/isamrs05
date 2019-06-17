package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class ItemFilterDTO {
    private Long hotel_id;
    private Date date_arrival;
    private int overnight_stays;
    private int number_guests;

    public ItemFilterDTO() { super(); }

    public ItemFilterDTO(Long hotel_id, Date date_arrival, int overnight_stays, int number_guests) {
        this.hotel_id = hotel_id;
        this.date_arrival = date_arrival;
        this.overnight_stays = overnight_stays;
        this.number_guests = number_guests;
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

    public int getNumber_guests() {
        return number_guests;
    }

    public void setNumber_guests(int number_guests) {
        this.number_guests = number_guests;
    }
}
