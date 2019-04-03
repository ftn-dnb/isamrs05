package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.RentACarCompanyDTO;
import ftn.dnb.dnbtravel.model.RentACarCompany;
import ftn.dnb.dnbtravel.repository.RentACarCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentACarCompanyService {

    @Autowired
    private RentACarCompanyRepository racRepository;

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
}
