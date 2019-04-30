package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.CarFilterDTO;
import ftn.dnb.dnbtravel.dto.RACListItemDTO;
import ftn.dnb.dnbtravel.dto.RentACarCompanyDTO;
import ftn.dnb.dnbtravel.model.RACPriceListItem;
import ftn.dnb.dnbtravel.model.RentACarCompany;
import ftn.dnb.dnbtravel.repository.RentACarCompanyRepository;
import ftn.dnb.dnbtravel.repository.RentACarPriceListItem;
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

        if (savedRentACarCompany == null)
            return null;

        return new RentACarCompanyDTO(savedRentACarCompany);
    }

    public RentACarCompanyDTO updateRentACarCompany(RentACarCompanyDTO rentACarCompany){
        RentACarCompany savedRentACarCompany = racRepository.save(new RentACarCompany(rentACarCompany));

        if(savedRentACarCompany == null)
            return null;

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

        if (filter.getStartDate() != null)
            list = list.stream().filter(f -> f.getStartDate().after(filter.getStartDate())).collect(Collectors.toList());


        if(filter.getEndDate() != null)
            list = list.stream().filter(f -> f.getEndDate().before(filter.getEndDate())).collect(Collectors.toList());

        if(filter.getPricePerDay() != null)
            list = list.stream().filter(f->{
                    if(f.getPricePerDay() <= filter.getPricePerDay())
                        return true;
                return false;
            }).collect(Collectors.toList());


        return list;
    }
}
