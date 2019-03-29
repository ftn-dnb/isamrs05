package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.AirlineDTO;
import ftn.dnb.dnbtravel.model.Airline;
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
        return new ResponseEntity<AirlineDTO>(savedAirline, HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    public List<AirlineDTO> getAllAirlines() {
        return airlineService.getAllAirlines();
    }

    @GetMapping(path = "/{id}")
    public AirlineDTO getAirlineById(@PathVariable("id") Long id) {
        return airlineService.getAirlineById(id);
    }

    @PutMapping(path = "")
    public AirlineDTO updateAirlineById(@RequestBody AirlineDTO airlineToEdit) {
        return airlineService.updateAirline(airlineToEdit);
    }
}
