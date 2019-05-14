package ftn.dnb.dnbtravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class HotelPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<HotelPriceListItem> hotelPriceListItems;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Hotel hotel;

    public HotelPriceList() {
        super();
    }

    public HotelPriceList(Set<HotelPriceListItem> hotelPriceListItems, Hotel hotel) {
        this.hotelPriceListItems = hotelPriceListItems;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<HotelPriceListItem> getHotelPriceListItems() {
        return hotelPriceListItems;
    }

    public void setHotelPriceListItems(Set<HotelPriceListItem> hotelPriceListItems) {
        this.hotelPriceListItems = hotelPriceListItems;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
