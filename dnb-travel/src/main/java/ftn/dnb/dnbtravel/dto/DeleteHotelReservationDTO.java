package ftn.dnb.dnbtravel.dto;

public class DeleteHotelReservationDTO {

    private String username;
    private Long reservationId;
    private Long itemId;

    public DeleteHotelReservationDTO(){

    }

    public DeleteHotelReservationDTO(String username, Long reservationId, Long itemId) {
        this.username = username;
        this.reservationId = reservationId;
        this.itemId = itemId;
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
