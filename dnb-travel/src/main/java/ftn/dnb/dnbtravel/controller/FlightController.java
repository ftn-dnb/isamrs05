package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.*;
import ftn.dnb.dnbtravel.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/flights", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8000")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping(path = "")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        FlightDTO flight = flightService.getFlightById(id);
        return new ResponseEntity<>(flight, (flight == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(path="")
    public ResponseEntity<List<FlightDTO>> searchAndFilterFlights(@RequestBody FlightFilterDTO filter) {
        List<FlightDTO> results = flightService.searchAndFilterFlights(filter);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping(path = "/{flightId}/reserve")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FlightDTO> reserveTickets(@PathVariable Long flightId, @RequestBody FlightReservationDataDTO data) {
        FlightDTO flight = flightService.reserveTickets(flightId, data);
        return new ResponseEntity<>((flight == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @PostMapping(path = "/{flightId}/fastSeats")
    @PreAuthorize("hasRole('AIRLINE_ADMIN')")
    public ResponseEntity<FlightDTO> addSeatsToFastReservation(@PathVariable Long flightId, @RequestBody List<SeatDTO> seats) {
        FlightDTO flight = flightService.addSeatsToFastReservation(flightId, seats);
        return new ResponseEntity<>(flight, (flight == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @PostMapping(path = "/{flightId}/reserveFast")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FlightDTO> reserveFastTicket(@PathVariable Long flightId, @RequestBody FlightFastReservationDataDTO info) {
        FlightDTO flight = flightService.reserveFastTicket(flightId, info);
        return new ResponseEntity<>(flight, (flight == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @PostMapping(path = "/cancelReservation")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> cancelReservation(@RequestBody CancelFlightDTO data){
        ResponseEntity<?> response = flightService.cancelFlight(data);
        return response;
    }

    @PostMapping(path = "/listAllReservations")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> listReservations(@RequestBody UserDTO user){
        ResponseEntity<?> response = flightService.getAllReservations(user);
        return response;
    }
}
