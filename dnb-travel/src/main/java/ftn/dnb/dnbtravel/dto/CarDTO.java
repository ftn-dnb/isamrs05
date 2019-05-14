package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Car;
import ftn.dnb.dnbtravel.model.CarType;

public class CarDTO {

    private Long id;
    private String name;
    private String brand;
    private int manufYear;
    private int seatsNumber;
    private CarType type;
    private double rating;
    private RentACarCompanyDTO company;

    public CarDTO(Long id, String name, String brand,
                  int manufYear, int seatsNumber, CarType type,
                  double rating, RentACarCompanyDTO company) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.manufYear = manufYear;
        this.seatsNumber = seatsNumber;
        this.type = type;
        this.rating = rating;
        this.company = company;
    }

    public CarDTO(Car car){
        this.id = car.getId();
        this.name = car.getName();
        this.brand = car.getBrand();
        this.manufYear = car.getManufYear();
        this.type = car.getType();
        this.rating = car.getRating();
        this.company = new RentACarCompanyDTO(car.getCompany());
        this.seatsNumber = car.getSeatsNumber();
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getManufYear() {
        return manufYear;
    }

    public void setManufYear(int manufYear) {
        this.manufYear = manufYear;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public RentACarCompanyDTO getCompany() {
        return company;
    }

    public void setCompany(RentACarCompanyDTO company) {
        this.company = company;
    }
}
