package info.dmerej.train_reservation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Train {

    private String name;
    @Id
    @GeneratedValue
    private Long id;
    public Train() {
    }

    public Train(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Train{" +
            "name='" + name + '\'' +
            ", id=" + id +
            '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}