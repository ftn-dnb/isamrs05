package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.model.Airline;
import ftn.dnb.dnbtravel.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    public List<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }

    public Airline getAirlineById(Long id) {
        return airlineRepository.findOneById(id);
    }

    public Airline addAirline(Airline airline) {
        return airlineRepository.save(airline);
    }

    public Airline updateAirline(Airline airline) {
        return airlineRepository.save(airline);
    }
}
