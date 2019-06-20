package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.RACPriceListItem;
import ftn.dnb.dnbtravel.model.RACReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RACReservationRepository  extends JpaRepository<RACReservation, Long> {

    RACReservation findByItem(RACPriceListItem item);
}
