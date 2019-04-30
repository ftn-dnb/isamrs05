package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Car;
import ftn.dnb.dnbtravel.model.RACPriceList;
import ftn.dnb.dnbtravel.model.RACPriceListItem;


import java.util.Date;

public class RACListItemDTO {


    private Date startDate;
    private Date endDate;
    private int pricePerDay;
    private Car car;

    public RACListItemDTO(Date startDate, Date endDate, int pricePerDay, Car car) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;
        this.car = car;
    }

    public RACListItemDTO(RACPriceListItem item){
        this.startDate = item.getStartDate();
        this.endDate = item.getEndDate();
        this.pricePerDay = item.getPricePerDay();
        this.car = item.getCar();
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

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
