package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class RACReservationRequestDTO {

    private String user;
    private Long itemID;
    private Date beginDate;
    private Date endDate;
    private Long companyID;

    public RACReservationRequestDTO(String user, Long itemID, Date beginDate, Date endDate, Long companyID) {
        this.user = user;
        this.itemID = itemID;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.companyID = companyID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }
}
