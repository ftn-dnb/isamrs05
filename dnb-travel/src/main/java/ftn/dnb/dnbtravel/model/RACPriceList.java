package ftn.dnb.dnbtravel.model;

import java.util.HashSet;
import java.util.Set;

public class RACPriceList {

    //?
    private RentACarCompany company;
    //?
    private Set<RACPriceListItem> items = new HashSet<>();

    public RACPriceList(){
        super();
    }

    public RACPriceList(RentACarCompany company, Set<RACPriceListItem> items) {
        this.company = company;
        this.items = items;
    }

    public RentACarCompany getCompany() {
        return company;
    }

    public void setCompany(RentACarCompany company) {
        this.company = company;
    }

    public Set<RACPriceListItem> getItems() {
        return items;
    }

    public void setItems(Set<RACPriceListItem> items) {
        this.items = items;
    }
}
