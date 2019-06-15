package ftn.dnb.dnbtravel.model;

import ftn.dnb.dnbtravel.dto.RACListItemDTO;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "racpricelistitem")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "racitem_car",
            joinColumns = @JoinColumn(name = "racitem_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
    private RACPriceList racPriceList;

    public RACPriceListItem(){
        super();
    }

    public RACPriceListItem(double activeDiscount, Date startDate, Date endDate, int pricePerDay, RACPriceList racPriceList, Car car) {
        this.activeDiscount = activeDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;
        this.racPriceList = racPriceList;
        this.car = car;

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
