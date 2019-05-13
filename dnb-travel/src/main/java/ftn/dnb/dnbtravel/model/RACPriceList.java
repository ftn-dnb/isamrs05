package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "racpricelist")
public class RACPriceList {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private RentACarCompany company;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "racpricelist_items",
            joinColumns = @JoinColumn(name = "racpricelist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
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
