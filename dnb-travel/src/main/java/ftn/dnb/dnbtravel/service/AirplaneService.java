package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.AirplaneDTO;
import ftn.dnb.dnbtravel.model.Airplane;
import ftn.dnb.dnbtravel.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirplaneService {

    @Autowired
    private AirplaneRepository airplaneRepository;

    public List<AirplaneDTO> getAllAirplanes() {
        List<Airplane> airplanes = airplaneRepository.findAll();
        List<AirplaneDTO> dtos = new ArrayList<AirplaneDTO>();
        airplanes.stream().forEach(airplane -> dtos.add(new AirplaneDTO(airplane)));

        return dtos;
    }

    public AirplaneDTO getAirplaneById(Long id) {
        Airplane airplane = airplaneRepository.findOneById(id);

        if (airplane == null)
            return null;

        return new AirplaneDTO(airplane);
    }
}
