package ftn.dnb.dnbtravel.dto;

import java.util.ArrayList;
import java.util.List;

public class RACStatsDTO {

    private float rating;
    private List<IncomeDTO> income;


    public RACStatsDTO(){

    }

    public RACStatsDTO(float rating, List<IncomeDTO> income) {
        this.rating = rating;
        this.income = income;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<IncomeDTO> getIncome() {
        if(income == null){
            income = new ArrayList<>();
        }
        return income;
    }

    public void setIncome(List<IncomeDTO> income) {
        this.income = income;
    }
}
