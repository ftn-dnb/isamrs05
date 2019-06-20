package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class OneHotelReservationDTO {

    private Long reservationId;
    private Long itmeId;
    private Date beginDate;
    private Date endDate;

    public OneHotelReservationDTO(){

    }

    public OneHotelReservationDTO(Long reservationId, Long itmeId, Date beginDate, Date endDate) {
        this.reservationId = reservationId;
        this.itmeId = itmeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getItmeId() {
        return itmeId;
    }

    public void setItmeId(Long itmeId) {
        this.itmeId = itmeId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
