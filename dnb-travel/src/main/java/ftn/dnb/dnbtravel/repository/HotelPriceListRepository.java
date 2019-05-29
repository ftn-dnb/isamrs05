package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.HotelPriceList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelPriceListRepository extends JpaRepository<HotelPriceList, Long> {

    HotelPriceList findOneById(Long id);

}
