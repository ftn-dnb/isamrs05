package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.RentACarCompany;

public class RentACarCompanyDTO {

    private long id;
    private String name;
    private String description;

    public RentACarCompanyDTO(){
        super();
    }

    public RentACarCompanyDTO(long id, String name, String description,Address address) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public RentACarCompanyDTO(RentACarCompany rentACarCompany){
        this.id = rentACarCompany.getId();
        this.name = rentACarCompany.getName();
        this.description = rentACarCompany.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
