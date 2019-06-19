package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.AdditionalService;
import ftn.dnb.dnbtravel.model.HotelPriceListItem;
import net.bytebuddy.implementation.bytecode.Addition;

import java.util.ArrayList;
import java.util.Date;

public class HotelPriceListItemDTO {

    private Long id;
    private double activeDiscount;
    private Date startDate;
    private Date endDate;
    private int pricePerDay;
    //private Long roomID;
    //private int roomNumber;
    private Long hotelPriceListID;
    private RoomDTO room;
    private ArrayList<AdditionalServiceDTO> additionalServices;

    public HotelPriceListItemDTO() {
        super();
    }

    public HotelPriceListItemDTO(HotelPriceListItem hotelPriceListItem) {
        this.id = hotelPriceListItem.getId();
        this.activeDiscount = hotelPriceListItem.getActiveDiscount();
        this.startDate = hotelPriceListItem.getStartDate();
        this.endDate = hotelPriceListItem.getEndDate();
        this.pricePerDay = hotelPriceListItem.getPricePerDay();
        //this.roomID = hotelPriceListItem.getRoom().getId();
        this.hotelPriceListID = hotelPriceListItem.getHotelPriceList().getId();
        //this.roomNumber = hotelPriceListItem.getRoom().getRoomNumber();
        this.room = new RoomDTO(hotelPriceListItem.getRoom());
        ArrayList<AdditionalService>services  = new ArrayList<>(hotelPriceListItem.getAdditionalServices());
        this.additionalServices = new ArrayList<>();
        for (AdditionalService service : services) {
            this.additionalServices.add(new AdditionalServiceDTO(service));
        }
    }

    public HotelPriceListItemDTO(double activeDiscount, Date startDate, Date endDate, int pricePerDay, Long hotelPriceListID, RoomDTO room, ArrayList<AdditionalServiceDTO> additionalServiceDTOS) {
        this.activeDiscount = activeDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;
        //this.roomID = roomID;
        this.hotelPriceListID = hotelPriceListID;
        //this.roomNumber = roomNumber;
        this.room = room;
        this.additionalServices = additionalServiceDTOS;
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

    public Long getHotelPriceListID() {
        return hotelPriceListID;
    }

    public void setHotelPriceListID(Long hotelPriceListID) {
        this.hotelPriceListID = hotelPriceListID;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public ArrayList<AdditionalServiceDTO> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(ArrayList<AdditionalServiceDTO> additionalServices) {
        this.additionalServices = additionalServices;
    }
}

