package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.HotelPriceList;
import ftn.dnb.dnbtravel.model.HotelPriceListItem;
import ftn.dnb.dnbtravel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAll();

    Room findOneById(Long id);

    @Query(value = "SELECT * FROM room r WHERE r.hotel_id = :id", nativeQuery = true)
    List<Room> findAllByHotelID(@Param("id") Long id);

    @Query(value = "SELECT h FROM HotelPriceList h WHERE h.hotel.id = ?1")
    List<HotelPriceList> findAllPriceListsByHotelID(Long id);

    @Query(value = "SELECT * FROM hotel_price_list_item h WHERE h.hotel_price_list_id = :id", nativeQuery = true)
    List<HotelPriceListItem> findAllPriceListItemsByListID(@Param("id") Long id);
}
