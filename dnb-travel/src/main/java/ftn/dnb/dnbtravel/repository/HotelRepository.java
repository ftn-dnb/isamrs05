package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAll();

    //Page<Hotel> findAll(Pageable pageable);

    //Hotel findOneById(Long id);
}
