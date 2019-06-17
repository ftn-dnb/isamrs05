package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Airline;
import ftn.dnb.dnbtravel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    List<Airline> findAll();

    Page<Airline> findAll(Pageable pageable);

    Airline findOneById(Long id);

    Airline findOneByAdministrator(User user);
}
