package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.RACPriceListItem;
import ftn.dnb.dnbtravel.model.RACReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RACReservationRepository  extends JpaRepository<RACReservation, Long> {

    List<RACReservation> findByItem(RACPriceListItem item);
    RACReservation findOneById(Long id);
}
