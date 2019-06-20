package ftn.dnb.dnbtravel.dto;

public class CancelFlightDTO {

    private Long flightId;
    private String username;
    private Long reservationId;

    public CancelFlightDTO() {
    }

    public CancelFlightDTO(Long flightId, String username, Long reservationId) {
        this.flightId = flightId;
        this.username = username;
        this.reservationId = reservationId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
