package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.AirlineDTO;
import ftn.dnb.dnbtravel.dto.FlightDTO;
import ftn.dnb.dnbtravel.model.Airline;
import ftn.dnb.dnbtravel.model.Airplane;
import ftn.dnb.dnbtravel.model.Destination;
import ftn.dnb.dnbtravel.model.Flight;
import ftn.dnb.dnbtravel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private AirplaneRepository airplaneRepository;

    public List<AirlineDTO> getAllAirlines() {
        List<Airline> airlines = airlineRepository.findAll();
        List<AirlineDTO> dtos = new ArrayList<AirlineDTO>();
        airlines.stream().forEach(airline -> dtos.add(new AirlineDTO(airline)));
        return dtos;
    }

    public AirlineDTO getAirlineById(Long id) {
        Airline airline = airlineRepository.findOneById(id);

        if (airline == null)
            return null;

        AirlineDTO airlineDto = new AirlineDTO(airline);
        return airlineDto;
    }

    public AirlineDTO addAirline(AirlineDTO airline) {
        addressRepository.save(airline.getAddress());
        Airline savedAirline = airlineRepository.save(new Airline(airline));

        if (savedAirline == null)
            return null;

        return new AirlineDTO(savedAirline);
    }

    public AirlineDTO updateAirline(AirlineDTO airline) {
        addressRepository.save(airline.getAddress());
        Airline savedAirline = airlineRepository.save(new Airline(airline));

        if (savedAirline == null)
            return null;

        return new AirlineDTO(savedAirline);
    }

    public FlightDTO addFlight(Long airlineId, FlightDTO flightToAdd) {
        Airline airline = airlineRepository.findOneById(airlineId);
        Destination startDestination = destinationRepository.findOneById(flightToAdd.getStartDestination().getId());
        Destination endDestination = destinationRepository.findOneById(flightToAdd.getEndDestination().getId());
        Airplane airplane = airplaneRepository.findOneById(flightToAdd.getAirplane().getId());

        Set<Destination> transits = new HashSet<Destination>();
        flightToAdd.getTransits().stream().forEach(transit -> transits.add(new Destination(transit)));

        Flight flight = new Flight(0L, flightToAdd.getStartDateTime(), flightToAdd.getEndDateTime(),
                flightToAdd.getTravelTime(), flightToAdd.getTravelLength(), 0, startDestination, endDestination,
                airline, transits, airplane, new HashSet<>(), new HashSet<>());

        airline.getFlights().add(flight);
        airlineRepository.save(airline);
        Flight savedFlight = flightRepository.save(flight);

        return new FlightDTO(savedFlight);
    }

    public List<FlightDTO> getFlights(Long id) {
        List<Flight> flights = flightRepository.findAllByAirlineId(id);
        List<FlightDTO> dtoFlights = new ArrayList<FlightDTO>();
        flights.stream().forEach(flight -> dtoFlights.add(new FlightDTO(flight)));
        return dtoFlights;
    }
}
