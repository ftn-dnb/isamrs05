package ftn.dnb.dnbtravel.model;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "streetName", nullable = false)
    private String streetName;

    @Column(name = "streetNumber", nullable = false)
    private String streetNumber;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "postalCode", nullable = false)
    private String postalCode;

    public Address(){
        super();
    }

    public Address(Long id, String streetName, String streetNumber, String city, String country, String postalCode) {
        this.id = id;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
