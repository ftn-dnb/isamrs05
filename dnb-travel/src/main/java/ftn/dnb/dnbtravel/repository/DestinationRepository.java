package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    List<Destination> findAll();

    Destination findOneById(Long id);

}
