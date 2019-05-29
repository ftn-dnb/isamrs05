package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.HotelPriceList;
import ftn.dnb.dnbtravel.model.HotelPriceListItem;

import java.util.ArrayList;

public class HotelPriceListDTO {

    private String description;
    private ArrayList<HotelPriceListItemDTO> hotelPriceListItems;
    private Long hotelID;

    public HotelPriceListDTO() { super(); }

    public HotelPriceListDTO(HotelPriceList hotelPriceList) {
        this.description = hotelPriceList.getDescription();
        this.hotelPriceListItems = new ArrayList<>();
        for (HotelPriceListItem item : hotelPriceList.getHotelPriceListItems()) {
            this.hotelPriceListItems.add(new HotelPriceListItemDTO(item));
        }
        this.hotelID = hotelPriceList.getHotel().getId();
    }

    public HotelPriceListDTO(String description, ArrayList<HotelPriceListItemDTO> hotelPriceListItems, Long hotelID) {
        this.description = description;
        this.hotelPriceListItems = hotelPriceListItems;
        this.hotelID = hotelID;
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
}
