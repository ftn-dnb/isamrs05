package ftn.dnb.dnbtravel.model;

import java.util.Date;

public class RACPriceListItem {

    private double activeDiscount;
    private Date startDate;
    private Date endDate;
    private int pricePerDay;
    private RACPriceList racPriceList;

    public RACPriceListItem(){
        super();
    }

    public RACPriceListItem(double activeDiscount, Date startDate, Date endDate, int pricePerDay, RACPriceList racPriceList) {
        this.activeDiscount = activeDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;
        this.racPriceList = racPriceList;
    }

    public double getActiveDiscount() {
        return activeDiscount;
    }

    public void setActiveDiscount(double activeDiscount) {
        this.activeDiscount = activeDiscount;
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

    public RACPriceList getRacPriceList() {
        return racPriceList;
    }

    public void setRacPriceList(RACPriceList racPriceList) {
        this.racPriceList = racPriceList;
    }
}
