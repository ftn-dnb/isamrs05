package ftn.dnb.dnbtravel.model;

import ftn.dnb.dnbtravel.dto.RentACarCompanyDTO;

import javax.persistence.*;

@Entity
public class RentACarCompany {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public RentACarCompany() {
        super();
    }

    public RentACarCompany(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public RentACarCompany(RentACarCompanyDTO rentACarCompanyDTO){
        this.id = rentACarCompanyDTO.getId();
        this.name = rentACarCompanyDTO.getName();
        this.description = rentACarCompanyDTO.getDescription();
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
