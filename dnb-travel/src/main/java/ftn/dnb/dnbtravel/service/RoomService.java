package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.HotelPriceListDTO;
import ftn.dnb.dnbtravel.dto.HotelPriceListItemDTO;
import ftn.dnb.dnbtravel.dto.ItemFilterDTO;
import ftn.dnb.dnbtravel.dto.RoomDTO;
import ftn.dnb.dnbtravel.model.*;
import ftn.dnb.dnbtravel.repository.HotelPriceListItemRepository;
import ftn.dnb.dnbtravel.repository.HotelPriceListRepository;
import ftn.dnb.dnbtravel.repository.HotelRepository;
import ftn.dnb.dnbtravel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RoomService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelPriceListItemRepository priceListItemRepository;

    @Autowired
    private HotelPriceListRepository priceListRepository;

    public List<RoomDTO> findRoomsByHotelID(Long id) {
        ArrayList<Room> rooms = new ArrayList<Room>(roomRepository.findAllByHotelID(id));
        ArrayList<RoomDTO> roomsDTO = new ArrayList<>();
        for (Room room : rooms) {
            roomsDTO.add(new RoomDTO(room));
        }
        return roomsDTO;
    }

    public RoomDTO addRoom(RoomDTO roomDTO) {
        Hotel ref_hotel = hotelRepository.findOneById(roomDTO.getHotelID());
        Room room = new Room(roomDTO);
        room.setHotel(ref_hotel);
        ref_hotel.getRooms().add(room);
        hotelRepository.save(ref_hotel);
        //roomRepository.save(room);
        return new RoomDTO(room);
    }

    public List<HotelPriceListDTO> findHotelPriceListsByID(Long id) {
        ArrayList<HotelPriceList> hotelPriceLists = new ArrayList<>(roomRepository.findAllPriceListsByHotelID(id));
        ArrayList<HotelPriceListDTO> dtoList = new ArrayList<>();
        for (HotelPriceList list : hotelPriceLists) {
            dtoList.add(new HotelPriceListDTO(list));
        }
        return dtoList;
    }

    public HotelPriceListItemDTO addPriceListItem(HotelPriceListItemDTO hotelPriceListItemDTO) {
        HotelPriceList refPriceList = priceListRepository.findOneById(hotelPriceListItemDTO.getHotelPriceListID());
        Room refRoom = roomRepository.findOneById(hotelPriceListItemDTO.getRoom().getId());
        HotelPriceListItem item = new HotelPriceListItem(hotelPriceListItemDTO);

        refPriceList.getHotelPriceListItems().add(item);
        refRoom.getHotelPriceListItems().add(item);

        item.setHotelPriceList(refPriceList);
        item.setRoom(refRoom);
        //priceListItemRepository.save(item);
        //priceListRepository.save(refPriceList);
        roomRepository.save(refRoom);
        return new HotelPriceListItemDTO(item);
    }

    public HotelPriceListDTO getHotelPriceList(Long id) {
        HotelPriceList hotelPriceList = priceListRepository.findOneById(id);
        return new HotelPriceListDTO(hotelPriceList);
    }

    public ArrayList<HotelPriceListItemDTO> searchHotelPriceListItems(ItemFilterDTO filter) {
        Set<HotelPriceListItem> items = hotelRepository.findOneById(filter.getHotel_id()).getCurrentPriceList().getHotelPriceListItems();
        Set<HotelReservation> reservations = hotelRepository.findOneById(filter.getHotel_id()).getHotelReservations();
        ArrayList<HotelPriceListItem> filtered = new ArrayList<>();
        Date departure_date = new Date(filter.getDate_arrival().getTime() + filter.getOvernight_stays()*(1000 * 60 * 60 * 24));
        for (HotelPriceListItem item : items) {
            boolean cond1 = item.getStartDate().before(filter.getDate_arrival()) || item.getStartDate().compareTo(filter.getDate_arrival()) == 0;
            boolean cond2 = item.getEndDate().after(departure_date) || item.getEndDate().compareTo(departure_date) == 0;
            boolean date_item_cond = false;
            if (cond1 && cond2)
                date_item_cond = true;
            boolean date_res_cond = true;
            for (HotelReservation reservation : reservations) {
                if (reservation.getHotelPriceListItem().getRoom().getId() == item.getRoom().getId()) {
                    if ((reservation.getBeginDate().compareTo(filter.getDate_arrival()) <= 0) && (filter.getDate_arrival().compareTo(reservation.getEndDate()) < 0)) {
                        date_res_cond = false;
                        break;
                    }
                    if ((reservation.getBeginDate().compareTo(departure_date) < 0) && (departure_date.compareTo(reservation.getEndDate()) <= 0)) {
                        date_res_cond = false;
                        break;
                    }
                    if ((filter.getDate_arrival().compareTo(reservation.getBeginDate()) <= 0) && (reservation.getEndDate().compareTo(departure_date) <= 0)) {
                        date_res_cond = false;
                        break;
                    }
                }
            }
            if ((item.getRoom().getCapacity() <= filter.getNumber_guests()) && (date_item_cond && date_res_cond))
                filtered.add(item);

        }





        ArrayList<HotelPriceListItemDTO> filteredDTO = new ArrayList<>();
        for(HotelPriceListItem item : filtered) {
            filteredDTO.add(new HotelPriceListItemDTO(item));
        }
        return filteredDTO;
    }
}
