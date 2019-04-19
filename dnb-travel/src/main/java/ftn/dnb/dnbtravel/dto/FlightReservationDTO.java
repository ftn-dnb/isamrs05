package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.FlightReservation;

import java.util.Date;

public class FlightReservationDTO {

    private Long id;
    private Date reservationDate;
    private float price;
    private float rating;
    private int seatRow;
    private int seatColumn;

    public FlightReservationDTO() {
    }

    public FlightReservationDTO(Long id, Date reservationDate, float price, float rating, int seatRow, int seatColumn) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.price = price;
        this.rating = rating;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public FlightReservationDTO(FlightReservation reservation) {
        this.id = reservation.getId();
        this.reservationDate = reservation.getReservationDate();
        this.price = reservation.getPrice();
        this.rating = reservation.getRating();
        this.seatRow = reservation.getSeatRow();
        this.seatColumn = reservation.getSeatColumn();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }
}
