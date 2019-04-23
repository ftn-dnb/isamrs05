package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.AirlinePriceListItem;

import java.util.Date;

public class AirlinePriceListItemDTO {

    private Long id;
    private int activeDiscount;
    private float price;
    private Date startDate;
    private Date endDate;

    public AirlinePriceListItemDTO() {
    }

    public AirlinePriceListItemDTO(Long id, int activeDiscount, float price, Date startDate, Date endDate) {
        this.id = id;
        this.activeDiscount = activeDiscount;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AirlinePriceListItemDTO(AirlinePriceListItem item) {
        this.id = item.getId();
        this.activeDiscount = item.getActiveDiscount();
        this.price = item.getPrice();
        this.startDate = item.getStartDate();
        this.endDate = item.getEndDate();
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
}
