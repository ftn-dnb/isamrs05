package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class CarFilterDTO {

    private Date startDate;
    private Date endDate;
    private Integer pricePerDay;

    public CarFilterDTO(){
        super();
    }

    public CarFilterDTO(Date startDate, Date endDate, int pricePerDay) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Integer pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
