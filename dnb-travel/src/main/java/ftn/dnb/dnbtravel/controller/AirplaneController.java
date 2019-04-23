package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.AirplaneDTO;
import ftn.dnb.dnbtravel.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/airplanes", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8000")
public class AirplaneController {

    @Autowired
    private AirplaneService airplaneService;

    @GetMapping(path = "")
    public ResponseEntity<List<AirplaneDTO>> getAllAirplanes() {
        List<AirplaneDTO> airplanes = airplaneService.getAllAirplanes();
        return new ResponseEntity<>(airplanes, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AirplaneDTO> getAirplaneById(@PathVariable("id") Long id) {
        AirplaneDTO airplane = airplaneService.getAirplaneById(id);
        return new ResponseEntity<>(airplane, (airplane == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
