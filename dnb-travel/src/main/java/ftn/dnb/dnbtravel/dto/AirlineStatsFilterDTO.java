package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class AirlineStatsFilterDTO {

    private Long airlineId;
    private Date dateFrom;
    private Date dateTo;

    public AirlineStatsFilterDTO() {
    }

    public AirlineStatsFilterDTO(Long airlineId, Date dateFrom, Date dateTo) {
        this.airlineId = airlineId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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
}
