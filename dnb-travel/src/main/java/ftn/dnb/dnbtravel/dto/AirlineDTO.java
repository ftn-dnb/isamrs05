package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.Airline;

import java.util.Objects;

public class AirlineDTO {

    private long id;
    private String name;
    private Address address;
    private String description;

    public AirlineDTO() {
    }

    public AirlineDTO(long id, String name, Address address, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public AirlineDTO(Airline airline) {
        this.id = airline.getId();
        this.name = airline.getName();
        this.address = airline.getAddress();
        this.description = airline.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirlineDTO that = (AirlineDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AirlineDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
