package DTO.boatDTOs;

import DTO.HarbourDTOs.HarbourDTO;
import entities.Boat;
import entities.Harbour;

import java.util.ArrayList;
import java.util.List;

public class BoatDTO {

    private int boatID;
    private String brand;
    private String name;

    public BoatDTO() {
    }

    public BoatDTO(Boat boat) {
        this.boatID = boat.getBoatId();
        this.brand = boat.getBrand();
        this.name = boat.getName();
    }

    public static List<BoatDTO> getDTO(List<Boat>boats){
        if (boats != null) {
            List <BoatDTO> boatsDTO = new ArrayList<>();
            boats.forEach(b -> boatsDTO.add(new BoatDTO(b)));
            return boatsDTO;
        }else {
            return null;
        }
    }

    public int getBoatID() {
        return boatID;
    }

    public void setBoatID(int boatID) {
        this.boatID = boatID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
