package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.FlightDTO;
import ftn.dnb.dnbtravel.dto.FlightFilterDTO;
import ftn.dnb.dnbtravel.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<FlightDTO>(flight, (flight == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(path="")
    public ResponseEntity<List<FlightDTO>> searchAndFilterFlights(@RequestBody FlightFilterDTO filter) {
        List<FlightDTO> results = flightService.searchAndFilterFlights(filter);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
