package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.AirlineDTO;
import ftn.dnb.dnbtravel.model.Airline;
import ftn.dnb.dnbtravel.repository.AddressRepository;
import ftn.dnb.dnbtravel.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AddressRepository addressRepository;

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
}
