package entities;

import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Harbour")
public class Harbour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HarbourID")
    @NotNull
    private int harbourId;
    @Column (name ="Capacity")
    private int capacity;
    @Column (name ="name")
    private String name;
    @Column (name ="address")
    private String address;

    @OneToMany(mappedBy="harbour", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Boat> boats = new ArrayList<Boat>();

    public Harbour() {
    }

    public Harbour(String address, String name, int capacity) {
        this.harbourId = harbourId;
        this.capacity = capacity;
        this.name = name;
        this.address = address;
        this.boats = new ArrayList<>();
    }


    public void addBoat(Boat boat) {
        this.boats.add(boat);
        if (boat != null) {
            boat.setHarbour(this);
        }
    }

    public int getHarbourId() {
        return harbourId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setHarbourId(int harbourId) {
        this.harbourId = harbourId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
