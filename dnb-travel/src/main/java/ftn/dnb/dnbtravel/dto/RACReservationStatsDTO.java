package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class RACReservationStatsDTO {

    private Long id;
    private Date dateReservation;
    private String reservationCriteria;

    public RACReservationStatsDTO(Long id, Date dateReservation, String reservationCriteria) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.reservationCriteria = reservationCriteria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getReservationCriteria() {
        return reservationCriteria;
    }

    public void setReservationCriteria(String reservationCriteria) {
        this.reservationCriteria = reservationCriteria;
    }
}
