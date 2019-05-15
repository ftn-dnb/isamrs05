package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Address;
import ftn.dnb.dnbtravel.model.RentACarCompany;
import ftn.dnb.dnbtravel.model.User;

public class RentACarCompanyDTO {

    private long id;
    private String name;
    private String description;
    private double rating;
    private UserDTO administrator;

    public RentACarCompanyDTO(){
        super();
    }

    public RentACarCompanyDTO(long id, String name, String description, double rating, Address address, UserDTO administrator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.administrator = administrator;
    }

    public RentACarCompanyDTO(RentACarCompany rentACarCompany){
        this.id = rentACarCompany.getId();
        this.name = rentACarCompany.getName();
        this.description = rentACarCompany.getDescription();
        this.rating = rentACarCompany.getRating();
        this.administrator = new UserDTO(rentACarCompany.getAdministrator());
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

    public UserDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(UserDTO administrator) {
        this.administrator = administrator;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
