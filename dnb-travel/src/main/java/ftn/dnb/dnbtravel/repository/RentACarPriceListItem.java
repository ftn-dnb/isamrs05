package ftn.dnb.dnbtravel.repository;


import ftn.dnb.dnbtravel.model.RACPriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentACarPriceListItem extends JpaRepository<RACPriceListItem, Long> {

    List <RACPriceListItem> findAll();

    RACPriceListItem findOneById(Long id);

}
