package ftn.dnb.dnbtravel.controller;

import com.sun.mail.iap.Response;
import ftn.dnb.dnbtravel.dto.HotelDTO;
import ftn.dnb.dnbtravel.dto.RoomDTO;
import ftn.dnb.dnbtravel.model.Hotel;
import ftn.dnb.dnbtravel.service.HotelService;
import ftn.dnb.dnbtravel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "api/hotels")
@CrossOrigin(origins = "http://localhost:8000")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
    }

    //@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    //public Hotel saveHotel(@RequestBody Hotel hotel) {
    //    return hotelService.save(hotel);
    //}

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Hotel updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        hotel.setId(id);
        return hotelService.save(hotel);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<HotelDTO> addHotel(@RequestBody HotelDTO hotelDTO) {
        HotelDTO savedHotel = hotelService.addHotel(hotelDTO);
        return new ResponseEntity<>(savedHotel, HttpStatus.OK);
    }

    @RequestMapping(value = "/addRoom", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO savedRoom = roomService.addRoom(roomDTO);
        return new ResponseEntity<>(savedRoom, HttpStatus.OK);
    }

    @RequestMapping(value = "/findHotelByAdmin", method = RequestMethod.POST)
    public ResponseEntity<Long> findHotelByAdmin(@RequestBody String username) {
        Long hotelID = hotelService.findHotelIdByAdmin(username);
        return new ResponseEntity<>(hotelID, HttpStatus.OK);
    }
}
