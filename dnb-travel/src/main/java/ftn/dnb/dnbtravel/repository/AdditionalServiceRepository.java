package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {

    @Transactional
    @Modifying
    @Query("delete from AdditionalService s where s.id = ?1")
    void delete(Long service_id);
}
