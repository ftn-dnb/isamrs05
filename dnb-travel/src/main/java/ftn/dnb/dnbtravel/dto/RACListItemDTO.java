package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Car;
import ftn.dnb.dnbtravel.model.RACPriceList;
import ftn.dnb.dnbtravel.model.RACPriceListItem;


import java.util.Date;

public class RACListItemDTO {


    private Date startDate;
    private Date endDate;
    private int pricePerDay;
    private CarDTO car;
    private double activeDiscount;

    public RACListItemDTO(){

    }

    public RACListItemDTO(Date startDate, Date endDate, int pricePerDay) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;

    }

    public RACListItemDTO(RACPriceListItem item){
        this.startDate = item.getStartDate();
        this.endDate = item.getEndDate();
        this.pricePerDay = item.getPricePerDay();
        this.car = new CarDTO(item.getCar());
        this.activeDiscount = item.getActiveDiscount();
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

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public double getActiveDiscount() {
        return activeDiscount;
    }

    public void setActiveDiscount(double activeDiscount) {
        this.activeDiscount = activeDiscount;
    }
}
