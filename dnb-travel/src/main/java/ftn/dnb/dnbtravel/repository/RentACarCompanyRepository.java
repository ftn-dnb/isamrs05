package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.RACPriceListItem;
import ftn.dnb.dnbtravel.model.RentACarCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentACarCompanyRepository extends JpaRepository<RentACarCompany, Long> {
    List<RentACarCompany> findAll ();

    Page<RentACarCompany> findAll(Pageable pageable);

    RentACarCompany findOneById(Long id);

}
