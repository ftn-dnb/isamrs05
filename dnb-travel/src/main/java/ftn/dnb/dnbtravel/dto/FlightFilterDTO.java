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
    private Boolean is1Transit;
    private Boolean is2Transits;
    private Boolean is3PlusTransits;

    public FlightFilterDTO() {
    }

    public FlightFilterDTO(Long airlineId, DestinationDTO startDestination, DestinationDTO endDestination,
                           Date startDate, Date endDate, Float travelTime, Float travelLength, Float minPrice,
                           Float maxPrice, Boolean isDirect, Boolean is1Transit, Boolean is2Transits,
                           Boolean is3PlusTransits) {
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
        this.is1Transit = is1Transit;
        this.is2Transits = is2Transits;
        this.is3PlusTransits = is3PlusTransits;
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

    public Boolean getDirect() {
        return isDirect;
    }

    public void setDirect(Boolean direct) {
        isDirect = direct;
    }

    public Boolean getIs1Transit() {
        return is1Transit;
    }

    public void setIs1Transit(Boolean is1Transit) {
        this.is1Transit = is1Transit;
    }

    public Boolean getIs2Transits() {
        return is2Transits;
    }

    public void setIs2Transits(Boolean is2Transits) {
        this.is2Transits = is2Transits;
    }

    public Boolean getIs3PlusTransits() {
        return is3PlusTransits;
    }

    public void setIs3PlusTransits(Boolean is3PlusTransits) {
        this.is3PlusTransits = is3PlusTransits;
    }
}
