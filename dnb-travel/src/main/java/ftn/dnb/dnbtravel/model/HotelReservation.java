package ftn.dnb.dnbtravel.model;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class HotelReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "beginDate", nullable = false)
    private Date beginDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "rating", nullable = false)
    private double rating;

    @OneToOne(cascade = CascadeType.ALL)
    private HotelPriceListItem hotelPriceListItem;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public HotelReservation() { super(); }

    public HotelReservation(Long id, Date beginDate, Date endDate, double rating, HotelPriceListItem hotelPriceListItem, User user) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.rating = rating;
        this.hotelPriceListItem = hotelPriceListItem;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public HotelPriceListItem getHotelPriceListItem() {
        return hotelPriceListItem;
    }

    public void setHotelPriceListItem(HotelPriceListItem hotelPriceListItem) {
        this.hotelPriceListItem = hotelPriceListItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
