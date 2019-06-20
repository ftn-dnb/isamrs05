package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.Airline;
import ftn.dnb.dnbtravel.model.Destination;
import ftn.dnb.dnbtravel.model.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AirlineDTO {

    private long id;
    private String name;
    private AddressDTO address;
    private String description;
    private float rating;
    private List<DestinationDTO> destinations;
    private List<FlightDTO> flights;
    private UserDTO administrator;

    public AirlineDTO() {
    }

    public AirlineDTO(long id, String name, AddressDTO address, String description, List<Destination> destinations,
                      List<Flight> flights, float rating, UserDTO administrator) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.rating = rating;
        this.administrator = administrator;
    }

    public AirlineDTO(Airline airline) {
        this.id = airline.getId();
        this.name = airline.getName();
        this.address = new AddressDTO(airline.getAddress());
        this.description = airline.getDescription();
        this.destinations = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.rating = airline.getRating();
        this.administrator = new UserDTO(airline.getAdministrator());

        airline.getDestinations().stream().forEach(dest -> this.destinations.add(new DestinationDTO(dest)));
        airline.getFlights().stream().forEach(flight -> this.flights.add(new FlightDTO(flight)));
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DestinationDTO> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationDTO> destinations) {
        this.destinations = destinations;
    }

    public List<FlightDTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightDTO> flights) {
        this.flights = flights;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public UserDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(UserDTO administrator) {
        this.administrator = administrator;
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
