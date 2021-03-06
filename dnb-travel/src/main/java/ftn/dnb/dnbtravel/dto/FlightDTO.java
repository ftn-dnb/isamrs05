package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Flight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FlightDTO {

    private Long id;
    private Date startDateTime;
    private Date endDateTime;
    private float travelTime;
    private float travelLength;
    private float rating;
    private DestinationDTO startDestination;
    private DestinationDTO endDestination;
    private List<DestinationDTO> transits;
    private Long airlineId;
    private AirplaneDTO airplane;
    private List<AirlinePriceListItemDTO> prices;
    private List<FlightReservationDTO> reservations;
    private boolean isOneWay;

    public FlightDTO() {
        super();
    }

    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.startDateTime = flight.getStartDateTime();
        this.endDateTime = flight.getEndDateTime();
        this.travelTime = flight.getTravelTime();
        this.travelLength = flight.getTravelLength();
        this.rating = flight.getRating();
        this.startDestination = new DestinationDTO(flight.getStartDestination());
        this.endDestination = new DestinationDTO(flight.getEndDestination());
        this.transits = new ArrayList<>();
        this.airlineId = flight.getAirline().getId();
        this.airplane = new AirplaneDTO(flight.getAirplane());
        this.prices = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.isOneWay = flight.isOneWay();

        flight.getTransits().stream().forEach(destination -> this.transits.add(new DestinationDTO(destination)));
        flight.getPrices().stream().forEach(price -> this.prices.add(new AirlinePriceListItemDTO(price)));
        flight.getReservations().stream().forEach(reservation -> this.reservations.add(new FlightReservationDTO(reservation)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public float getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(float travelTime) {
        this.travelTime = travelTime;
    }

    public float getTravelLength() {
        return travelLength;
    }

    public void setTravelLength(float travelLength) {
        this.travelLength = travelLength;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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

    public List<DestinationDTO> getTransits() {
        return transits;
    }

    public void setTransits(List<DestinationDTO> transits) {
        this.transits = transits;
    }

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }

    public AirplaneDTO getAirplane() {
        return airplane;
    }

    public void setAirplane(AirplaneDTO airplane) {
        this.airplane = airplane;
    }

    public List<AirlinePriceListItemDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<AirlinePriceListItemDTO> prices) {
        this.prices = prices;
    }

    public List<FlightReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<FlightReservationDTO> reservations) {
        this.reservations = reservations;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public void setOneWay(boolean oneWay) {
        isOneWay = oneWay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDTO flightDTO = (FlightDTO) o;
        return Objects.equals(id, flightDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
