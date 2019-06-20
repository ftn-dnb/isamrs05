package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findAll();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Flight findOneById(Long id);

    List<Flight> findAllByAirlineId(Long id);
}
