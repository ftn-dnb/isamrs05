package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.HotelDTO;
import ftn.dnb.dnbtravel.model.Hotel;
import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.repository.HotelRepository;
import ftn.dnb.dnbtravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    public Hotel findOne(Long id) { return hotelRepository.getOne(id); }

    public List<Hotel> findAll() { return hotelRepository.findAll(); }

    public Hotel save(Hotel hotel) { return hotelRepository.save(hotel); }

    public HotelDTO addHotel(HotelDTO hotelDTO) {
        Hotel savedHotel = new Hotel(hotelDTO);
        if (savedHotel == null) {
            return null;
        }
        User admin = userRepository.findByUsername(hotelDTO.getAdministrator().getUsername());
        savedHotel.setAdministrator(admin);
        hotelRepository.save(savedHotel);
        return new HotelDTO(savedHotel);
    }

}
