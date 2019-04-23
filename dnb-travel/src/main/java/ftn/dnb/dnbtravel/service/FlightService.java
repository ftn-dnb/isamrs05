package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.FlightDTO;
import ftn.dnb.dnbtravel.model.Flight;
import ftn.dnb.dnbtravel.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        List<FlightDTO> dtos = new ArrayList<FlightDTO>();
        flights.stream().forEach(flight -> dtos.add(new FlightDTO(flight)));
        return dtos;
    }

    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findOneById(id);

        if (flight == null)
            return null;

        return new FlightDTO(flight);
    }

}
