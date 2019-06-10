package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.HotelPriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelPriceListItemRepository extends JpaRepository<HotelPriceListItem, Long> {

    HotelPriceListItem findOneById(Long id);

}
