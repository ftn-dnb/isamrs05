package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.HotelDTO;
import ftn.dnb.dnbtravel.model.Hotel;
import ftn.dnb.dnbtravel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel findOne(Long id) { return hotelRepository.getOne(id); }

    public List<Hotel> findAll() { return hotelRepository.findAll(); }

    public Hotel save(Hotel hotel) { return hotelRepository.save(hotel); }

    public HotelDTO addHotel(HotelDTO hotelDTO) {
        Hotel savedHotel = hotelRepository.save(new Hotel(hotelDTO));
        if (savedHotel == null) {
            return null;
        }

        return new HotelDTO(savedHotel);
    }

}
