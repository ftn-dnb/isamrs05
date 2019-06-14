package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.HotelPriceListDTO;
import ftn.dnb.dnbtravel.dto.HotelPriceListItemDTO;
import ftn.dnb.dnbtravel.dto.RoomDTO;
import ftn.dnb.dnbtravel.model.Hotel;
import ftn.dnb.dnbtravel.model.HotelPriceList;
import ftn.dnb.dnbtravel.model.HotelPriceListItem;
import ftn.dnb.dnbtravel.model.Room;
import ftn.dnb.dnbtravel.repository.HotelPriceListItemRepository;
import ftn.dnb.dnbtravel.repository.HotelPriceListRepository;
import ftn.dnb.dnbtravel.repository.HotelRepository;
import ftn.dnb.dnbtravel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Room refRoom = roomRepository.findOneById(hotelPriceListItemDTO.getRoomID());
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
}
