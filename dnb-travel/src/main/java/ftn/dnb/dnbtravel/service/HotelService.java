package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.HotelDTO;
import ftn.dnb.dnbtravel.dto.HotelFilterDTO;
import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.Hotel;
import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.repository.AddressRepository;
import ftn.dnb.dnbtravel.repository.HotelRepository;
import ftn.dnb.dnbtravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public Hotel findOne(Long id) { return hotelRepository.getOne(id); }

    public List<Address> getAddresses() { return addressRepository.findAll(); }

    public List<HotelDTO> findAll() {
        List<Hotel> hotels = hotelRepository.findAll();
        ArrayList<HotelDTO> dtos = new ArrayList<>();
        for (Hotel h : hotels) {
            dtos.add(new HotelDTO(h));
        }
        return dtos;
    }

    public ArrayList<HotelDTO> hotelSearch(HotelFilterDTO filter) {
        ArrayList<HotelDTO> hotels = new ArrayList<>();
        for (Hotel hotel : hotelRepository.findAll()) {
            if (!hotel.getAddress().getCity().equals(filter.getAddress().getCity()))
                continue;
            if (filter.getRating() != 0)
                if (hotel.getRating() < filter.getRating())
                    continue;
            hotels.add(new HotelDTO(hotel));
        }
        return hotels;
    }

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

    public Long findHotelIdByAdmin(String username) {
        String adminUsername = username.substring(0, username.length()-1);
        User admin = userRepository.findByUsername(adminUsername);
        Hotel hotel = hotelRepository.findOneByAdminId(admin.getId());
        return hotel.getId();
    }

}
