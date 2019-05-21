package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.*;
import ftn.dnb.dnbtravel.model.Car;
import ftn.dnb.dnbtravel.model.RACPriceListItem;
import ftn.dnb.dnbtravel.model.RentACarCompany;
import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.repository.RentACarCompanyRepository;
import ftn.dnb.dnbtravel.repository.RentACarPriceListItem;
import ftn.dnb.dnbtravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentACarCompanyService {

    @Autowired
    private RentACarCompanyRepository racRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentACarPriceListItem priceListItemRepository;

    public List<RentACarCompanyDTO> getAllRentACarCompanies(){
        List<RentACarCompany> companies = racRepository.findAll();
        List<RentACarCompanyDTO> dtos = new ArrayList<>();
        companies.stream().forEach(company -> dtos.add(new RentACarCompanyDTO(company)));
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

    public List<RACListItemDTO> getAllItems(){
        List<RACPriceListItem> items = priceListItemRepository.findAll();
        List<RACListItemDTO> dtos = new ArrayList<>();
        items.stream().forEach(item -> dtos.add(new RACListItemDTO(item)));
        return dtos;
    }

    public List<RACListItemDTO> searchCar(CarFilterDTO filter){
        List<RACListItemDTO> list = this.getAllItems();

        //start date
        if (filter.getStartDate() != null)
            list = list.stream().filter(f -> f.getStartDate().after(filter.getStartDate())).collect(Collectors.toList());

        //end date
        if(filter.getEndDate() != null)
            list = list.stream().filter(f -> f.getEndDate().before(filter.getEndDate())).collect(Collectors.toList());

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
}
