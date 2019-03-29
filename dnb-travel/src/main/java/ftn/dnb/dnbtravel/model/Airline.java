package ftn.dnb.dnbtravel.model;

import ftn.dnb.dnbtravel.dto.AirlineDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address; // @TODO: change this to Address object

    @Column(name = "description", nullable = false)
    private String description;

    // private Set<Destination> destinations; // @TODO: add destinations

    // private Set<Flight> flights; // @TODO: add flights

    // @TODO: dodati
    // Spisak karata sa popustima za brzu rezervaciju
    // Konfiguraciju segmenata i mesta u avionima
    // Cenovnik i informacije o prtljagu

    public Airline() {
        super();
    }

    public Airline(Long id, String name, String address, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public Airline(AirlineDTO airlineDTO) {
        this.id = airlineDTO.getId();
        this.name = airlineDTO.getName();
        this.address = airlineDTO.getAddress();
        this.description = airlineDTO.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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
        Airline airline = (Airline) o;
        return id.equals(airline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Airline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
