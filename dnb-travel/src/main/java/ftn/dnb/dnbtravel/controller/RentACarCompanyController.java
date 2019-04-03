package ftn.dnb.dnbtravel.controller;

import com.sun.mail.iap.Response;
import ftn.dnb.dnbtravel.dto.RentACarCompanyDTO;
import ftn.dnb.dnbtravel.service.RentACarCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RentACarCompanyDTO> getRentACarCompanyById(@PathVariable("id") Long id){
        RentACarCompanyDTO rentACarCompany = rentACarCompanyService.getRentACarCompanyById(id);
        return new ResponseEntity<>(rentACarCompany,(rentACarCompany == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PutMapping(path = "")
    public ResponseEntity<RentACarCompanyDTO> updateRentaCarById(@RequestBody RentACarCompanyDTO rentACarCompanyToEdit){
        RentACarCompanyDTO rentACarCompany = rentACarCompanyService.updateRentACarCompany(rentACarCompanyToEdit);
        return new ResponseEntity<>(rentACarCompany, (rentACarCompany == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

}