package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RACReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "beginDate", nullable = false)
    private Date beginDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "rating", nullable = false)
    private double rating;

    @OneToOne(cascade = CascadeType.ALL)
    private RACPriceListItem item;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public RACReservation(){

    }

    public RACReservation(Long id, Date beginDate, Date endDate, double rating, RACPriceListItem item, User user) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.rating = rating;
        this.item = item;
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

    public RACPriceListItem getItem() {
        return item;
    }

    public void setItem(RACPriceListItem item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
