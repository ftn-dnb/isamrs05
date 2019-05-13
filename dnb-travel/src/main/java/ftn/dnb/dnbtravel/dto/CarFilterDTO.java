package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.CarType;

import java.util.Date;

public class CarFilterDTO {

    private Date startDate;
    private Date endDate;
    private Integer pricePerDay;
    private Integer seatsNumber;
    private CarType type;
    private String  brand;

    public CarFilterDTO(){
        super();
    }

    public CarFilterDTO(Date startDate, Date endDate, Integer pricePerDay,
                        Integer seatsNumber, CarType type, String brand) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;
        this.seatsNumber = seatsNumber;
        this.type = type;
        this.brand = brand;
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

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
