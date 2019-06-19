package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.*;
import ftn.dnb.dnbtravel.model.*;
import ftn.dnb.dnbtravel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private HotelPriceListRepository hotelPriceListRepository;

    @Autowired
    private HotelPriceListItemRepository hotelPriceListItemRepository;

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Autowired
    private AdditionalServiceRepository additionalServiceRepository;

    public Hotel findOne(Long id) { return hotelRepository.getOne(id); }

    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.getOne(id);
        return new HotelDTO(hotel);
    }

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

    public HotelDTO findHotelByAdmin(String username) {
        String adminUsername = username.substring(0, username.length()-1);
        User admin = userRepository.findByUsername(adminUsername);
        Hotel hotel = hotelRepository.findOneByAdminId(admin.getId());
        return new HotelDTO(hotel);
    }

    public Long setCurrentPriceList(Long hotel_id, Long price_list_id) {
        HotelPriceList currentPriceList = hotelPriceListRepository.findOneById(price_list_id);
        Hotel hotel = hotelRepository.findOneById(hotel_id);

        if (hotel.getCurrentPriceList().getId() == price_list_id) return Long.valueOf(-1);

        hotel.setCurrentPriceList(currentPriceList);
        hotelRepository.save(hotel);
        return currentPriceList.getId();
    }

    public ArrayList<Long> addFastHotelReservation(HotelReservationDTO hotelReservationDTO) {
        int capacity_counter = 1, item_counter = 0;
        ArrayList<Long> id_list = new ArrayList<>();
        Hotel hotel = hotelRepository.findOneById(hotelReservationDTO.getHotel_id());
        for (UserDTO userDTO : hotelReservationDTO.getUsers()) {

            HotelReservation reservation = new HotelReservation();
            reservation.setBeginDate(hotelReservationDTO.getDate_arrival());
            Date departure_date = new Date(hotelReservationDTO.getDate_arrival().getTime() + hotelReservationDTO.getOvernight_stays()*(1000 * 60 * 60 * 24));
            reservation.setEndDate(departure_date);

            for (AdditionalServiceDTO serviceDTO : hotelReservationDTO.getItems().get(0).getAdditionalServices()) {
                reservation.getAdditionalServices().add(additionalServiceRepository.getOne(serviceDTO.getId()));
            }

            HotelPriceListItem item = hotelPriceListItemRepository.findOneById(hotelReservationDTO.getItems().get(item_counter).getId());
            if (item.getRoom().getCapacity() == capacity_counter) {
                capacity_counter = 1;
                item_counter += 1;
            } else {
                capacity_counter += 1;
            }
            if (userDTO.getUsername() != null) {
                User user = userRepository.findByUsername(userDTO.getUsername());
                user.getHotelReservations().add(reservation);
                reservation.setUser(user);
            }
            reservation.setHotelPriceListItem(item);
            reservation.setRating(3);
            hotel.getHotelReservations().add(reservation);
            hotelReservationRepository.save(reservation);
            id_list.add(reservation.getId());
        }
        return id_list;
    }

    public ArrayList<Long> addHotelReservation(HotelReservationDTO hotelReservationDTO) {
        int capacity_counter = 1, item_counter = 0;
        ArrayList<Long> id_list = new ArrayList<>();
        Hotel hotel = hotelRepository.findOneById(hotelReservationDTO.getHotel_id());
        for (UserDTO userDTO : hotelReservationDTO.getUsers()) {

            HotelReservation reservation = new HotelReservation();
            reservation.setBeginDate(hotelReservationDTO.getDate_arrival());
            Date departure_date = new Date(hotelReservationDTO.getDate_arrival().getTime() + hotelReservationDTO.getOvernight_stays()*(1000 * 60 * 60 * 24));
            reservation.setEndDate(departure_date);

            for (AdditionalServiceDTO serviceDTO : hotelReservationDTO.getAdditionalServices()) {
                reservation.getAdditionalServices().add(additionalServiceRepository.getOne(serviceDTO.getId()));
            }

            HotelPriceListItem item = hotelPriceListItemRepository.findOneById(hotelReservationDTO.getItems().get(item_counter).getId());
            if (item.getRoom().getCapacity() == capacity_counter) {
                capacity_counter = 1;
                item_counter += 1;
            } else {
                capacity_counter += 1;
            }
            if (userDTO.getUsername() != null) {
                User user = userRepository.findByUsername(userDTO.getUsername());
                user.getHotelReservations().add(reservation);
                reservation.setUser(user);
            }
            reservation.setHotelPriceListItem(item);
            reservation.setRating(3);
            hotel.getHotelReservations().add(reservation);
            hotelReservationRepository.save(reservation);
            id_list.add(reservation.getId());
        }
        return id_list;
    }

    public HotelDTO addAdditionalService(AdditionalServiceDTO additionalServiceDTO) {
        Hotel hotel = hotelRepository.findOneById(additionalServiceDTO.getHotel_id());
        AdditionalService service = new AdditionalService(additionalServiceDTO);
        hotel.getAdditionalServices().add(service);
        hotelRepository.save(hotel);
        return new HotelDTO(hotel);
    }

    public HotelDTO deleteService(Long service_id, Long hotel_id) {
        Hotel hotel = hotelRepository.findOneById(hotel_id);
        AdditionalService service = additionalServiceRepository.getOne(service_id);
        hotel.getAdditionalServices().remove(service);
        hotelRepository.save(hotel);
        //additionalServiceRepository.delete(service_id);
        return new HotelDTO(hotel);
    }

}
