package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.*;
import ftn.dnb.dnbtravel.model.*;
import ftn.dnb.dnbtravel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public HotelDTO updateHotel(HotelDTO hotelDTO) {
        Hotel hotel = hotelRepository.findOneById(hotelDTO.getId());
        hotel.setName(hotelDTO.getName());
        hotel.setDescription(hotelDTO.getDescription());

        Address address = addressRepository.findOneByPlaceId(hotelDTO.getAddress().getPlace_id());
        if (address == null) {
            address = new Address(hotelDTO.getAddress());
        }

        hotel.setAddress(address);

        hotelRepository.save(hotel);

        return new HotelDTO(hotel);
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
        //ArrayList<Address> addressList = new ArrayList<>(addressRepository.findAll());
        Address address = addressRepository.findOneByPlaceId(hotelDTO.getAddress().getPlace_id());
        if (address == null) {
            address = new Address(hotelDTO.getAddress());
        }

        HotelPriceList priceList = new HotelPriceList();
        priceList.setDescription("Primarni cenovnik");
        priceList.setHotel(savedHotel);

        savedHotel.setCurrentPriceList(priceList);
        savedHotel.getHotelPriceLists().add(priceList);

        savedHotel.setAddress(address);

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

    public ArrayList<IncomeDTO> getIncomeStatsHotel(HotelStatsFilterDTO filter) {
        Hotel hotel = hotelRepository.findOneById(filter.getHotel_id());

        if (hotel == null) {
            return null;
        }

        LinkedHashMap<String, Double> income;

        if (filter.getDateTo() == null) {
            filter.setDateTo(new Date());
        }

        if (filter.getDateFrom() == null)
            income = this.filterReservationsToDate(hotel, filter.getDateTo());
        else
            income = this.filterReservationsFromDate(hotel, filter.getDateFrom(), filter.getDateTo());

        ArrayList<IncomeDTO> incomeStats = new ArrayList<>();
        income.forEach((key, value) -> incomeStats.add(new IncomeDTO(key, value.floatValue())));

        return incomeStats;
    }

    private LinkedHashMap filterReservationsToDate(Hotel hotel, Date to) {
        LinkedHashMap<String, Double> income = new LinkedHashMap<>();

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        for (HotelReservation reservation : hotel.getHotelReservations()) {
            if (reservation.getBeginDate().before(to)) {
                String dateKey = formatter.format(reservation.getBeginDate());
                if (income.containsKey(dateKey))
                    income.put(dateKey, income.get(dateKey) + reservation.getPrice());
                else
                    income.put(dateKey, reservation.getPrice());
            }
        }

        return income;
    }

    private LinkedHashMap filterReservationsFromDate(Hotel hotel, Date from, Date to) {
        LinkedHashMap<String, Double> income = new LinkedHashMap<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        for (HotelReservation reservation : hotel.getHotelReservations()) {
            if (reservation.getBeginDate().after(from) && reservation.getBeginDate().before(to)) {
                String dateKey = formatter.format(reservation.getBeginDate());
                if (income.containsKey(dateKey))
                    income.put(dateKey, income.get(dateKey) + reservation.getPrice());
                else
                    income.put(dateKey, reservation.getPrice());
            }
        }

        return income;
    }

    public List<ReservationStatsDTO> getReservationStatsHotel(HotelStatsFilterDTO filter) {
        Hotel hotel = hotelRepository.findOneById(filter.getHotel_id());

        if (filter.getDateReservations() == null)
            filter.setDateReservations(new Date());

        switch (filter.getReservationsCriteria()) {
            case "Day":
                return getReservationStatsDay(hotel, filter.getDateReservations());
            case "Week":
                return getReservationStatsWeek(hotel, filter.getDateReservations());
            case "Month":
                return getReservationStatsMonth(hotel, filter.getDateReservations());
            default:
                return getReservationStatsWeek(hotel, filter.getDateReservations());
        }
    }

    private List<ReservationStatsDTO> getReservationStatsDay(Hotel hotel, Date date) {
        List<ReservationStatsDTO> stats = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        stats.add(new ReservationStatsDTO(formatter.format(date), 0));

        LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (HotelReservation reservation : hotel.getHotelReservations()) {
            LocalDate resDate = reservation.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (startDate.isEqual(resDate)) {
                stats.get(0).setNumber(stats.get(0).getNumber() + 1);
            }
        }

        return stats;
    }

    private List<ReservationStatsDTO> getReservationStatsWeek(Hotel hotel, Date date) {
        LinkedHashMap<String, Integer> stats= new LinkedHashMap<>();
        List<ReservationStatsDTO> statsList = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        startDate = startDate.minusDays(startDate.getDayOfWeek().getValue());
        endDate = endDate.plusDays(7 - endDate.getDayOfWeek().getValue());

        for (HotelReservation reservation : hotel.getHotelReservations()) {
            LocalDate reservationDate = reservation.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (reservationDate.isAfter(startDate) && reservationDate.isBefore(endDate)) {
                String key = formatter.format(reservation.getBeginDate());

                if (stats.containsKey(key))
                    stats.put(key, stats.get(key) + 1);
                else
                    stats.put(key, 1);
            }
        }

        stats.forEach((key, value) -> statsList.add(new ReservationStatsDTO(key, value)));

        return statsList;
    }

    private List<ReservationStatsDTO> getReservationStatsMonth(Hotel hotel, Date date) {
        LinkedHashMap<String, Integer> stats= new LinkedHashMap<>();
        List<ReservationStatsDTO> statsList = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        LocalDate filterDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int monthFilterDate = filterDate.getMonthValue();


        for (HotelReservation reservation : hotel.getHotelReservations()) {
            LocalDate reservationDate = reservation.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (reservationDate.getMonthValue() == monthFilterDate) {
                String key = formatter.format(reservation.getBeginDate());

                if (stats.containsKey(key))
                    stats.put(key, stats.get(key) + 1);
                else
                    stats.put(key, 1);
            }
        }

        stats.forEach((key, value) -> statsList.add(new ReservationStatsDTO(key, value)));

        return statsList;
    }

    public List<OneHotelReservationDTO> getAllUserReservations(UserDTO userDTO){
        User user = userRepository.findOneByUsername(userDTO.getUsername());
        List<OneHotelReservationDTO> list = new LinkedList<>();

        for(HotelReservation res: user.getHotelReservations()){

            OneHotelReservationDTO fake_res = new OneHotelReservationDTO();
            fake_res.setReservationId(res.getId());
            fake_res.setItmeId(res.getHotelPriceListItem().getId());
            fake_res.setBeginDate(res.getBeginDate());
            fake_res.setEndDate(res.getEndDate());

            list.add(fake_res);
        }

        return list;
    }

    public ResponseEntity<?> deleteReservation(DeleteHotelReservationDTO data){
        HotelReservation reservation = hotelReservationRepository.findOneById(data.getReservationId());
        User user = userRepository.findByUsername(data.getUsername());
        HotelPriceListItem item = hotelPriceListItemRepository.findOneById(data.getItemId());
        Hotel h = item.getRoom().getHotel();


        if(reservation == null || user == null || item == null || h == null){
            return new ResponseEntity<>("Error", HttpStatus.OK);
        }

        for(HotelReservation res: h.getHotelReservations()){
            if(res.getId() == reservation.getId()){
                h.getHotelReservations().remove(res);
                hotelRepository.save(h);
                break;
            }
        }

        for(HotelReservation res: user.getHotelReservations()){
            if(res.getId()==reservation.getId()){
                user.getHotelReservations().remove(res);
                userRepository.save(user);
            }
        }

        reservation.setUser(null);
        reservation.setHotelPriceListItem(null);

        hotelReservationRepository.save(reservation);

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
