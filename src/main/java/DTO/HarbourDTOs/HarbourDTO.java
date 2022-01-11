package DTO.HarbourDTOs;

import entities.Boat;
import entities.Harbour;

import java.util.ArrayList;
import java.util.List;

public class HarbourDTO {

    private int harbourId;
    private int capacity;
    private String name;
    private String address;
    private List<Boat> boats;

    public HarbourDTO() {
    }

    public HarbourDTO(Harbour harbour) {
        this.harbourId = harbour.getHarbourId();
        this.capacity = harbour.getCapacity();
        this.name = harbour.getName();
        this.address = harbour.getAddress();
        this.boats = harbour.getBoats();
    }

    public static List <HarbourDTO> getDTO(List<Harbour>harbours){
        if (harbours != null) {
            List <HarbourDTO> harboursDTO = new ArrayList<>();
            harbours.forEach(h -> harboursDTO.add(new HarbourDTO(h)));
            return harboursDTO;
        }else {
            return null;
        }
    }

    public int getHarbourId() {
        return harbourId;
    }

    public void setHarbourId(int harbourId) {
        this.harbourId = harbourId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }
}



