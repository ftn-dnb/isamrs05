package ftn.dnb.dnbtravel.dto;

import java.util.List;

public class FlightReservationDataDTO {

    private List<SeatDTO> seats;
    private List<UserReservationInfoDTO> users;

    public List<SeatDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDTO> seats) {
        this.seats = seats;
    }

    public List<UserReservationInfoDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserReservationInfoDTO> users) {
        this.users = users;
    }
}

