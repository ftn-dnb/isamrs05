package ftn.dnb.dnbtravel.model;

import ftn.dnb.dnbtravel.dto.AdditionalServiceDTO;

import javax.persistence.*;

@Entity
public class AdditionalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serviceName", nullable = false)
    private String serviceName;

    @Column(name = "servicePrice", nullable = false)
    private int servicePrice;

    public AdditionalService() { super(); }

    public AdditionalService(Long id, String serviceName, int servicePrice) {
        this.id = id;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

    public AdditionalService(AdditionalServiceDTO additionalServiceDTO) {
        this.id = additionalServiceDTO.getId();
        this.serviceName = additionalServiceDTO.getServiceName();
        this.servicePrice = additionalServiceDTO.getServicePrice();
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
}
