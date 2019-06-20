package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.*;
import ftn.dnb.dnbtravel.model.*;
import ftn.dnb.dnbtravel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RentACarCompanyService {

    @Autowired
    private RentACarCompanyRepository racRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentACarPriceListItem priceListItemRepository;

    @Autowired
    private RACReservationRepository reservationRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RACBranchOfficeRepository branchOfficeRepository;

    public List<RentACarCompanyDTO> getAllRentACarCompanies(){
        List<RentACarCompany> companies = racRepository.findAll();
        List<RentACarCompanyDTO> dtos = new ArrayList<>();

        for (RentACarCompany c: companies) {
            BranchOffice b = c.getMainOffice();
            RentACarCompanyDTO dto = new RentACarCompanyDTO(c);
            dtos.add(dto);
        }

        return dtos;
    }

    public RentACarCompanyDTO getRentACarCompanyById(Long id){
        RentACarCompany rentACarCompany = racRepository.findOneById(id);

        if(rentACarCompany == null)
            return null;
        RentACarCompanyDTO rentACarCompanyDTO = new RentACarCompanyDTO(rentACarCompany);

        return rentACarCompanyDTO;
    }

    public RentACarCompanyDTO addRentACarCompany(RentACarCompanyDTO rentACarCompany){
        RentACarCompany savedRentACarCompany = racRepository.save(new RentACarCompany(rentACarCompany));
        if (savedRentACarCompany == null) {
            return null;
        }
        User admin = userRepository.findByUsername(rentACarCompany.getAdministrator().getUsername());
        savedRentACarCompany.setAdministrator(admin);
        racRepository.save(savedRentACarCompany);
        return new RentACarCompanyDTO(savedRentACarCompany);
    }

    public RentACarCompanyDTO updateRentACarCompany(RentACarCompanyDTO rentACarCompany){
        RentACarCompany savedRentACarCompany = racRepository.findOneById(rentACarCompany.getId());

        if(savedRentACarCompany == null)
            return null;

        savedRentACarCompany.setDescription(rentACarCompany.getDescription());
        savedRentACarCompany.setName(rentACarCompany.getName());

        racRepository.save(savedRentACarCompany);


        return new RentACarCompanyDTO(savedRentACarCompany);
    }

    public List<RACListItemDTO> getAllItems(Long id){

        List<RACPriceListItem> items = new LinkedList<>();
        RentACarCompany company = racRepository.findOneById(id);
            if(company.getCurrentPriceList() != null){
                for(RACPriceListItem real_item: company.getCurrentPriceList().getItems()){
                    items.add(real_item);
                }
            }
        List<RACListItemDTO> dtos = new ArrayList<>();
        items.stream().forEach(item -> dtos.add(new RACListItemDTO(item)));
        return dtos;
    }

    public List<RACListItemDTO> searchCar(CarFilterDTO filter){
        List<RACListItemDTO> list = this.getAllItems(filter.getId());
        
        //start date
        if (filter.getStartDate() != null) {
            filter.getStartDate().setHours(0);
            list = list.stream().filter(f -> f.getStartDate().after(filter.getStartDate())).collect(Collectors.toList());
        }
        //end date
        if(filter.getEndDate() != null) {
            filter.getEndDate().setHours(0);
            list = list.stream().filter(f -> f.getEndDate().before(filter.getEndDate())).collect(Collectors.toList());
        }
        //price per day max price
        if(filter.getPricePerDay() != null)
            list = list.stream().filter(f->{
                    if(f.getPricePerDay() <= filter.getPricePerDay())
                        return true;
                return false;
            }).collect(Collectors.toList());

        //seats number
        if(filter.getSeatsNumber() != null)
            list = list.stream().filter(f->{
                if(f.getCar().getSeatsNumber() >= filter.getSeatsNumber())
                    return true;
                return false;
            }).collect(Collectors.toList());

        //type
        if(filter.getType() != null)
            list = list.stream().filter(f->{
                if(f.getCar().getType() == filter.getType())
                    return true;
                return false;
            }).collect(Collectors.toList());

        //brand
        if(filter.getBrand() != null)
            list = list.stream().filter(f->{
                if(f.getCar().getBrand().toLowerCase().equals(filter.getBrand().toLowerCase()))
                    return true;
                return false;
            }).collect(Collectors.toList());

        return list;
    }

    public RentACarCompanyDTO getRentACarCompanyByAdministrator(String username){


        User user = userRepository.findByUsername(username);

        RentACarCompany rentACarCompany = racRepository.findOneByAdministrator(user);

        // ubaci ako je null

        return  new RentACarCompanyDTO(rentACarCompany);
    }

    public CarDTO addNewCar(CarDTO car){
        RentACarCompany company = racRepository.findOneById(car.getCompany().getId());
        Car savedCar = new Car(car);
        savedCar.setCompany(company);

        company.getCars().add(savedCar);
        racRepository.save(company);

        return new CarDTO(savedCar);
    }

    public ResponseEntity<?> addReservation(RACListItemDTO item){
        RentACarCompany racToAdd = racRepository.findOneById(item.getCar().getCompany().getId());
        Car carToAdd = carRepository.findOneById(item.getCar().getId());


        RACPriceList priceList = racToAdd.getCurrentPriceList();

        for (RACPriceListItem real_item: priceList.getItems()) {
                if(real_item.getCar().getId() == item.getCar().getId()){
                    if(item.getStartDate().after(real_item.getStartDate()) && item.getStartDate().before(real_item.getEndDate())){
                        return new ResponseEntity<>("Start date problem",HttpStatus.CONFLICT);
                    }
                    else if (item.getEndDate().after(real_item.getStartDate()) && item.getEndDate().before(real_item.getEndDate())){
                        return new ResponseEntity<>("End date problem",HttpStatus.CONFLICT);
                    }
                }
        }

        RACPriceListItem itemToAdd = new RACPriceListItem(item.getActiveDiscount(),item.getStartDate(),item.getEndDate(),
                item.getPricePerDay(),racToAdd.getCurrentPriceList(),carToAdd);

        racToAdd.getCurrentPriceList().getItems().add(itemToAdd);
        racRepository.save(racToAdd);

        return new ResponseEntity<>("New reservation added",HttpStatus.OK);
    }

    public ResponseEntity <?> setActivePriceList(Long copmany_id, Long price_list_id){

        RentACarCompany company = racRepository.findOneById(copmany_id);
        RACPriceList listToSet = null;
        for(RACPriceList  l : company.getPriceList()){
            if(l.getId() == price_list_id){
                listToSet = l;
                break;
            }
        }
        if(listToSet == null){

            return new ResponseEntity<>("List set failed", HttpStatus.CONFLICT);
        }

        company.setCurrentPriceList(listToSet);
        racRepository.save(company);
        return new ResponseEntity<>("New price list is " + listToSet.getName(), HttpStatus.OK);
    }

    public ResponseEntity<?> addPriceList(RACSetPriceListDTO addList){
        RentACarCompany company = racRepository.findOneById(addList.getCompany_id());

        RACPriceList priceList = new RACPriceList();
        priceList.setName(addList.getName());
        priceList.setCompany(company);

        company.getPriceList().add(priceList);
        racRepository.save(company);
        // dodaj proveru
        return new ResponseEntity<>("Price list added", HttpStatus.OK);

    }

    public ResponseEntity<?> addBranchOffice(BranchOfficeDTO office){
        RentACarCompany company = racRepository.findOneById(office.getCompanyDTO().getId());

        if(company == null){
            return  new ResponseEntity<>("Unable to add office",HttpStatus.CONFLICT);
        }

        BranchOffice newOffice = new BranchOffice(office.getName(),office.getAddress(),company);

        company.getOffices().add(newOffice);

        racRepository.save(company);

        return  new ResponseEntity<>("New office added",HttpStatus.OK);
    }

    public ResponseEntity<?> setMainOffice(BranchOfficeDTO office){
        RentACarCompany company = racRepository.findOneById(office.getCompanyDTO().getId());

        if(company == null){
            return  new ResponseEntity<>("Unable to set office",HttpStatus.CONFLICT);
        }

        for(BranchOffice b: company.getOffices()){
           if( b.getId() == office.getId()){
               company.setMainOffice(b);
               break;
           }
        }

        racRepository.save(company);

        return new ResponseEntity<>("New main office",HttpStatus.OK);
    }

    public List<BranchOfficeDTO> getAllOffices(RentACarCompanyDTO companyDTO){
        RentACarCompany company = racRepository.findOneById(companyDTO.getId());
        List<BranchOfficeDTO> officeDTOS = new ArrayList<>();
        for(BranchOffice b : company.getOffices()){
             BranchOfficeDTO fake_office = new BranchOfficeDTO(b);
             officeDTOS.add(fake_office);
        }
        return officeDTOS;

    }

    public ResponseEntity<?> reserveCar(RACReservationRequestDTO requestDTO){
        RACPriceListItem item = priceListItemRepository.findOneById(requestDTO.getItemID());
        User user = userRepository.findOneByUsername(requestDTO.getUser());
        RentACarCompany company = racRepository.findOneById(requestDTO.getCompanyID());

        RACReservation realReservation = new RACReservation();

        realReservation.setBeginDate(requestDTO.getBeginDate());
        realReservation.setEndDate(requestDTO.getEndDate());
        realReservation.setRating(0);
        realReservation.setItem(item);
        realReservation.setUser(user);


        // provera da li je zauzeto

        user.getRacReservations().add(realReservation);
        company.getRacReservations().add(realReservation);

        racRepository.save(company);

        // dodavanje korisniku/kompaniji
        // odgovor
        return new ResponseEntity<>("Reservation successfully added", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCar(CarDTO carDTO){

        Car realCar  = carRepository.findOneById(carDTO.getId());

        List<RACPriceListItem> list = priceListItemRepository.findByCar(realCar);

        if(list == null || list.size() == 0){

            RentACarCompany company = realCar.getCompany();
            company.getCars().remove(realCar);
            racRepository.save(company);


            realCar.setCompany(null);
            carRepository.save(realCar);

            return  new ResponseEntity<>("Car is deleted", HttpStatus.OK);
        }
        {
            return  new ResponseEntity<>("Unable to delte car", HttpStatus.CONFLICT);
        }


    }

    public ResponseEntity<?> editCar(CarDTO carDTO) {

        Car realCar = carRepository.findOneById(carDTO.getId());

        List<RACPriceListItem> list = priceListItemRepository.findByCar(realCar);

        if (list == null || list.size() == 0) {
            realCar.setBrand(carDTO.getBrand());
            realCar.setManufYear(carDTO.getManufYear());
            realCar.setName(carDTO.getName());
            realCar.setSeatsNumber(carDTO.getSeatsNumber());
            realCar.setType(carDTO.getType());

            carRepository.save(realCar);

            return new ResponseEntity<>("Car is edited", HttpStatus.OK);
        }
        {
            return new ResponseEntity<>("Unable to edit car", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> changeBranchOffice(BranchOfficeDTO officeDTO){

        if(officeDTO.getAddress() == null || officeDTO.getName() == null){
            return new ResponseEntity("Corupted data", HttpStatus.CONFLICT);
        }

        RentACarCompany company = racRepository.findOneById(officeDTO.getCompanyDTO().getId());

        if(company == null){
            return new ResponseEntity("Error finding branch company", HttpStatus.CONFLICT);
        }

        BranchOffice editOffice = branchOfficeRepository.findOneById(officeDTO.getId());

        if(editOffice == null){ // nije pronasao taj office
            return new ResponseEntity("Error finding branch office", HttpStatus.CONFLICT);
        }


        editOffice.setName(officeDTO.getName());
        editOffice.setAddress(officeDTO.getAddress());

        racRepository.save(company);

        return new ResponseEntity<>("Successful edit", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteBranchOffice(BranchOfficeDTO officeDTO){
        if(officeDTO.getId() == null) {
            return new ResponseEntity("Corupted data", HttpStatus.CONFLICT);
        }

        RentACarCompany company = racRepository.findOneById(officeDTO.getCompanyDTO().getId());

        if(company == null){
            return new ResponseEntity("Error finding branch company", HttpStatus.CONFLICT);
        }

        if(company.getOffices().size() <= 1 ){
            return new ResponseEntity("This is the only office, unable to delete", HttpStatus.CONFLICT);
        }

        if(company.getMainOffice().getId() == officeDTO.getId()){
            for(BranchOffice b : company.getOffices()){
                if(b.getId() != officeDTO.getId()){
                    company.setMainOffice(b);
                    break;
                }
            }
        }

        BranchOffice office = branchOfficeRepository.findOneById(officeDTO.getId());

        company.getOffices().remove(office);
        racRepository.save(company);

        office.setCompany(null);
        branchOfficeRepository.save(office);

        return new ResponseEntity<>("Branch office deleted", HttpStatus.OK);

    }

    public RACStatsDTO getIncome(RACIncomeSearchDataDTO filter){
        RACStatsDTO stats = new RACStatsDTO();
        LinkedHashMap<String,Float> income = new LinkedHashMap<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        RentACarCompany company = racRepository.findOneById(filter.getId());

        for(RACReservation reservation: company.getRacReservations() ){
           if(reservation.getEndDate().after(filter.getBeginDate()) && reservation.getEndDate().before(filter.getEndDate())){
               String dataKey = formatter.format(reservation.getEndDate());

               long diff = reservation.getEndDate().getTime() - reservation.getBeginDate().getTime();
               diff = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

               float price = diff  * reservation.getItem().getPricePerDay();
               if(income.containsKey(dataKey)){
                    income.put(dataKey,income.get(dataKey) + price);
               }
               else{
                   income.put(dataKey, price);
               }
            }
        }

        income.forEach((key, value) -> stats.getIncome().add(new IncomeDTO(key, value)));
        return stats;
    }

    public List<ReservationStatsDTO> getStatsForCompanyReservation(RACReservationStatsDTO filter){
        RentACarCompany company = racRepository.findOneById(filter.getId());

        switch (filter.getReservationCriteria()){
            case "Day":
                return getReservationStatsDay(company, filter.getDateReservation());
            case "Week":
                return getReservationStatsWeek(company, filter.getDateReservation());
            case "Month":
                return getReservationStatsMonth(company, filter.getDateReservation());
            default:
                return getReservationStatsWeek(company, filter.getDateReservation());
        }
    }

    private List<ReservationStatsDTO> getReservationStatsDay(RentACarCompany company, Date date){
        List<ReservationStatsDTO> stats = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        stats.add(new ReservationStatsDTO(formatter.format(date), 0));

        LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for(RACReservation reservation: company.getRacReservations()){
            LocalDate resDate = reservation.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(startDate.isEqual(resDate)){
                stats.get(0).setNumber(stats.get(0).getNumber()+1);
            }
        }
        return stats;
    }

    private List<ReservationStatsDTO> getReservationStatsWeek(RentACarCompany company, Date date){
        List<ReservationStatsDTO> statsList = new ArrayList<>();
        LinkedHashMap<String,Integer>  stats = new LinkedHashMap<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");


        LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        startDate = startDate.minusDays(startDate.getDayOfWeek().getValue());
        endDate = endDate.plusDays(7 - endDate.getDayOfWeek().getValue());

        for(RACReservation reservation: company.getRacReservations()){
            LocalDate reservationDate = reservation.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (reservationDate.isAfter(startDate) && reservationDate.isBefore(endDate)) {
                String key = formatter.format(reservation.getBeginDate());
                if (stats.containsKey(key))
                    stats.put(key, stats.get(key) + 1);
                else
                    stats.put(key, 1);
            }
        }

        stats.forEach((key, value) -> statsList.add(new ReservationStatsDTO(key, value)));
        return statsList;

    }

    private List<ReservationStatsDTO> getReservationStatsMonth(RentACarCompany company, Date date){
        List<ReservationStatsDTO> statsList = new ArrayList<>();
        LinkedHashMap<String,Integer>  stats = new LinkedHashMap<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        LocalDate filterDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int monthFilterDate = filterDate.getMonthValue();

        for(RACReservation reservation : company.getRacReservations()){
            LocalDate reservationDate = reservation.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (reservationDate.getMonthValue() == monthFilterDate) {
                String key = formatter.format(reservation.getBeginDate());

                if (stats.containsKey(key))
                    stats.put(key, stats.get(key) + 1);
                else
                    stats.put(key, 1);
            }
        }

        stats.forEach((key, value) -> statsList.add(new ReservationStatsDTO(key, value)));
        return statsList;
    }

    public ResponseEntity<?> cancelCarReservation(CancelReservationCarDTO request) {
        User user = userRepository.findOneById(request.getId());
        RentACarCompany company = racRepository.findOneById(request.getCompanyId());

        if (user == null || company == null) {
            return new ResponseEntity<>("Cancelation error", HttpStatus.CONFLICT);
        }

        for (RACReservation reservation : user.getRacReservations()) {
            if (reservation.getId() == request.getReservationId()) {
                user.getRacReservations().remove(reservation);
                userRepository.save(user);
                break;
            }
        }

        for (RACReservation reservation : company.getRacReservations()) {
            if (reservation.getId() == request.getReservationId()) {
                company.getRacReservations().remove(reservation);
                racRepository.save(company);
                break;

            }


        }

        return new ResponseEntity<>("Cancelation ok", HttpStatus.OK);
    }

    public List<RacReservationDTO> getCarReservationsUser(UserDTO user){
        User real_user = userRepository.findByUsername(user.getUsername());
        List<RacReservationDTO> list = new LinkedList<>();


        for(RACReservation reservation: real_user.getRacReservations()){
            RacReservationDTO fakeR = new RacReservationDTO();

            fakeR.setBegin(reservation.getBeginDate());
            fakeR.setEnd(reservation.getEndDate());
            fakeR.setId(reservation.getId());
            fakeR.setItem_id(reservation.getItem().getId());
            fakeR.setCompany_id(reservation.getItem().getCar().getCompany().getId());

            list.add(fakeR);
        }

        return list;
    }




}
