package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Airplane;

import java.util.Objects;

public class AirplaneDTO {

    private Long id;
    private String name;
    private int numOfRows;
    private int numOfColumns;

    public AirplaneDTO() {
        super();
    }

    public AirplaneDTO(Long id, String name, int numOfRows, int numOfColumns) {
        this.id = id;
        this.name = name;
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
    }

    public AirplaneDTO(Airplane airplane) {
        this.id = airplane.getId();
        this.name = airplane.getName();
        this.numOfRows = airplane.getNumOfRows();
        this.numOfColumns = airplane.getNumOfColumns();
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

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getNumOfColumns() {
        return numOfColumns;
    }

    public void setNumOfColumns(int numOfColumns) {
        this.numOfColumns = numOfColumns;
    }

    public int getNumOfSeats() {
        return numOfColumns * numOfRows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirplaneDTO that = (AirplaneDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
