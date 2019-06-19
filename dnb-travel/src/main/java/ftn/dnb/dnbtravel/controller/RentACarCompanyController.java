package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.*;
import ftn.dnb.dnbtravel.service.RentACarCompanyService;
import ftn.dnb.dnbtravel.service.UserService;
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
    private UserService userService;

    @Autowired
    public RentACarCompanyController(RentACarCompanyService rentACarCompanyService){
        this.rentACarCompanyService = rentACarCompanyService;
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(path = "/company", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> getCompanyForAdmin(@RequestBody UserDTO user){
        RentACarCompanyDTO rentACarCompanyDTO = rentACarCompanyService.getRentACarCompanyByAdministrator(user.getUsername());
        return new ResponseEntity<>(rentACarCompanyDTO, HttpStatus.OK);
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

    @RequestMapping(path= "/addCar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> addCarToCompany(@RequestBody CarDTO car){
        CarDTO response = rentACarCompanyService.addNewCar(car);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @RequestMapping(path = "/addReservation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> addReservation(@RequestBody RACListItemDTO item){
        //RACListItemDTO response = rentACarCompanyService.addReservation(item);
        ResponseEntity<?> response = rentACarCompanyService.addReservation(item);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @RequestMapping(path = "/setPriceList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> setPriceList(@RequestBody RACSetPriceListDTO listDTO){

        ResponseEntity<?> response = rentACarCompanyService.setActivePriceList(listDTO.getCompany_id(),listDTO.getPrice_list_id());
        return response;
    }

    @RequestMapping(path = "/addPriceList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> addPriceList(@RequestBody RACSetPriceListDTO addList){
        //dodaj provere
        ResponseEntity<?> response = rentACarCompanyService.addPriceList(addList);
        return response;
    }

    @RequestMapping(path ="/getAllCompanies", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanies(){
        List<RentACarCompanyDTO> dtos = rentACarCompanyService.getAllRentACarCompanies();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @RequestMapping(path = "/addBranchOffice", method = RequestMethod.POST)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> addBranchOffice(@RequestBody BranchOfficeDTO office){
        ResponseEntity<?> response = rentACarCompanyService.addBranchOffice(office);
        return response;
    }

    @RequestMapping(path = "/setMainBranchOffice", method = RequestMethod.POST)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> setMainBranchOffice(@RequestBody BranchOfficeDTO office){
        ResponseEntity response = rentACarCompanyService.setMainOffice(office);
        return response;
    }

    @RequestMapping(path = "/getAllOffices", method = RequestMethod.POST)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> getAllBranchOffices(@RequestBody RentACarCompanyDTO company){
        List<BranchOfficeDTO> officeDTOS = rentACarCompanyService.getAllOffices(company);
        return new ResponseEntity<>(officeDTOS,HttpStatus.OK);
    }

    @PostMapping(path = "/reserveCar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> reserveCar(@RequestBody RACReservationRequestDTO reservation){
        ResponseEntity<?> response = rentACarCompanyService.reserveCar(reservation);
        return response;
    }

    @PostMapping(path = "/deleteCar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> deleteCar(@RequestBody CarDTO car ){
        ResponseEntity<?> response = rentACarCompanyService.deleteCar(car);
        return response;
    }

    @PostMapping(path = "/editCar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> editCar(@RequestBody CarDTO car ) {
        ResponseEntity<?> response = rentACarCompanyService.editCar(car);
        return response;
    }

    @PostMapping(path = "/changeBranchOffice", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> editBranchOffice(@RequestBody BranchOfficeDTO office){
        ResponseEntity response = rentACarCompanyService.changeBranchOffice(office);
        return response;
    }

    @PostMapping(path = "/deleteBranchOffice", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> deleteBranchOffice(@RequestBody BranchOfficeDTO office){
        ResponseEntity response = rentACarCompanyService.deleteBranchOffice(office);
        return response;
    }

    @PostMapping(path = "/getIncome", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RAC_ADMIN')")
    public ResponseEntity<?> getIncome(@RequestBody RACIncomeSearchDataDTO incomeDTO){
        RACStatsDTO stats = rentACarCompanyService.getIncome(incomeDTO);
        return new ResponseEntity<>(stats,HttpStatus.OK);
    }


}
