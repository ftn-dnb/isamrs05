package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT * FROM address a WHERE a.place_id = ?1", nativeQuery = true)
    Address findOneByPlaceId(@Param("id") String id);
}
