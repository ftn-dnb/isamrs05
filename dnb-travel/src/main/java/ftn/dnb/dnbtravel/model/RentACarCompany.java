package ftn.dnb.dnbtravel.model;

import ftn.dnb.dnbtravel.dto.RentACarCompanyDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RentACarCompany {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    //?

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Car>cars;
    //private Set<RACPriceList> priceList;
    //private Set<BranchOffice> offices;
    //private BranchOffice mainOffice;

    public RentACarCompany() {
        super();
    }

    public RentACarCompany(Long id, String name, String description, Set<Car> cars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cars = cars;
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

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        cars = cars;
    }
}
