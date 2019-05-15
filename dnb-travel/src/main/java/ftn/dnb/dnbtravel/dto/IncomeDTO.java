package ftn.dnb.dnbtravel.dto;

public class IncomeDTO {

    private String date;
    private float income;

    public IncomeDTO() {
    }

    public IncomeDTO(String date, float income) {
        this.date = date;
        this.income = income;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }
}
