package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Boat")
public class Boat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column (name ="boatID")
    private int boatId;
    @Column (name ="brand")
    private String brand;
    @Column (name ="name")
    private String name;

    public Boat() {
    }

    @ManyToOne
    @JoinColumn(name="OwnerID")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name="HarbourID")
    private Harbour harbour;

    public Boat(String brand, String name) {
        this.boatId = boatId;
        this.brand = brand;
        this.name = name;
        this.owner = owner;
        this.harbour = harbour;


    }


    public int getBoatId() {
        return boatId;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }


    public Owner getOwner() {
        return owner;
    }

    public Harbour getHarbour() {
        return harbour;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setHarbour(Harbour harbour) {
        this.harbour = harbour;
    }
}
