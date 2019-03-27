package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.model.Hotel;
import ftn.dnb.dnbtravel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Hotel saveHotel(@RequestBody Hotel hotel) {
        return hotelService.save(hotel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Hotel updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        hotel.setId(id);
        return hotelService.save(hotel);
    }
}
