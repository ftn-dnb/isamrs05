package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class AirlineStatsFilterDTO {

    private Long airlineId;
    private Date dateFrom;
    private Date dateTo;
    private Date dateReservations;
    private String reservationsCriteria;

    public AirlineStatsFilterDTO() {
    }

    public AirlineStatsFilterDTO(Long airlineId, Date dateFrom, Date dateTo, Date dateReservations, String reservationsCriteria) {
        this.airlineId = airlineId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateReservations = dateReservations;
        this.reservationsCriteria = reservationsCriteria;
    }

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
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
