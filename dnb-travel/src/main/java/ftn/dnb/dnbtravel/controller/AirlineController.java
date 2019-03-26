package ftn.dnb.dnbtravel.controller;

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
public class AirlineController {

    private AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Airline> addAirline(@RequestBody Airline airlineToInsert) {
        Airline savedAirline = airlineService.addAirline(airlineToInsert);
        return new ResponseEntity<Airline>(savedAirline, HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    public List<Airline> getAllAirlines() {
        return airlineService.getAllAirlines();
    }

    @GetMapping(path = "/{id}")
    public Airline getAirlineById(@PathVariable("id") Long id) {
        return airlineService.getAirlineById(id);
    }

    @PutMapping(path = "/{id}")
    public Airline updateAirlineById(@PathVariable("id") Long id, @RequestBody Airline airlineToEdit) {
        airlineToEdit.setId(id);
        return airlineService.updateAirline(airlineToEdit);
    }
}
