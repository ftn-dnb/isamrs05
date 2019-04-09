package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class AirlinePriceListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "activeDiscount")
    private int activeDiscount;

    @Column(name = "price")
    private float price;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;


    public AirlinePriceListItem() {
        super();
    }

    public AirlinePriceListItem(Long id, int activeDiscount, float price, Date startDate, Date endDate) {
        this.id = id;
        this.activeDiscount = activeDiscount;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getActiveDiscount() {
        return activeDiscount;
    }

    public void setActiveDiscount(int activeDiscount) {
        this.activeDiscount = activeDiscount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirlinePriceListItem that = (AirlinePriceListItem) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AirlinePriceListItem{" +
                "id=" + id +
                ", activeDiscount=" + activeDiscount +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
