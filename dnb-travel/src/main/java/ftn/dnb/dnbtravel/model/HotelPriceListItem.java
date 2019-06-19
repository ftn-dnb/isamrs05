package ftn.dnb.dnbtravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ftn.dnb.dnbtravel.dto.AdditionalServiceDTO;
import ftn.dnb.dnbtravel.dto.HotelPriceListDTO;
import ftn.dnb.dnbtravel.dto.HotelPriceListItemDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class HotelPriceListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activeDiscount", nullable = false)
    private double activeDiscount;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "pricePerDay", nullable = false)
    private int pricePerDay;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private HotelPriceList hotelPriceList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "hotel_price_list_item_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "additional_service_id", referencedColumnName = "id"))
    private Set<AdditionalService> additionalServices;

    public HotelPriceListItem() {
        super();
    }

    public HotelPriceListItem(double activeDiscount, Date startDate, Date endDate, int pricePerDay, Room room, HotelPriceList hotelPriceList, Set<AdditionalService> additionalServices) {
        this.activeDiscount = activeDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;
        this.room = room;
        this.hotelPriceList = hotelPriceList;
        this.additionalServices = additionalServices;
    }

    public HotelPriceListItem(HotelPriceListItemDTO dto) {
        this.activeDiscount = dto.getActiveDiscount();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.pricePerDay = dto.getPricePerDay();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getActiveDiscount() {
        return activeDiscount;
    }

    public void setActiveDiscount(double activeDiscount) {
        this.activeDiscount = activeDiscount;
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

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public HotelPriceList getHotelPriceList() {
        return hotelPriceList;
    }

    public void setHotelPriceList(HotelPriceList hotelPriceList) {
        this.hotelPriceList = hotelPriceList;
    }

    public Set<AdditionalService> getAdditionalServices() {
        if (this.additionalServices == null) {
            this.additionalServices = new HashSet<>();
        }
        return this.additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }
}
