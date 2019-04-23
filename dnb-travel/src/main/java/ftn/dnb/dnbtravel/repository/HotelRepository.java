package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
