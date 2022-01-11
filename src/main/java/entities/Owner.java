package entities;

import entities.Boat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Owner")
public class Owner {

    @Id
    @Column(name = "ownerID")
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int ownerId;
    @Column (name ="name")
    private String name;
    @Column (name = "address")
    private String address;
    @Column (name = "phone")
    private String phone;

    @OneToMany(mappedBy = "owner",cascade = CascadeType.PERSIST)
    private List<Boat> boats = new ArrayList<>();

    public Owner(String name, String address, String phone) {
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.boats = boats;
    }

    public Owner() {
    }

    public void addBoat(Boat boat) {
        this.boats.add(boat);
        if (boat != null){
            boat.setOwner(this);
        }
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Boat> getBoats() {
        return boats;
    }

}