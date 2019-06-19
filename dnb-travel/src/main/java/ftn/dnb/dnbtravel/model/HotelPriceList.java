package ftn.dnb.dnbtravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class HotelPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<HotelPriceListItem> hotelPriceListItems;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Hotel hotel;

    public HotelPriceList() {
        super();
    }

    public HotelPriceList(String description, Set<HotelPriceListItem> hotelPriceListItems, Hotel hotel) {
        this.description = description;
        this.hotelPriceListItems = hotelPriceListItems;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<HotelPriceListItem> getHotelPriceListItems() {

        if (this.hotelPriceListItems == null) {
            this.hotelPriceListItems = new HashSet<>();
        }
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
