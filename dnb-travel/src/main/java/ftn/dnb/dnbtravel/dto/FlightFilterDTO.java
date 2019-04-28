package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class FlightFilterDTO {

    private Long airlineId;
    private DestinationDTO startDestination;
    private DestinationDTO endDestination;
    private Date startDate;
    private Date endDate;
    private Float travelTime;
    private Float travelLength;
    private Float minPrice;
    private Float maxPrice;
    private Boolean isDirect;
    private Boolean isOneWay;
    private Integer numOfPeople;

    public FlightFilterDTO() {
    }

    public FlightFilterDTO(Long airlineId, DestinationDTO startDestination, DestinationDTO endDestination,
                           Date startDate, Date endDate, Float travelTime, Float travelLength, Float minPrice,
                           Float maxPrice, Boolean isDirect, Boolean isOneWay, Integer numOfPeople) {
        this.airlineId = airlineId;
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.travelTime = travelTime;
        this.travelLength = travelLength;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.isDirect = isDirect;
        this.isOneWay = isOneWay;
        this.numOfPeople = numOfPeople;
    }

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }

    public DestinationDTO getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(DestinationDTO startDestination) {
        this.startDestination = startDestination;
    }

    public DestinationDTO getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(DestinationDTO endDestination) {
        this.endDestination = endDestination;
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

    public Float getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Float travelTime) {
        this.travelTime = travelTime;
    }

    public Float getTravelLength() {
        return travelLength;
    }

    public void setTravelLength(Float travelLength) {
        this.travelLength = travelLength;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(Boolean direct) {
        isDirect = direct;
    }

    public Boolean getIsOneWay() {
        return isOneWay;
    }

    public void setIsOneWay(Boolean isOneWay) {
        this.isOneWay = isOneWay;
    }

    public Integer getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(Integer numOfPeople) {
        this.numOfPeople = numOfPeople;
    }
}
