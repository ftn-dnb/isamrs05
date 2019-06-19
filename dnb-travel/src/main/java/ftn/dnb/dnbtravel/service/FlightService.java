package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.*;
import ftn.dnb.dnbtravel.model.AirlinePriceListItem;
import ftn.dnb.dnbtravel.model.Flight;
import ftn.dnb.dnbtravel.model.FlightReservation;
import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.repository.FlightRepository;
import ftn.dnb.dnbtravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        List<FlightDTO> dtos = new ArrayList<>();
        flights.stream().forEach(flight -> dtos.add(new FlightDTO(flight)));
        return dtos;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findOneById(id);

        if (flight == null)
            return null;

        return new FlightDTO(flight);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public FlightDTO reserveTickets(Long flightId, FlightReservationDataDTO data) {
        Flight flight = flightRepository.findOneById(flightId);

        if (data.getUsers().size() != data.getSeats().size())
            return null;

        Date today = new Date();
        float ticketPrice = 0.0f;

        for (AirlinePriceListItem price : flight.getPrices()) {
            if (price.getStartDate().before(today) && price.getEndDate().after(today)) {
                ticketPrice = price.getPrice() * price.getActiveDiscount() / 100;
                break;
            }
        }

        for (int i = 0; i < data.getUsers().size(); ++i) {
            FlightReservation reservation = new FlightReservation();
            reservation.setReservationDate(today);

            SeatDTO seat = new SeatDTO();
            seat.setX(data.getSeats().get(i).getX());
            seat.setY(data.getSeats().get(i).getY());

            if (!checkIfSeatIsFree(seat, flight))
                return null;

            reservation.setSeatRow(data.getSeats().get(i).getX());
            reservation.setSeatColumn(data.getSeats().get(i).getY());
            reservation.setPrice(ticketPrice);
            reservation.setApproved(false);
            reservation.setFastReservation(false);
            reservation.setPassport(data.getUsers().get(i).getPassport());
            reservation.setFirstName(data.getUsers().get(i).getFirstName());
            reservation.setLastName(data.getUsers().get(i).getLastName());

            // First element is user that made the reservations, others are friends
            if (i == 0) {
                reservation.setApproved(true);
            }

            if (data.getUsers().get(i).getUsername() != null) {
                User user = userRepository.findOneByUsername(data.getUsers().get(i).getUsername());
                reservation.setUser(user);
                user.getReservations().add(reservation);
                flight.getReservations().add(reservation);
                flightRepository.save(flight);
                userRepository.save(user);
            } else {
                flight.getReservations().add(reservation);
                flightRepository.save(flight);
            }

        }

        return new FlightDTO(flight);
    }

    @Transactional(readOnly = false)
    public FlightDTO addSeatsToFastReservation(Long flightId, List<SeatDTO> seats) {
        Flight flight = flightRepository.findOneById(flightId);

        for (SeatDTO seat : seats) {
            if (!checkIfSeatIsFree(seat, flight))
                return null;

            FlightReservation reservation = new FlightReservation();
            reservation.setReservationDate(new Date());
            reservation.setPrice(seat.getPrice());
            reservation.setApproved(false);
            reservation.setPassport("fast");
            reservation.setFirstName("fast");
            reservation.setLastName("fast");
            reservation.setSeatRow(seat.getX());
            reservation.setSeatColumn(seat.getY());
            reservation.setFastReservation(true);

            flight.getReservations().add(reservation);
            flightRepository.save(flight);
        }

        return new FlightDTO(flight);
    }

    private boolean checkIfSeatIsFree(SeatDTO seat, Flight flight) {
        for (FlightReservation res : flight.getReservations()) {
            if (res.getSeatRow() == seat.getX() && res.getSeatColumn() == seat.getY()) {
                return false;
            }
        }

        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public FlightDTO reserveFastTicket(Long flightId, FlightFastReservationDataDTO reservationInfo) {
        Flight flight = flightRepository.findOneById(flightId);
        User user = userRepository.findOneByUsername(reservationInfo.getUsername());

        FlightReservation reservation = flight.getReservations().stream()
                .filter(flightReservation -> flightReservation.getId().equals(reservationInfo.getReservationId()))
                .findFirst().get();

        if (reservation == null)
            return null;

        reservation.setApproved(true);
        reservation.setFastReservation(false);
        reservation.setReservationDate(new Date());
        reservation.setPassport(reservationInfo.getPassport());
        reservation.setFirstName(user.getFirstName());
        reservation.setLastName(user.getLastName());
        reservation.setUser(user);

        user.getReservations().add(reservation);
        flightRepository.save(flight);
        userRepository.save(user);

        return new FlightDTO(flight);
    }

    public List<FlightDTO> searchAndFilterFlights(FlightFilterDTO filter) {
        List<FlightDTO> flights = this.getAllFlights();

        if (filter.getAirlineId() != null)
            flights = flights.stream().filter(f -> f.getAirlineId().equals(filter.getAirlineId())).collect(Collectors.toList());
        if (filter.getStartDestination() != null)
            flights = flights.stream().filter(f -> f.getStartDestination().equals(filter.getStartDestination())).collect(Collectors.toList());
        if (filter.getEndDestination() != null)
            flights = flights.stream().filter(f -> f.getEndDestination().equals(filter.getEndDestination())).collect(Collectors.toList());
        if (filter.getStartDate() != null)
            flights = flights.stream().filter(f -> f.getStartDateTime().after(filter.getStartDate())).collect(Collectors.toList());
        if (filter.getEndDate() != null)
            flights = flights.stream().filter(f -> f.getEndDateTime().before(filter.getEndDate())).collect(Collectors.toList());
        if (filter.getTravelTime() != null)
            flights = flights.stream().filter(f -> f.getTravelTime() <= filter.getTravelTime()).collect(Collectors.toList());
        if (filter.getTravelLength() != null)
            flights = flights.stream().filter(f -> f.getTravelLength() <= filter.getTravelLength()).collect(Collectors.toList());

        if (filter.getIsDirect() != null)
            if (filter.getIsDirect())
                flights = flights.stream().filter(f -> f.getTransits().isEmpty()).collect(Collectors.toList());

        if (filter.getIsOneWay() != null)
            if (filter.getIsOneWay())
                flights = flights.stream().filter(f -> f.isOneWay()).collect(Collectors.toList());

        if (filter.getNumOfPeople() != null)
            flights = flights.stream().filter(f -> f.getReservations().size() + filter.getNumOfPeople() <= f.getAirplane().getNumOfSeats()).collect(Collectors.toList());

        if (filter.getMinPrice() != null)
            flights = flights.stream().filter(f -> {
                for (AirlinePriceListItemDTO item : f.getPrices()) {
                    if (item.getPrice() <= filter.getMinPrice())
                        return false;
                }
                return true;
            }).collect(Collectors.toList());

        if (filter.getMaxPrice() != null)
            flights = flights.stream().filter(f -> {
                for (AirlinePriceListItemDTO item : f.getPrices()) {
                    if (item.getPrice() <= filter.getMaxPrice())
                        return true;
                }
                return false;
            }).collect(Collectors.toList());


        return flights;
    }

}
