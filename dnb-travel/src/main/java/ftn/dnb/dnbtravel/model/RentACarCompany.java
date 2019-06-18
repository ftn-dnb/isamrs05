package ftn.dnb.dnbtravel.model;

import ftn.dnb.dnbtravel.dto.RentACarCompanyDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "rac")
public class RentACarCompany {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rating", nullable = false)
    private double rating;

    @OneToOne(cascade = CascadeType.ALL)
    private RACPriceList currentPriceList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rac_cars",
            joinColumns = @JoinColumn(name = "rac_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
    private Set<Car>cars = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rac_pricelist",
            joinColumns = @JoinColumn(name = "rac_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pricelist_id",referencedColumnName = "id"))
    private Set<RACPriceList> priceList = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rac_offices",
            joinColumns = @JoinColumn(name = "rac_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "office_id", referencedColumnName = "id"))
    private Set<BranchOffice> offices = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL)
    private BranchOffice mainOffice;

    @OneToOne(cascade = CascadeType.ALL)
    private User administrator;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RACReservation> reservations;

    public RentACarCompany() {
        super();
    }

    public RentACarCompany(Long id, String name, String description,
                           double rating, Set<Car> cars, Set<RACPriceList> priceList,
                           Set<BranchOffice> offices, BranchOffice mainOffice, User administrator, Set<RACReservation> racReservations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.cars = cars;
        this.priceList = priceList;
        this.offices = offices;
        this.mainOffice = mainOffice;
        this.administrator = administrator;
        this.reservations = racReservations;
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
        this.cars = cars;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Set<RACPriceList> getPriceList() {
        return priceList;
    }

    public void setPriceList(Set<RACPriceList> priceList) {
        this.priceList = priceList;
    }

    public Set<BranchOffice> getOffices() {
        return offices;
    }

    public void setOffices(Set<BranchOffice> offices) {
        this.offices = offices;
    }

    public BranchOffice getMainOffice() {
        return mainOffice;
    }

    public void setMainOffice(BranchOffice mainOffice) {
        this.mainOffice = mainOffice;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public RACPriceList getCurrentPriceList() {
        return currentPriceList;
    }

    public void setCurrentPriceList(RACPriceList currentPriceList) {
        this.currentPriceList = currentPriceList;
    }

    public Set<RACReservation> getRacReservations() {
        if(this.reservations == null){
            this.reservations = new HashSet<>();
        }
        return this.reservations;
    }

    public void setRacReservations(Set<RACReservation> racReservations) {
        this.reservations = racReservations;
    }
}
