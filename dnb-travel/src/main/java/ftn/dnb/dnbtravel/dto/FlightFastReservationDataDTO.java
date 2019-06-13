package ftn.dnb.dnbtravel.dto;

public class FlightFastReservationDataDTO {

    private Long reservationId;
    private String username;
    private String passport;

    public FlightFastReservationDataDTO() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
