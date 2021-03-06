package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.DestinationDTO;
import ftn.dnb.dnbtravel.model.Destination;
import ftn.dnb.dnbtravel.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination getDestinationById(Long id) {
        return destinationRepository.findOneById(id);
    }

    public DestinationDTO addDestination(DestinationDTO destination) {
        try {
            Destination dest = destinationRepository.save(new Destination(destination));
            return new DestinationDTO(dest);
        } catch (Exception e) {
            return null;
        }
    }
}
