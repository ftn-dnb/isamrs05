package ftn.dnb.dnbtravel.dto;

public class RACSetPriceListDTO {
    private Long company_id;
    private Long price_list_id;
    private String name;

    public RACSetPriceListDTO(){

    }

    public RACSetPriceListDTO(Long company_id, Long price_list_id, String name) {
        this.company_id = company_id;
        this.price_list_id = price_list_id;
        this.name = name;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public Long getPrice_list_id() {
        return price_list_id;
    }

    public void setPrice_list_id(Long price_list_id) {
        this.price_list_id = price_list_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
