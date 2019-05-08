package ftn.dnb.dnbtravel.dto;

import java.util.ArrayList;
import java.util.List;

public class AirlineStatsDTO {

    private float rating;
    private List<FlightDTO> flights;
    private List<IncomeDTO> income;

    public AirlineStatsDTO() {
    }

    public AirlineStatsDTO(float rating, List<FlightDTO> flights, List<IncomeDTO> income) {
        this.rating = rating;
        this.flights = flights;
        this.income = income;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<FlightDTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightDTO> flights) {
        this.flights = flights;
    }

    public List<IncomeDTO> getIncome() {
        if (income == null)
            income = new ArrayList<IncomeDTO>();

        return income;
    }

    public void setIncome(List<IncomeDTO> income) {
        this.income = income;
    }
}
