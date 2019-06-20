package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.HotelReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {

    HotelReservation findOneById(Long id);

}
