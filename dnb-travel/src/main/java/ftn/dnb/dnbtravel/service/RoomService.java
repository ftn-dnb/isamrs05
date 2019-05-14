package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.RoomDTO;
import ftn.dnb.dnbtravel.model.Hotel;
import ftn.dnb.dnbtravel.model.Room;
import ftn.dnb.dnbtravel.repository.HotelRepository;
import ftn.dnb.dnbtravel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Room save(Room room) { return roomRepository.save(room); }

    public RoomDTO addRoom(RoomDTO roomDTO) {
        Hotel ref_hotel = hotelRepository.getOne(new Long(roomDTO.getHotelID()));
        Room room = new Room(roomDTO);
        ref_hotel.getRooms().add(room);
        room.setHotel(ref_hotel);
        hotelRepository.save(ref_hotel);
        //roomRepository.save(room);
        return new RoomDTO(room);
    }
}
