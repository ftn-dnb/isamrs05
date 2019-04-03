package ftn.dnb.dnbtravel.model;

import javax.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "manufYear", nullable = false)
    private int manufYear;
    @Column(name = "type" , nullable = false)
    private CarType type;
    @Column(name = "rating", nullable = false)
    private double rating;

   // @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.)
    private RentACarCompany company;

    public Car(){
        super();
    }

    public Car(String name, String brand, int manufYear, CarType type, double rating) {
        this.name = name;
        this.brand = brand;
        this.manufYear = manufYear;
        this.type = type;
        this.rating = rating;
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
}
