package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.BranchOffice;
import org.springframework.mail.MailAuthenticationException;

public class MainOfficeDTO {

    private Long id;
    private String name;
    private Address address;

    public MainOfficeDTO(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public MainOfficeDTO(BranchOffice mainOffice){
        this.id = mainOffice.getId();
        this.name = mainOffice.getName();
        this.address = mainOffice.getAddress();
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
}
