package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.HotelPriceListDTO;
import ftn.dnb.dnbtravel.dto.HotelPriceListItemDTO;
import ftn.dnb.dnbtravel.dto.RoomDTO;
import ftn.dnb.dnbtravel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/rooms")
@CrossOrigin(origins = "http://localhost:8000")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping(path = "/all/{id}")
    public ResponseEntity<List<RoomDTO>> getAllRooms(@PathVariable("id") Long hotelID) {
        List<RoomDTO> roomList = roomService.findRoomsByHotelID(hotelID);
        return new ResponseEntity<List<RoomDTO>>(roomList, HttpStatus.OK);
    }

    @GetMapping(path = "/prices/{id}")
    public ResponseEntity<List<HotelPriceListDTO>> getAllPrices(@PathVariable("id") Long hotelID) {
        List<HotelPriceListDTO> priceList = roomService.findHotelPriceListsByID(hotelID);
        return new ResponseEntity<>(priceList, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO savedRoom = roomService.addRoom(roomDTO);
        return new ResponseEntity<>(savedRoom, HttpStatus.OK);
    }

    @RequestMapping(value = "/addPriceListItem", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<HotelPriceListItemDTO> addHotelPriceListItem(@RequestBody HotelPriceListItemDTO hotelPriceListItemDTO) {
        HotelPriceListItemDTO savedPriceListItem = roomService.addPriceListItem(hotelPriceListItemDTO);
        return new ResponseEntity<>(savedPriceListItem, HttpStatus.OK);
    }

    @GetMapping(path = "/priceList/{id}")
    public ResponseEntity<HotelPriceListDTO> getHotelPriceList(@PathVariable("id") Long id) {
        HotelPriceListDTO hotelPriceListDTO = roomService.getHotelPriceList(id);
        return new ResponseEntity<>(hotelPriceListDTO, HttpStatus.OK);
    }
}
