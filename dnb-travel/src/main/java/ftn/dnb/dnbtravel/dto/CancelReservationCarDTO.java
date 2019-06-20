package ftn.dnb.dnbtravel.dto;

public class CancelReservationCarDTO {

    private Long id;//user id
    private Long reservationId;
    private Long companyId;

    public CancelReservationCarDTO(){

    }

    public CancelReservationCarDTO(Long id, Long reservationId, Long companyId) {
        this.id = id;
        this.reservationId = reservationId;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
