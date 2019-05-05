package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.AirlinePriceListItemDTO;
import ftn.dnb.dnbtravel.dto.FlightDTO;
import ftn.dnb.dnbtravel.dto.FlightFilterDTO;
import ftn.dnb.dnbtravel.model.Flight;
import ftn.dnb.dnbtravel.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<FlightDTO> searchAndFilterFlights(FlightFilterDTO filter) {
        List<FlightDTO> flights = this.getAllFlights();

        if (filter.getAirlineId() != null)
            flights = flights.stream().filter(f -> f.getAirlineId().equals(filter.getAirlineId())).collect(Collectors.toList());
        if (filter.getStartDestination() != null)
            flights = flights.stream().filter(f -> f.getStartDestination().equals(filter.getStartDestination())).collect(Collectors.toList());
        if (filter.getEndDestination() != null)
            flights = flights.stream().filter(f -> f.getEndDestination().equals(filter.getEndDestination())).collect(Collectors.toList());
        if (filter.getStartDate() != null)
            flights = flights.stream().filter(f -> f.getStartDateTime().after(filter.getStartDate())).collect(Collectors.toList());
        if (filter.getEndDate() != null)
            flights = flights.stream().filter(f -> f.getEndDateTime().before(filter.getEndDate())).collect(Collectors.toList());
        if (filter.getTravelTime() != null)
            flights = flights.stream().filter(f -> f.getTravelTime() <= filter.getTravelTime()).collect(Collectors.toList());
        if (filter.getTravelLength() != null)
            flights = flights.stream().filter(f -> f.getTravelLength() <= filter.getTravelLength()).collect(Collectors.toList());

        if (filter.getIsDirect() != null)
            if (filter.getIsDirect())
                flights = flights.stream().filter(f -> f.getTransits().isEmpty()).collect(Collectors.toList());

        if (filter.getIsOneWay() != null)
            if (filter.getIsOneWay())
                flights = flights.stream().filter(f -> f.isOneWay()).collect(Collectors.toList());

        if (filter.getNumOfPeople() != null)
            flights = flights.stream().filter(f -> f.getReservations().size() + filter.getNumOfPeople() <= f.getAirplane().getNumOfSeats()).collect(Collectors.toList());

        if (filter.getMinPrice() != null)
            flights = flights.stream().filter(f -> {
                for (AirlinePriceListItemDTO item : f.getPrices()) {
                    if (item.getPrice() <= filter.getMinPrice())
                        return false;
                }
                return true;
            }).collect(Collectors.toList());

        if (filter.getMaxPrice() != null)
            flights = flights.stream().filter(f -> {
                for (AirlinePriceListItemDTO item : f.getPrices()) {
                    if (item.getPrice() <= filter.getMaxPrice())
                        return true;
                }
                return false;
            }).collect(Collectors.toList());


        return flights;
    }

}
