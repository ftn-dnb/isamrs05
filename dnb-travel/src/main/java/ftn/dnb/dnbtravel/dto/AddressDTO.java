package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;

public class AddressDTO {
    private String place_id; //api_id
    private String country;
    private String locality;
    private String name;
    private String postal_code;
    private double latitude;
    private double longitude;

    public AddressDTO() { super(); }

    public AddressDTO(String place_id, String country, String locality, String name, double latitude, double longitude, String postal_code) {
        this.place_id = place_id;
        this.country = country;
        this.locality = locality;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postal_code = postal_code;
    }

    public AddressDTO(Address address) {
        this.place_id = address.getPlace_id();
        this.country = address.getCountry();
        this.locality = address.getCity();
        this.name = address.getStreetName();
        this.postal_code = address.getPostalCode();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
}
