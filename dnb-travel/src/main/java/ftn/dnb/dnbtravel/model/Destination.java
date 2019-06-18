package ftn.dnb.dnbtravel.model;

import ftn.dnb.dnbtravel.dto.DestinationDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "airportName", nullable = false)
    private String airportName;

    @Column(name = "airportCode", nullable = false, unique = true, length = 3)
    private String airportCode;

    @Column(name = "longitude", nullable = false)
    private float longitude;

    @Column(name = "latitude", nullable = false)
    private float latitude;


    public Destination() {
        super();
    }

    public Destination(Long id, String city, String country, String airportName,
                       String airportCode, float longitude, float latitude) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Destination(DestinationDTO dest) {
        this.id = dest.getId();
        this.city = dest.getCity();
        this.country = dest.getCountry();
        this.airportCode = dest.getAirportCode();
        this.airportName = dest.getAirportName();
        this.longitude = dest.getLongitude();
        this.latitude = dest.getLatitude();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return id.equals(that.id) &&
                airportCode.equals(that.airportCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, airportCode);
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", airportName='" + airportName + '\'' +
                ", airportCode='" + airportCode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
