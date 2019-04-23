package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class FlightReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public FlightReservation() {
        super();
    }

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
