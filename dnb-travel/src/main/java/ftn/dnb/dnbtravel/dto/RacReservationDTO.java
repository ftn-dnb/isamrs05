package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class RacReservationDTO {
    private  Long id;
    private  Long company_id;
    private  Long item_id;
    private  Date begin;
    private  Date end;

    public RacReservationDTO(){

    }


    public RacReservationDTO(Long id, Long company_id, Long item_id, Date begin, Date end) {
        this.id = id;
        this.company_id = company_id;
        this.item_id = item_id;
        this.begin = begin;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
