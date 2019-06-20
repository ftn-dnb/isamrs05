package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class HotelStatsFilterDTO {
    private Long hotel_id;
    private Date dateFrom;
    private Date dateTo;
    private Date dateReservations;
    private String reservationsCriteria;

    public HotelStatsFilterDTO() { super(); }

    public HotelStatsFilterDTO(Long hotel_id, Date dateFrom, Date dateTo, Date dateReservations, String reservationsCriteria) {
        this.hotel_id = hotel_id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateReservations = dateReservations;
        this.reservationsCriteria = reservationsCriteria;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateReservations() {
        return dateReservations;
    }

    public void setDateReservations(Date dateReservations) {
        this.dateReservations = dateReservations;
    }

    public String getReservationsCriteria() {
        return reservationsCriteria;
    }

    public void setReservationsCriteria(String reservationsCriteria) {
        this.reservationsCriteria = reservationsCriteria;
    }
}
