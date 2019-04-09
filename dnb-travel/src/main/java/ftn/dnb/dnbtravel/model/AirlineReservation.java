package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AirlineReservation {

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

    public AirlineReservation() {
        super();
    }
}
