package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "startDateTime", nullable = false)
    private Date startDateTime;

    @Column(name = "endDateTime", nullable = false)
    private Date endDateTime;

    @Column(name = "travelTime", nullable = false)
    private float travelTime;

    @Column(name = "travelLength", nullable = false)
    private float travelLength;

    @Column(name = "rating", nullable = false)
    private float rating;

    @OneToOne(cascade = CascadeType.ALL)
    private Destination startDestination;

    @OneToOne(cascade = CascadeType.ALL)
    private Destination endDestination;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Destination> transits = new HashSet<Destination>();

    @ManyToOne
    private Airline airline;

    @ManyToOne(cascade = CascadeType.ALL)
    private Airplane airplane;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AirlinePriceListItem> prices = new HashSet<AirlinePriceListItem>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FlightReservation> reservations = new HashSet<FlightReservation>();

    public Flight() {
    }

    public Flight(Long id, Date startDateTime, Date endDateTime, float travelTime, float travelLength,
                  float rating, Destination startDestination, Destination endDestination, Airline airline,
                  Set<Destination> transits, Airplane airplane, Set<AirlinePriceListItem> prices,
                  Set<FlightReservation> reservations) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.travelTime = travelTime;
        this.travelLength = travelLength;
        this.rating = rating;
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.airline = airline;
        this.transits = transits;
        this.airplane = airplane;
        this.prices = prices;
        this.reservations = reservations;
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

    public Destination getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(Destination startDestination) {
        this.startDestination = startDestination;
    }

    public Destination getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(Destination endDestination) {
        this.endDestination = endDestination;
    }

    public Set<Destination> getTransits() {
        return transits;
    }

    public void setTransits(Set<Destination> transits) {
        this.transits = transits;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Set<AirlinePriceListItem> getPrices() {
        return prices;
    }

    public void setPrices(Set<AirlinePriceListItem> prices) {
        this.prices = prices;
    }

    public Set<FlightReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<FlightReservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id.equals(flight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", travelTime=" + travelTime +
                ", travelLength=" + travelLength +
                ", rating=" + rating +
                ", startDestination=" + startDestination +
                ", endDestination=" + endDestination +
                '}';
    }
}
