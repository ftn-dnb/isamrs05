package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.RACPriceList;

public class RACPriceListDTO {
    private Long id;
    private String name;

    public RACPriceListDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RACPriceListDTO(RACPriceList list){
        this.id = list.getId();
        this.name = list.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
