package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.BranchOffice;

public class BranchOfficeDTO {

    private Long id;
    private String name;
    private Address address;
    private RentACarCompanyDTO companyDTO;


    public BranchOfficeDTO(){
    }

    public BranchOfficeDTO(Long id, String name, Address address, RentACarCompanyDTO companyDTO) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.companyDTO = companyDTO;
    }

    public BranchOfficeDTO(BranchOffice office){
        this.id = office.getId();
        this.name = office.getName();
        this.address = office.getAddress();

        this.companyDTO = new RentACarCompanyDTO(office.getCompany());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RentACarCompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(RentACarCompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }
}
