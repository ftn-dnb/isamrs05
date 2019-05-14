package ftn.dnb.dnbtravel.dto;

public class ReservationStatsDTO {

    private String date;
    private int number;

    public ReservationStatsDTO() {
    }

    public ReservationStatsDTO(String date, int number) {
        this.date = date;
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
