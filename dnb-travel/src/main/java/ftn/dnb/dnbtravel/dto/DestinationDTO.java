package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Destination;

import java.util.Objects;

public class DestinationDTO {

    private Long id;
    private String city;
    private String country;
    private String airportName;
    private String airportCode;
    private float longitude;
    private float latitude;

    public DestinationDTO() {
        super();
    }

    public DestinationDTO(Long id, String city, String country, String airportName,
                          String airportCode, float longitude, float latitude) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public DestinationDTO(Destination dest) {
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
        DestinationDTO that = (DestinationDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
