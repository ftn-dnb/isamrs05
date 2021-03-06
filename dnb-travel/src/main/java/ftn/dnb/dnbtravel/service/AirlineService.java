package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.*;
import ftn.dnb.dnbtravel.model.*;
import ftn.dnb.dnbtravel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AirlineDTO> getAllAirlines() {
        List<Airline> airlines = airlineRepository.findAll();
        List<AirlineDTO> dtos = new ArrayList<>();
        airlines.stream().forEach(airline -> dtos.add(new AirlineDTO(airline)));
        return dtos;
    }

    public AirlineDTO getAirlineById(Long id) {
        Airline airline = airlineRepository.findOneById(id);

        if (airline == null)
            return null;

        return new AirlineDTO(airline);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public AirlineDTO updateAirline(AirlineDTO airlineDto) {
        Airline airline = airlineRepository.findOneById(airlineDto.getId());
        airline.setName(airlineDto.getName());
        airline.setDescription(airlineDto.getDescription());

        Address address = addressRepository.findOneByPlaceId(airlineDto.getAddress().getPlace_id());
        if (address == null) {
            address = new Address(airlineDto.getAddress());
        }

        airline.setAddress(address);

        airlineRepository.save(airline);

        return new AirlineDTO(airline);
    }

    public FlightDTO addFlight(Long airlineId, FlightDTO flightToAdd) {
        Airline airline = airlineRepository.findOneById(airlineId);
        Destination startDestination = destinationRepository.findOneById(flightToAdd.getStartDestination().getId());
        Destination endDestination = destinationRepository.findOneById(flightToAdd.getEndDestination().getId());
        Airplane airplane = airplaneRepository.findOneById(flightToAdd.getAirplane().getId());

        Set<Destination> transits = new HashSet<>();
        flightToAdd.getTransits().forEach(transit -> transits.add(destinationRepository.findOneById(transit.getId())));

        Set<AirlinePriceListItem> prices = new HashSet<>();
        flightToAdd.getPrices().forEach(item -> prices.add(new AirlinePriceListItem(item)));

        Flight flight = new Flight(null, flightToAdd.getStartDateTime(), flightToAdd.getEndDateTime(),
                flightToAdd.getTravelTime(), flightToAdd.getTravelLength(), 0, startDestination, endDestination,
                airline, transits, airplane, prices, new HashSet<>(), flightToAdd.isOneWay());

        airline.getFlights().add(flight);
        airlineRepository.save(airline);

        return new FlightDTO(flight);
    }

    public List<FlightDTO> getFlights(Long id) {
        List<Flight> flights = flightRepository.findAllByAirlineId(id);
        List<FlightDTO> dtoFlights = new ArrayList<>();
        flights.stream().forEach(flight -> dtoFlights.add(new FlightDTO(flight)));
        return dtoFlights;
    }

    public AirlineStatsDTO getStatsForCompany(AirlineStatsFilterDTO filter) {
        Airline airline = airlineRepository.findOneById(filter.getAirlineId());

        if (airline == null)
            return null;

        AirlineStatsDTO stats = new AirlineStatsDTO();
        LinkedHashMap<String, Float> income;

        if (filter.getDateTo() == null)
            filter.setDateTo(new Date());

        if (filter.getDateFrom() == null)
            income = this.filterReservationsToDate(airline, filter.getDateTo());
        else
            income = this.filterReservationsFromToDate(airline, filter.getDateFrom(), filter.getDateTo());

        income.forEach((key, value) -> stats.getIncome().add(new IncomeDTO(key, value)));

        return stats;
    }

    private LinkedHashMap filterReservationsToDate(Airline airline, Date to) {
        LinkedHashMap<String, Float> income = new LinkedHashMap<>();

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        for (Flight flight : airline.getFlights()) {
            for (FlightReservation reservation : flight.getReservations()) {
                if (reservation.getReservationDate().before(to)) {
                    String dateKey = formatter.format(reservation.getReservationDate());
                    if (income.containsKey(dateKey))
                        income.put(dateKey, income.get(dateKey) + reservation.getPrice());
                    else
                        income.put(dateKey, reservation.getPrice());
                }
            }
        }

        return income;
    }

    private LinkedHashMap filterReservationsFromToDate(Airline airline, Date from, Date to) {
        LinkedHashMap<String, Float> income = new LinkedHashMap<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        for (Flight flight : airline.getFlights()) {
            for (FlightReservation reservation : flight.getReservations()) {
                if (reservation.getReservationDate().after(from) && reservation.getReservationDate().before(to)) {
                    String dateKey = formatter.format(reservation.getReservationDate());
                    if (income.containsKey(dateKey))
                        income.put(dateKey, income.get(dateKey) + reservation.getPrice());
                    else
                        income.put(dateKey, reservation.getPrice());
                }
            }
        }

        return income;
    }

    public List<ReservationStatsDTO> getStatsForCompanyReservations(AirlineStatsFilterDTO filter) {
        Airline airline = airlineRepository.findOneById(filter.getAirlineId());

        if (filter.getDateReservations() == null)
            filter.setDateReservations(new Date());

        switch (filter.getReservationsCriteria()) {
            case "Day":
                return getReservationStatsDay(airline, filter.getDateReservations());
            case "Week":
                return getReservationStatsWeek(airline, filter.getDateReservations());
            case "Month":
                return getReservationStatsMonth(airline, filter.getDateReservations());
            default: // week
                return getReservationStatsWeek(airline, filter.getDateReservations());
        }
    }

    private List<ReservationStatsDTO> getReservationStatsDay(Airline airline, Date date) {
        List<ReservationStatsDTO> stats = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        stats.add(new ReservationStatsDTO(formatter.format(date), 0));

        LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (Flight flight : airline.getFlights()) {
            for (FlightReservation reservation : flight.getReservations()) {
                LocalDate resDate = reservation.getReservationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (startDate.isEqual(resDate)) {
                    stats.get(0).setNumber(stats.get(0).getNumber() + 1);
                }
            }
        }

        return stats;
    }

    private List<ReservationStatsDTO> getReservationStatsWeek(Airline airline, Date date) {
        LinkedHashMap<String, Integer> stats= new LinkedHashMap<>();
        List<ReservationStatsDTO> statsList = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        startDate = startDate.minusDays(startDate.getDayOfWeek().getValue());
        endDate = endDate.plusDays(7 - endDate.getDayOfWeek().getValue());

        for (Flight flight : airline.getFlights()) {
            for (FlightReservation reservation : flight.getReservations()) {
                LocalDate reservationDate = reservation.getReservationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (reservationDate.isAfter(startDate) && reservationDate.isBefore(endDate)) {
                    String key = formatter.format(reservation.getReservationDate());

                    if (stats.containsKey(key))
                        stats.put(key, stats.get(key) + 1);
                    else
                        stats.put(key, 1);
                }
            }
        }

        stats.forEach((key, value) -> statsList.add(new ReservationStatsDTO(key, value)));

        return statsList;
    }

    private List<ReservationStatsDTO> getReservationStatsMonth(Airline airline, Date date) {
        LinkedHashMap<String, Integer> stats= new LinkedHashMap<>();
        List<ReservationStatsDTO> statsList = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        LocalDate filterDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int monthFilterDate = filterDate.getMonthValue();

        for (Flight flight : airline.getFlights()) {
            for (FlightReservation reservation : flight.getReservations()) {
                LocalDate reservationDate = reservation.getReservationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (reservationDate.getMonthValue() == monthFilterDate) {
                    String key = formatter.format(reservation.getReservationDate());

                    if (stats.containsKey(key))
                        stats.put(key, stats.get(key) + 1);
                    else
                        stats.put(key, 1);
                }
            }
        }

        stats.forEach((key, value) -> statsList.add(new ReservationStatsDTO(key, value)));

        return statsList;
    }


    public AirlineDTO addAirline(AirlineDTO airlineDTO) {
        Airline savedAirline = new Airline(airlineDTO);
        if (savedAirline == null) {
            return null;
        }

        Address address = addressRepository.findOneByPlaceId(airlineDTO.getAddress().getPlace_id());
        if (address == null) {
            address = new Address(airlineDTO.getAddress());
        }

        savedAirline.setAddress(address);

        User admin = userRepository.findByUsername(airlineDTO.getAdministrator().getUsername());
        savedAirline.setAdministrator(admin);
        airlineRepository.save(savedAirline);
        return new AirlineDTO(savedAirline);
    }

    public AirlineDTO addDestination(Long airlineId, DestinationDTO destination) {
        Destination dest = destinationRepository.findOneById(destination.getId());
        Airline airline = airlineRepository.findOneById(airlineId);

        if (dest == null || airline == null)
            return null;

        if (airline.getDestinations().contains(dest))
            return null;

        airline.getDestinations().add(dest);
        airlineRepository.save(airline);

        return new AirlineDTO(airline);
    }

    public List<FlightReservationDTO> getFastReservations(Long airlineId) {
        Airline airline = airlineRepository.findOneById(airlineId);
        List<FlightReservationDTO> reservations = new ArrayList<>();

        for (Flight flight : airline.getFlights()) {
            for (FlightReservation reservation : flight.getReservations()) {
                if (reservation.isFastReservation()) {
                    reservations.add(new FlightReservationDTO(reservation));
                }
            }
        }

        return reservations;
    }

    public AirlineDTO getAirlineByAdminUsername(String username) {
        User admin = userRepository.findOneByUsername(username);

        if (admin == null)
            return null;

        Airline airline = airlineRepository.findOneByAdministrator(admin);
        if (airline == null)
            return null;

        return new AirlineDTO(airline);
    }
}
