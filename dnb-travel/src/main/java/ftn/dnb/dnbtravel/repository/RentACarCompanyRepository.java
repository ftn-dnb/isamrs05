package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.dto.UserDTO;
import ftn.dnb.dnbtravel.model.Car;
import ftn.dnb.dnbtravel.model.RACPriceListItem;
import ftn.dnb.dnbtravel.model.RentACarCompany;
import ftn.dnb.dnbtravel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentACarCompanyRepository extends JpaRepository<RentACarCompany, Long> {
    List<RentACarCompany> findAll ();

    Page<RentACarCompany> findAll(Pageable pageable);

    RentACarCompany findOneById(Long id);

    RentACarCompany findOneByAdministrator(User user);

    Car findCarById(Long id);

}
