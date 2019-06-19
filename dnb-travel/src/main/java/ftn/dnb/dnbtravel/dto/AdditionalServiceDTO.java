package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.AdditionalService;
import net.bytebuddy.implementation.bytecode.Addition;

public class AdditionalServiceDTO {
    private Long id;
    private String serviceName;
    private int servicePrice;
    private Long hotel_id;
    private Long room_id;

    public AdditionalServiceDTO() { super(); }

    public AdditionalServiceDTO(Long id, String serviceName, int servicePrice, Long hotel_id, Long room_id) {
        this.id = id;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.hotel_id = hotel_id;
        this.room_id = room_id;
    }

    public AdditionalServiceDTO(AdditionalService service) {
        this.id = service.getId();
        this.serviceName = service.getServiceName();
        this.servicePrice = service.getServicePrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }
}
