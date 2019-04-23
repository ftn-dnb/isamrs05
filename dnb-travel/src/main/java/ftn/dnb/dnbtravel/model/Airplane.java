package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "numberOfRows", nullable = false)
    private int numOfRows;

    @Column(name = "numberOfColumns", nullable = false)
    private int numOfColumns;

    public Airplane() {
        super();
    }

    public Airplane(Long id, String name, int numOfRows, int numOfColumns) {
        this.id = id;
        this.name = name;
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return Objects.equals(id, airplane.id) &&
                Objects.equals(name, airplane.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numOfRows=" + numOfRows +
                ", numOfColumns=" + numOfColumns +
                '}';
    }
}
