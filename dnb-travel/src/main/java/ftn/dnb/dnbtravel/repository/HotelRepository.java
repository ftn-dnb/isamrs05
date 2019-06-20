package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Set;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAll();

    //Page<Hotel> findAll(Pageable pageable);

    Hotel findOneById(Long id);

    @Query(value = "SELECT * FROM hotel h WHERE h.administrator_id = ?1", nativeQuery = true)
    Hotel findOneByAdminId(@Param("id") Long id);
}
