package ftn.dnb.dnbtravel.model;

public class BranchOffice {

    private String name;
    private Address address;
    private RentACarCompany company;

    public BranchOffice(){
        super();
    }

    public BranchOffice(String name, Address address, RentACarCompany company) {
        this.name = name;
        this.address = address;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RentACarCompany getCompany() {
        return company;
    }

    public void setCompany(RentACarCompany company) {
        this.company = company;
    }
}
