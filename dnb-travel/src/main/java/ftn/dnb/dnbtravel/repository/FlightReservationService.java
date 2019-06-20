package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  FlightReservationService extends JpaRepository<FlightReservation, Long> {

    FlightReservation findOneById(Long id);
}
