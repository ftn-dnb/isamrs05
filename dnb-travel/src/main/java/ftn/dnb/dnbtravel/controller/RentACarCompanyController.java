package ftn.dnb.dnbtravel.controller;

import com.sun.mail.iap.Response;
import ftn.dnb.dnbtravel.dto.CarFilterDTO;
import ftn.dnb.dnbtravel.dto.RACListItemDTO;
import ftn.dnb.dnbtravel.dto.RentACarCompanyDTO;
import ftn.dnb.dnbtravel.service.RentACarCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/rentACarCompanies", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8000")
public class RentACarCompanyController {

    private RentACarCompanyService rentACarCompanyService;

    @Autowired
    public RentACarCompanyController(RentACarCompanyService rentACarCompanyService){
        this.rentACarCompanyService = rentACarCompanyService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentACarCompanyDTO> addRentACarCompany(@RequestBody RentACarCompanyDTO racToInsert){
        RentACarCompanyDTO savedRentACarCompany = rentACarCompanyService.addRentACarCompany(racToInsert);
        return new ResponseEntity<RentACarCompanyDTO>(savedRentACarCompany, HttpStatus.OK);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<RentACarCompanyDTO>> getAllRentACarCompanies(){
        return new ResponseEntity<>(rentACarCompanyService.getAllRentACarCompanies(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<RentACarCompanyDTO> getRentACarCompanyById(@PathVariable("id") Long id){
        RentACarCompanyDTO rentACarCompany = rentACarCompanyService.getRentACarCompanyById(id);
        return new ResponseEntity<>(rentACarCompany,(rentACarCompany == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<RentACarCompanyDTO> updateRentaCarById(@RequestBody RentACarCompanyDTO rentACarCompanyToEdit){
        RentACarCompanyDTO rentACarCompany = rentACarCompanyService.updateRentACarCompany(rentACarCompanyToEdit);
        return new ResponseEntity<>(rentACarCompany, (rentACarCompany == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(path = "/carSearch")
    public ResponseEntity<?> searchCars(@RequestBody CarFilterDTO filter){
        List<RACListItemDTO> items = rentACarCompanyService.searchCar(filter);
        return new ResponseEntity<>(items,HttpStatus.OK);
    }

}
