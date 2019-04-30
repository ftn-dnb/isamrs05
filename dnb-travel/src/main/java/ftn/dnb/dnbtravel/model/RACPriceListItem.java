package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RACPriceListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "active_discount")
    private double activeDiscount;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "price_day", nullable = false)
    private int pricePerDay;

    @OneToOne(cascade = CascadeType.ALL)
    private Car car;

    @OneToOne(cascade = CascadeType.ALL)
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
