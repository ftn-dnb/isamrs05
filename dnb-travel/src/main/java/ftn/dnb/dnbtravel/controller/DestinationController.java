package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.DestinationDTO;
import ftn.dnb.dnbtravel.model.Destination;
import ftn.dnb.dnbtravel.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/destinations", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8000")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @GetMapping(path = "")
    public ResponseEntity<List<Destination>> getAllDestination() {
        return new ResponseEntity<>(destinationService.getAllDestinations(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable("id") Long id) {
        Destination destination = destinationService.getDestinationById(id);
        return new ResponseEntity<>(destination, (destination == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(path = "")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<DestinationDTO> addDestination(@RequestBody DestinationDTO destinationToAdd) {
        DestinationDTO destination = destinationService.addDestination(destinationToAdd);
        return new ResponseEntity<>(destination, (destination == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }
}
