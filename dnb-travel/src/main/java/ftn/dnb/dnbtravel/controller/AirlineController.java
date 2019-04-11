package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.AirlineDTO;
import ftn.dnb.dnbtravel.dto.FlightDTO;
import ftn.dnb.dnbtravel.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/airlines", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8000")
public class AirlineController {

    private AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AirlineDTO> addAirline(@RequestBody AirlineDTO airlineToInsert) {
        AirlineDTO savedAirline = airlineService.addAirline(airlineToInsert);
        return new ResponseEntity<AirlineDTO>(savedAirline, HttpStatus.CREATED); // @TODO: mozda dodati proveru za null ? koji http kod onda vratiti ?
    }

    @GetMapping(path = "")
    public ResponseEntity<List<AirlineDTO>> getAllAirlines() {
        return new ResponseEntity<>(airlineService.getAllAirlines(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AirlineDTO> getAirlineById(@PathVariable("id") Long id) {
        AirlineDTO airline = airlineService.getAirlineById(id);
        return new ResponseEntity<>(airline, (airline == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PutMapping(path = "")
    public ResponseEntity<AirlineDTO> updateAirlineById(@RequestBody AirlineDTO airlineToEdit) {
        AirlineDTO airline = airlineService.updateAirline(airlineToEdit);
        return new ResponseEntity<>(airline, (airline == null) ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @PostMapping(path = "/{id}/flights")
    public ResponseEntity<FlightDTO> addFlight(@PathVariable Long id, @RequestBody FlightDTO flightToAdd) {
        FlightDTO savedFlight = airlineService.addFlight(id, flightToAdd);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }
}
