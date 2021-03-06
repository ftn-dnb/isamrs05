package ftn.dnb.dnbtravel.model;

import ftn.dnb.dnbtravel.dto.CarDTO;

import javax.persistence.*;

@Entity
@Table(name = "CARS")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "manuf_year", nullable = false)
    private int manufYear;

    @Column(name = "seats_number", nullable = false)
    private int seatsNumber;

    @Column(name = "type" , nullable = false)
    private CarType type;

    @Column(name = "rating", nullable = false)
    private double rating;

    @ManyToOne(cascade = CascadeType.ALL)
    private RentACarCompany company;

    public Car(){
        super();
    }

    public Car(String name, String brand, int manufYear, CarType type, double rating,int seatsNumber, RentACarCompany company) {
        this.name = name;
        this.brand = brand;
        this.manufYear = manufYear;
        this.type = type;
        this.rating = rating;
        this.seatsNumber = seatsNumber;
        this.company = company;
    }

    public Car(CarDTO car){
        this.name = car.getName();
        this.brand = car.getBrand();
        this.manufYear = car.getManufYear();
        this.type = car.getType();
        this.seatsNumber = car.getSeatsNumber();
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

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RentACarCompany getCompany() {
        return company;
    }

    public void setCompany(RentACarCompany company) {
        this.company = company;
    }
}
