package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.RACPriceListItem;
import ftn.dnb.dnbtravel.model.RACReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface RACReservationRepository  extends JpaRepository<RACReservation, Long> {

    List<RACReservation> findByItem(RACPriceListItem item);

    RACReservation findOneById(Long id);
}
