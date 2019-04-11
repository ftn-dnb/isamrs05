package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    List<Airplane> findAll();

    Airplane findOneById(Long id);
}
