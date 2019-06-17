package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

    Car findOneById(Long id);
}
