package ftn.dnb.dnbtravel.dto;

import java.util.Date;

public class RACIncomeSearchDataDTO {

    private Date beginDate;
    private Date endDate;
    private Long id;

    public RACIncomeSearchDataDTO(){

    }

    public RACIncomeSearchDataDTO(Date beginDate, Date endDate, Long id) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
