package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.HotelPriceList;
import ftn.dnb.dnbtravel.model.HotelPriceListItem;

import java.util.ArrayList;

public class HotelPriceListDTO {

    private Long id;
    private String description;
    private ArrayList<HotelPriceListItemDTO> hotelPriceListItems;
    private Long hotelID;

    public HotelPriceListDTO() { super(); }

    public HotelPriceListDTO(HotelPriceList hotelPriceList) {
        this.id = hotelPriceList.getId();
        this.description = hotelPriceList.getDescription();
        this.hotelPriceListItems = new ArrayList<>();
        for (HotelPriceListItem item : hotelPriceList.getHotelPriceListItems()) {
            this.hotelPriceListItems.add(new HotelPriceListItemDTO(item));
        }
        this.hotelID = hotelPriceList.getHotel().getId();
    }

    public HotelPriceListDTO(Long id, String description, ArrayList<HotelPriceListItemDTO> hotelPriceListItems, Long hotelID) {
        this.description = description;
        this.hotelPriceListItems = hotelPriceListItems;
        this.hotelID = hotelID;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<HotelPriceListItemDTO> getHotelPriceListItems() {
        return hotelPriceListItems;
    }

    public void setHotelPriceListItems(ArrayList<HotelPriceListItemDTO> hotelPriceListItems) {
        this.hotelPriceListItems = hotelPriceListItems;
    }

    public Long getHotelID() {
        return hotelID;
    }

    public void setHotelID(Long hotelID) {
        this.hotelID = hotelID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
