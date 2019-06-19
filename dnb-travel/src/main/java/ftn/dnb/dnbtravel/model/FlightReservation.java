package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class FlightReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservationDate", nullable = false)
    private Date reservationDate;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Column(name = "seatRow", nullable = false)
    private int seatRow;

    @Column(name = "seatColumn", nullable = false)
    private int seatColumn;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    @Column(name = "passport", nullable = false)
    private String passport;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "fastReservation", nullable = false)
    private boolean fastReservation;

    @ManyToOne
    private User user;

    @Version
    private Long version;


    public FlightReservation() { super(); }

    public FlightReservation(Long id, Date reservationDate, float price, float rating, int seatRow, int seatColumn) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.price = price;
        this.rating = rating;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isFastReservation() {
        return fastReservation;
    }

    public void setFastReservation(boolean fastReservation) {
        this.fastReservation = fastReservation;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightReservation that = (FlightReservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FlightReservation{" +
                "id=" + id +
                ", reservationDate=" + reservationDate +
                ", price=" + price +
                ", rating=" + rating +
                ", seatRow=" + seatRow +
                ", seatColumn=" + seatColumn +
                '}';
    }
}
