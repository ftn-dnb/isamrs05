package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RentACarCompanyDTO {

    private long id;
    private String name;
    private String description;
    private double rating;
    private UserDTO administrator;
    private List<CarDTO> cars;
    private List<RACPriceListDTO> priceList;
    private RACPriceListDTO currentPriceList;
    private Address address;

    public RentACarCompanyDTO(){
        super();
    }

    public RentACarCompanyDTO(long id, String name, String description, double rating, Address address, UserDTO administrator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.administrator = administrator;
        this.cars = cars;
    }

    public RentACarCompanyDTO(RentACarCompany rentACarCompany){
        this.id = rentACarCompany.getId();
        this.name = rentACarCompany.getName();
        this.description = rentACarCompany.getDescription();
        this.rating = rentACarCompany.getRating();
        if(rentACarCompany.getMainOffice() != null) {
            this.address = rentACarCompany.getMainOffice().getAddress();
        }
        this.administrator = new UserDTO(rentACarCompany.getAdministrator());

        if(rentACarCompany.getCurrentPriceList()!= null)
            this.currentPriceList = new RACPriceListDTO(rentACarCompany.getCurrentPriceList());

        this.cars = new LinkedList<>();

        if((rentACarCompany.getCars()!= null) && (rentACarCompany.getCars().size() > 0)){
            fakeCars(rentACarCompany.getCars());
        }

        this.priceList = new LinkedList<>();
        if((rentACarCompany.getPriceList() != null ) && (rentACarCompany.getPriceList().size() > 0)){
            fakePraceLists(rentACarCompany.getPriceList());
        }


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

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }

    public RACPriceListDTO getCurrentPriceList() {
        return currentPriceList;
    }

    public void setCurrentPriceList(RACPriceListDTO currentPriceList) {
        this.currentPriceList = currentPriceList;
    }

    private void fakeCars(Set<Car> realCars){
        for(Car c: realCars){
            RentACarCompanyDTO fakeCompany = new RentACarCompanyDTO();
            fakeCompany.setId(c.getCompany().getId());
            fakeCompany.setName(c.getCompany().getName());
            CarDTO fakeCar = new CarDTO(c.getId(),c.getName(),c.getBrand(),c.getManufYear(),c.getSeatsNumber(),c.getType(),c.getRating(),
                    fakeCompany);
            this.cars.add(fakeCar);
        }
    }

    private void fakePraceLists(Set<RACPriceList> lists){
        for(RACPriceList l: lists){
            RACPriceListDTO fakeList = new RACPriceListDTO(l);
            this.priceList.add(fakeList);
        }
    }

    public List<RACPriceListDTO> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<RACPriceListDTO> priceList) {
        this.priceList = priceList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
